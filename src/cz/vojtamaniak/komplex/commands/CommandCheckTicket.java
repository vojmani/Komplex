package cz.vojtamaniak.komplex.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandCheckTicket extends ICommand {
	
	public CommandCheckTicket(Komplex plg){
		super(plg);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("checkticket"))
			return false;
		
		if(args.length < 1){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return true;
		}
		
		if(!Utils.isInt(args[0])){
			sm(sender, "TICKET_ID_INT");
			return true;
		}
		
		int id = Integer.parseInt(args[0]);
		
		HashMap<String, Object> ticket = database.getTicket(id);
		if(ticket == null){
			sm(sender, "TICKET_NOT_EXISTS", "%ID%", id+"");
			return true;
		}
		
		if(!(sender.hasPermission("komplex.ticket.admin")) && !((String)ticket.get("creator")).equalsIgnoreCase(sender.getName())){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		sm(sender, "TICKET_INFO_ID", "%ID%", id + "");
		sm(sender, "TICKET_INFO_CREATOR", "%CREATOR%", (String)ticket.get("creator"));
		sm(sender, "TICKET_INFO_CREATED", "%CREATED%", Utils.dateFormat((long)ticket.get("timeCreated")));
		sm(sender, "TICKET_INFO_REFRESH", "%REFRESHED%", Utils.dateFormat((long)ticket.get("refreshTime")));
		sm(sender, "TICKET_INFO_STATUS", "%STATUS%", (String)ticket.get("status"));
		if((String)ticket.get("admin") == null){
			sm(sender, "TICKET_INFO_ADMIN", "%ADMIN%", "Nikdo");
		}else{
			sm(sender, "TICKET_INFO_ADMIN", "%ADMIN%", (String)ticket.get("admin"));
		}
		sm(sender, "TICKET_INFO_TEXT", "%TEXT%", (String)ticket.get("text"));
		sm(sender, "TICKET_INFO_USERREPLY", "%REPLY%", ticket.get("userReply") == null ? "Zadna" : (String)ticket.get("userReply"));
		sm(sender, "TICKET_INFO_ADMINREPLY", "%REPLY%", ticket.get("adminReply") == null ? "Zadna" : (String)ticket.get("adminReply"));
		return true;
	}

}
