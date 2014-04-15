package cz.vojtamaniak.komplex.commands;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandMail extends ICommand {
	
	public CommandMail(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(!cmd.getName().equalsIgnoreCase("mail"))
			return false;
		
		if(!sender.hasPermission("komplex.mail")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(arg.length == 0){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return true;
		}
		
		switch(arg[0]){
		case "send":
			api.sendMail(sender.getName(), arg[1], Utils.buildMessage(arg, 2));
			sm(sender, "MAIL_SEND");
			break;
		case "read":
			if(!(sender instanceof Player)){
				sm(sender, "PLAYER_ONLY");
				return true;
			}
			HashMap<String, String> mails = api.getMails(sender.getName());
			if(mails.isEmpty()){
				sm(sender, "MAIL_EMPTY");
				return true;
			}
			for(Entry<String, String> e : mails.entrySet()){
				sm(sender, "MAIL_FORMAT", "%SENDER%", e.getKey(), "%MESSAGE%", e.getValue());
			}
			sm(sender, "MAIL_CLEAR");
			break;
		case "clear":
			if(!(sender instanceof Player)){
				sm(sender, "PLAYER_ONLY");
				return true;
			}
			api.clearMails(sender.getName());
			sm(sender, "MAIL_CLEARED");
			break;
		}
		return true;
	}

}
