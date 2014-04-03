package cz.vojtamaniak.komplex.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandSetWarp extends ICommand {
	
	public CommandSetWarp(Komplex plg){
		super(plg);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("setwarp"))
			return false;
		
		if(!sender.hasPermission("komplex.setwarp")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(args.length == 0){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return true;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		
		if(database.getWarpLocation(args[0]) != null){
			sm(sender, "WARP_SET_EXISTS");
			return true;
		}
		
		Player player = (Player)sender;
		
		Location loc = player.getLocation();
		
		database.addWarp(args[0], loc.getWorld().getName(), (int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), loc.getYaw(), loc.getPitch());
		sm(sender, "WARP_SET_SUCCESS");
		return true;
	}

}
