package cz.vojtamaniak.komplex.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandCloseTicket extends ICommand {

	public CommandCloseTicket(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("closeticket"))
			return false;
		
		if(!sender.hasPermission("komplex.ticket.admin")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(args.length < 1){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
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
		
		boolean open = ((String)ticket.get("status")).equals("OPEN");
		String creator = (String)ticket.get("creator");
		
		api.closeTicket(id, open, creator, sender.getName());
		sm(sender, open ? "TICKET_CLOSED" : "TICKET_OPENED", "%ID%", String.valueOf(id));
		return true;
	}

}
