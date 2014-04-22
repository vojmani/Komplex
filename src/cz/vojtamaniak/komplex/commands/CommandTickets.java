package cz.vojtamaniak.komplex.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cz.vojtamaniak.komplex.Komplex;

public class CommandTickets extends ICommand {

	public CommandTickets(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("tickets"))
			return false;
		
		if(!sender.hasPermission("komplex.ticket.admin")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		List<String> tickets = null;
		if(args.length != 0 && args[0].equalsIgnoreCase("-all")){
			tickets = api.getTickets(true);
		}else{
			tickets = api.getTickets(false);
		}
		
		for(String ticket : tickets){
			sender.sendMessage(ticket);
		}
		return true;
	}

}
