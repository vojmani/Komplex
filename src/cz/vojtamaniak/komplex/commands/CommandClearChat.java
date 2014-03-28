package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cz.vojtamaniak.komplex.Komplex;

public class CommandClearChat extends ICommand {

	public CommandClearChat(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(cmd.getName().equalsIgnoreCase("clearchat"))
			return false;
		
		if(!sender.hasPermission("komplex.clearchat")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		for(int i = 0; i < 120; i++){
			Bukkit.broadcastMessage("");
		}
		Bukkit.broadcastMessage(msgManager.getMessage("CHAT_CLEARED").replaceAll("%NICK%", sender.getName()));
		return true;
	}
	
}