package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cz.vojtamaniak.komplex.Komplex;

public class CommandTeleport extends ICommand {
	
	public CommandTeleport(Komplex plg){
		super(plg);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if(!cmd.getName().equalsIgnoreCase("teleport"))
			return false;
		
		if(!sender.hasPermission("komplex.tp")){
			
		}
		return true;
	}
}
