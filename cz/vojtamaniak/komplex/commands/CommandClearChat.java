package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandClearChat extends ICommand implements CommandExecutor {

	public CommandClearChat(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(cmd.getName().equalsIgnoreCase("clearchat")){
			if(sender.isOp() || sender.hasPermission("komplex.clearchat")){
				for(int i=0; i<120; i++){
					for(Player p : Bukkit.getOnlinePlayers()){
						p.sendMessage("");
					}
				}
				for(Player p : Bukkit.getOnlinePlayers()){
					p.sendMessage(msgManager.getMessage("CHAT_CLEARED").replaceAll("%NICK%", sender.getName()));
				}
			}else{
				sender.sendMessage(msgManager.getMessage("NO_PERMISSION"));
			}
			return true;
		}
		return false;
	}
	
}
