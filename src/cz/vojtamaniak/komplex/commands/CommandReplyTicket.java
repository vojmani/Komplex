package cz.vojtamaniak.komplex.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandReplyTicket extends ICommand {
	
	public CommandReplyTicket(Komplex plg){
		super(plg);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if(!cmd.getName().equalsIgnoreCase("replyticket"))
			return false;
		
		if(args.length < 2){
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
		
		if((String)ticket.get("status") == "CLOSED"){
			sm(sender, "TICKET_REPLY_CLOSED");
			return true;
		}
		
		if(sender.hasPermission("komplex.ticket.admin")){
			api.addAdminReplyTicket(id, sender.getName() + ": " +Utils.buildMessage(args, 1), sender.getName(), (String)ticket.get("creator"));
			sm(sender, "TICKET_REPLIED", "%ID%", id+"");
		}else{
			if(!sender.getName().toLowerCase().equals(((String)ticket.get("creator")).toLowerCase())){
				sm(sender, "TICKET_NOT_YOURS");
				return true;
			}
			api.addReplyTicket(id, Utils.buildMessage(args, 1));
			sm(sender, "TICKET_REPLIED", "%ID%", id+"");
		}
		return true;
	}
}
