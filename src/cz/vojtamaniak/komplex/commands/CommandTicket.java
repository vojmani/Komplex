package cz.vojtamaniak.komplex.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandTicket extends ICommand {
	
	public CommandTicket(Komplex plg){
		super(plg);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if(!cmd.getName().equalsIgnoreCase("ticket"))
			return false;
		
		if(!sender.hasPermission("komplex.ticket.create")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(args.length == 0){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return true;
		}
		
		if(sender instanceof Player){
			Location loc = ((Player)sender).getLocation();
			api.addTicket(sender.getName(), Utils.buildMessage(args, 0), loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		}else{
			api.addTicket(sender.getName(), Utils.buildMessage(args, 0), null, 0, 0, 0, 0F, 0F);
		}
		
		sm(sender, "TICKET_ADDED");
		return true;
	} 
}
