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
	
	public static String buildMessage(String[] arg, int start){
		String message = "";
		
		for(int i = start; i < arg.length; i++){
			message += arg[i] + " ";
		}
		return message;
	}
}