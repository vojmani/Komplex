package cz.vojtamaniak.komplex.commands;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Database;
import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandMail extends ICommand {
	
	private Database database;
	
	public CommandMail(Komplex plg) {
		super(plg);
		this.database = plg.getDB();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(cmd.getName().equalsIgnoreCase("mail")){
			if(sender.hasPermission("komplex.mail")){
				if(arg.length > 0){
				switch(arg[0]){
				case "send":
					database.sendMail(sender.getName(), arg[1], Utils.buildMessage(arg, 2));
					sender.sendMessage(msgManager.getMessage("MAIL_SEND"));
					break;
				case "read":
					if(sender instanceof Player){
						HashMap<String, String> mails = new HashMap<String, String>();
						if(mails.size() <= 0){
							sender.sendMessage(msgManager.getMessage("MAIL_EMPTY"));
						}else{
							for(Entry<String, String> e : mails.entrySet()){
								sender.sendMessage(msgManager.getMessage("MAIL_FORMAT").replaceAll("%SENDER%", e.getKey()).replaceAll("%MESSAGE%", e.getValue()));
							}
							sender.sendMessage(msgManager.getMessage("MAIL_CLEAR"));
						}
					}
					break;
				case "clear":
					if(sender instanceof Player){
						database.clearMails(sender.getName());
						sender.sendMessage(msgManager.getMessage("MAIL_CLEARED"));
					}
					break;
				default:
					sender.sendMessage(msgManager.getMessage("WRONG_USAGE").replaceAll("%USAGE%", cmd.getUsage()));
				}
				}else{
					sender.sendMessage(msgManager.getMessage("WRONG_USAGE").replaceAll("%USAGE%", cmd.getUsage()));
				}
			}else{
				sender.sendMessage(msgManager.getMessage("NO_PERMISSION"));
			}
			return true;
		}
		return false;
	}

}
