package cz.vojtamaniak.komplex.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandSetSpawn extends ICommand {
	
	public CommandSetSpawn(Komplex plg){
		super(plg);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if(!cmd.getName().equalsIgnoreCase("setspawn"))
			return false;
		
		if(!sender.hasPermission("komplex.setspawn")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		
		Location loc = ((Player)sender).getLocation();
		
		api.setSpawn(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		sm(sender, "SPAWN_SET");
		return true;
	}
}
