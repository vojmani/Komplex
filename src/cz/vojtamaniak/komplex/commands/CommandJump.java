package cz.vojtamaniak.komplex.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandJump extends ICommand {

	public CommandJump(Komplex plg) {
		super(plg);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("jump"))
			return false;
		
		if(!sender.hasPermission("komplex.jump")){
			sendNoPermission(sender);
			return true;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		
		Player player = (Player)sender;
		Location target = player.getTargetBlock(null, 300).getLocation();
		
		if(plg.getConfigManager().getConfig().getBoolean("jump-use-velocity")){
			player.setVelocity(target.toVector());
		}else{
			player.teleport(target);
		}
		return true;
	}

}
