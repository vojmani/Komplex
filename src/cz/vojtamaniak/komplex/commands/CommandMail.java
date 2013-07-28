package cz.vojtamaniak.komplex.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.StorageManager;
import cz.vojtamaniak.komplex.Utils;

public class CommandMail extends ICommand {
	
	private StorageManager storManager;
	
	public CommandMail(Komplex plg) {
		super(plg);
		this.storManager = plg.getStorageManager();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(cmd.getName().equalsIgnoreCase("mail")){
			if(sender.isOp() || sender.hasPermission("komplex.mail")){
				if(arg.length > 0){
				switch(arg[0]){
				case "send":
					storManager.sendMail(sender.getName(), arg[1], Utils.buildMessage(arg, 2));
					sender.sendMessage(msgManager.getMessage("MAIL_SEND"));
					break;
				case "read":
					if(sender instanceof Player){
						List<String> mails = storManager.getMails(sender.getName());
						if(mails != null && !mails.isEmpty()){
							for(String mail : mails){
								sender.sendMessage(mail);
							}
							sender.sendMessage(msgManager.getMessage("MAIL_CLEAR"));
						}else{
							sender.sendMessage(msgManager.getMessage("MAIL_EMPTY"));
						}
					}
					break;
				case "clear":
					if(sender instanceof Player){
						storManager.clearMails(sender.getName());
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
