package cz.vojtamaniak.komplex.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandIgnore extends ICommand {
	
	public CommandIgnore(Komplex plg){
		super(plg);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("ignore"))
			return false;
		
		if(!sender.hasPermission("komplex.ignore")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(args.length < 1){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return true;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		
		switch(args[0].toLowerCase()){
		case "list":
			listIgnored(sender);
			break;
		case "add":
			addIgnored(sender, args);
			break;
		case "remove":
			removeIgnored(sender, args);
			break;
		default:
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			break;
		}
		return true;
	}
	
	private void listIgnored(CommandSender sender){
		List<String> ignPlayers = database.getIgnoredPlayers(sender.getName());
		if(ignPlayers.isEmpty()){
			sm(sender, "IGNORE_LIST_EMPTY");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(String player : ignPlayers){
			sb.append(player + ", ");
		}
		sb.replace(sb.length() - 2, sb.length(), "");
		String players = sb.toString();
		sm(sender, "IGNORE_LIST_FORMAT", "%PLAYERS%", players);
	}
	
	private void addIgnored(CommandSender sender, String[] args){
		if(plg.getUser(sender.getName().toLowerCase()).getIgnoredPlayers().contains(args[1].toLowerCase())){
			sender.sendMessage(msgManager.getMessage("IGNORE_ADD_ALREADY"));
		}else if(Bukkit.getOfflinePlayer(args[1]).isOnline()){
			Player p = (Player)Bukkit.getOfflinePlayer(args[1]);
			if(p.hasPermission("komplex.ignore.bypass")){
				sender.sendMessage(msgManager.getMessage("IGNORE_ADD_BYPASS").replaceAll("%PLAYER%", p.getName()));
				return;
			}
		}else{
			if(sender.getName().equalsIgnoreCase(args[1])){
				sender.sendMessage(msgManager.getMessage("IGNORE_ADD_SELF"));
				return;
			}
			plg.getUser(sender.getName().toLowerCase()).getIgnoredPlayers().add(args[1].toLowerCase());
			database.addIgnored(sender.getName(), args[1]);
			sender.sendMessage(msgManager.getMessage("IGNORE_ADD_SUCCESS").replaceAll("%PLAYER%", args[1]));
		}
	}
	
	private void removeIgnored(CommandSender sender, String[] args){
		if(!plg.getUser(sender.getName().toLowerCase()).getIgnoredPlayers().contains(args[1].toLowerCase())){
			sender.sendMessage(msgManager.getMessage("IGNORE_REMOVE_NOTIGNORED"));
		}else{
			plg.getUser(sender.getName().toLowerCase()).getIgnoredPlayers().remove(args[1].toLowerCase());
			database.removeIgnored(sender.getName(), args[1]);
			sender.sendMessage(msgManager.getMessage("IGNORE_REMOVE_SUCCESS"));
		}
	}
}
