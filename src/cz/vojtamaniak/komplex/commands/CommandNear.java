package cz.vojtamaniak.komplex.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
		if(cmd.getName().equalsIgnoreCase("near")){
			if(sender.isOp() || sender.hasPermission("komplex.near")){
				if(args.length > 0){
					OfflinePlayer offP = Bukkit.getOfflinePlayer(args[0]);
					if(offP.isOnline()){
						Player player = (Player) offP;
						List<Entity> entities = player.getNearbyEntities(300, 300, 300);
						String message = "";
						for(Entity e : entities){
							if(e instanceof Player){
								message += msgManager.getMessage("NEAR_PLAYER").replaceAll("%NICK%", ((Player) e).getName()).replaceAll("%DISTANCE%", (int)player.getLocation().distance(e.getLocation()) + "b") + ", ";
							}
						}
						if(message == ""){
							sender.sendMessage(msgManager.getMessage("NEAR_OTHER").replaceAll("%PLAYERS%", msgManager.getMessage("NEAR_NOBODY")));
							return true;
						}
						message += "/*";
						message.replaceAll(", /*", "");
						
						sender.sendMessage(msgManager.getMessage("NEAR_OTHER").replaceAll("%PLAYERS%", message));
					}else{
						sender.sendMessage(msgManager.getMessage("PLAYER_OFFLINE"));
					}
				}else if(sender instanceof Player){
					Player player = (Player) sender;
					List<Entity> entities = player.getNearbyEntities(300, 300, 300);
					String message = "";
					for(Entity e : entities){
						if(e instanceof Player){
							message += msgManager.getMessage("NEAR_PLAYER").replaceAll("%NICK%", ((Player) e).getName()).replaceAll("%DISTANCE%", (int)player.getLocation().distance(e.getLocation()) + "b") + ", ";
						}
					}
					if(message == ""){
						sender.sendMessage(msgManager.getMessage("NEAR").replaceAll("%PLAYERS%", msgManager.getMessage("NEAR_NOBODY")));
						return true;
					}
					message += "/*";
					message.replaceAll(", /*", "");
					
					player.sendMessage(msgManager.getMessage("NEAR").replaceAll("%PLAYERS%", message));
				}
			}
			return true;
		}
		return false;
	}

}
