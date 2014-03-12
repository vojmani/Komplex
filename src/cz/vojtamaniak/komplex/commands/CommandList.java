package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandList extends ICommand {

	public CommandList(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(cmd.getName().equalsIgnoreCase("list")){
			if(sender.hasPermission("komplex.list")){
				String players = "";
				int playersOnline = 0;
				for(Player p : Bukkit.getOnlinePlayers()){
					playersOnline++;
					players += p.getName() + ", ";
				}
				players += "/*";
				players.replaceAll(", /*", "");
				sender.sendMessage(msgManager.getMessage("LIST").replaceAll("%NUMBEROFPLAYERS%", Integer.toString(playersOnline)).replaceAll("%MAXPLAYERS%", Integer.toString(Bukkit.getMaxPlayers())).replaceAll("%PLAYERS%", players));
			}
			else{
				sender.sendMessage(msgManager.getMessage("NO_PERMISSION"));
			}
			return true;
		}
		return false;
	}

}
