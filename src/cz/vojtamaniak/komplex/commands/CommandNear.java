package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandNear extends ICommand {

	public CommandNear(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("near"))
			return false;
		
		if(args.length == 0){
			nearSelf(sender);
		}else{
			nearOther(sender, args);
		}
		return true;
	}
	
	private void nearSelf(CommandSender sender){
		if(!sender.hasPermission("komplex.near")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return;
		}
		StringBuilder sb = new StringBuilder();
		for(Entity e : ((Player) sender).getNearbyEntities(300, 300, 300)){
			if(e instanceof Player){
				sb.append(msgManager.getMessage("NEAR_PLAYER").replaceAll("%NICK%", ((Player) e).getName()).replaceAll("%DISTANCE%", (int)((Player)sender).getLocation().distance(e.getLocation()) + "b") + ", ");
			}
		}
		
		if(sb.length() == 0){
			sm(sender, "NEAR", "%PLAYERS%", msgManager.getMessage("NEAR_NOBODY"));
			return;
		}
		
		sb.replace(sb.length() - 2, sb.length(), "");
		sm(sender, "NEAR", "%PLAYERS%", sb.toString());
	}
	
	private void nearOther(CommandSender sender, String[] args){
		if(!sender.hasPermission("komplex.near.other")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		Player player = Bukkit.getPlayer(args[0]);
		if(player == null){
			sm(sender, "PLAYER_OFFLINE");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		for(Entity e : player.getNearbyEntities(300, 300, 300)){
			if(e instanceof Player){
				sb.append(msgManager.getMessage("NEAR_PLAYER").replaceAll("%NICK%", ((Player) e).getName()).replaceAll("%DISTANCE%", player.getLocation().distance(e.getLocation()) + "b") + ", ");
			}
		}
		
		if(sb.length() == 0){
			sm(sender, "NEAR", "%NICK%", player.getName(), "%PLAYERS%", msgManager.getMessage("NEAR_NOBODY"));
			return;
		}
		
		sb.replace(sb.length() - 2, sb.length(), "");
		sm(sender, "NEAR_OTHER", "%NICK%", player.getName(), "%PLAYERS%", sb.toString());
	}
}
