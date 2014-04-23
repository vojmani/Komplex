package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
		Bukkit.broadcast(message, permission);
	}
}