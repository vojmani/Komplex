package cz.vojtamaniak.komplex.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;


public class CommandDeleteTicket extends ICommand {
	
	public CommandDeleteTicket(Komplex plg){
		super(plg);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if(!cmd.getName().equalsIgnoreCase("deleteticket"))
			return false;
		
		if(!sender.hasPermission("komplex.ticket.admin")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(!Utils.isInt(args[0])){
			sm(sender, "TICKET_ID_INT");
			return true;
		}
		int id = Integer.parseInt(args[0]);
		
		HashMap<String, Object> ticket = api.getTicket(id);
		
		if(ticket == null){
			sm(sender, "TICKET_NOT_EXISTS");
			return true;
		}
		
		api.deleteTicket(id, (String)ticket.get("creator"), sender.getName(), args.length == 1 ? "" : Utils.buildMessage(args, 1));
		sm(sender, "TICKET_DELETED", "%ID%", String.valueOf(id));
		return true;
	}
}
