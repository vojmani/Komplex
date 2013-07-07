package cz.vojtamaniak.komplex;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utils {
	
	public static void broadcast(String message, String permission){
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.isOp() || p.hasPermission(permission)){
				p.sendMessage(message);
			}
		}
	}
}