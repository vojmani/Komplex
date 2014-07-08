package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandInfoMessages extends ICommand {

	public CommandInfoMessages(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("infomessages"))
			return false;
		
		if(!sender.hasPermission("komplex.infomessages")){
			sendNoPermission(sender);
			return true;
		}
		
		if(args.length < 1){
			sendWrongUsageMessage(sender, cmd);
			return true;
		}
		
		switch(args[0].toLowerCase()){
		case "add":
			add(sender, cmd, args);
			break;
		case "remove":
			remove(sender, cmd, args);
			break;
		default:
			sendWrongUsageMessage(sender, cmd);
			break;
		}
		
		return true;
	}

	private void remove(CommandSender sender, Command cmd, String[] args) {
		if(args.length < 2){
			sendWrongUsageMessage(sender, cmd);
			return;
		}
		
		if(!Utils.isInt(args[1])){
			sm(sender, "INFOMESSAGES_ID_INT");
			return;
		}
		
		int id = Integer.parseInt(args[1]);
		
		api.removeInfoMessage(id);
	}

	private void add(CommandSender sender, Command cmd, String[] args) {
		
	}

}
