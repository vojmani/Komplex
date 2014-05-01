package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.KomplexAPI;
import cz.vojtamaniak.komplex.MessageManager;

abstract public class ICommand implements CommandExecutor {
	
	protected Komplex plg;
	protected MessageManager msgManager;
	protected KomplexAPI api;
	
	public ICommand(Komplex plg){
		this.plg = plg;
		this.msgManager = plg.getMessageManager();
		this.api = plg.getAPI();
	}
	
	protected void sm(CommandSender sender, String key, String... replacements){
		String message = msgManager.getMessage(key);
		
		if(replacements.length != 0){
			if((replacements.length % 2) != 0){
				plg.getLogger().severe("[ICommand - sm] Replacements are not multiply of 2! Skipping send...");
				return;
			}
			
			int index = 0;
			for(String s : replacements){
				if(s.startsWith("%")){
					message = message.replaceAll(s, replacements[index + 1]);
				}
				index++;
			}
		}
		sender.sendMessage(message.split("\n"));
	}
	
	protected void bm(String key, String permission, String... replacements){
		String message = msgManager.getMessage(key);
		
		if(replacements.length != 0){
			if((replacements.length % 2) != 0){
				plg.getLogger().severe("[ICommand - bm] Replacements are not multiply of 2! Skipping broadcast...");
				return;
			}
			
			int index = 0;
			for(String s : replacements){
				if(s.startsWith("%")){
					message = message.replaceAll(s, replacements[index + 1]);
				}
				index++;
			}
		}
		
		if(permission == ""){
			Bukkit.broadcastMessage(message);
		}else{
			Bukkit.broadcast(message, permission);
		}
	}
	
	protected void sendWrongUsageMessage(CommandSender sender, Command cmd){
		sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
	}
	
	protected void sendNoPermission(CommandSender sender){
		sm(sender, "NO_PERMISSION");
	}
	
	protected void kick(Player player, String key, String... replacements){
		String message = msgManager.getMessage(key);
		
		if(replacements.length != 0){
			if((replacements.length % 2) != 0){
				plg.getLogger().severe("[ICommand - kick] Replacements are not multiply of 2! Skipping kick...");
				return;
			}
			
			int index = 0;
			for(String s : replacements){
				if(s.startsWith("%")){
					message = message.replaceAll(s, replacements[index + 1]);
				}
				index++;
			}
		}
		
		player.kickPlayer(message);
	}
	
	protected String gm(String key, String... replacements){
		String message = msgManager.getMessage(key);
		
		if(replacements.length != 0){
			if((replacements.length % 2) != 0){
				plg.getLogger().severe("[ICommand - kick] Replacements are not multiply of 2! Skipping msg get...");
				return "";
			}
			
			int index = 0;
			for(String s : replacements){
				if(s.startsWith("%")){
					message = message.replaceAll(s, replacements[index + 1]);
				}
				index++;
			}
		}
		
		return message;
	}
}