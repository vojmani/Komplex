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
		if(!cmd.getName().equalsIgnoreCase("list"))
			return false;
		
		if(!sender.hasPermission("komplex.list")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		StringBuilder sb = new StringBuilder();
		for(Player p : Bukkit.getOnlinePlayers()){
			sb.append(p.getName() + ", ");
		}
		sb.replace(sb.length() - 2, sb.length(), "");
		sm(sender, "LIST", "%NUMBEROFPLAYERS%", Integer.toString(Bukkit.getOnlinePlayers().length), "%MAXPLAYERS%", Integer.toString(Bukkit.getMaxPlayers()), "%PLAYERS%", sb.toString());
		return true;
	}

}
