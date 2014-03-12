package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.User;
import cz.vojtamaniak.komplex.Utils;

public class CommandReply extends ICommand {

	public CommandReply(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(cmd.getName().equalsIgnoreCase("reply")){
			if(sender.hasPermission("komplex.reply")){
				if(args.length > 0){
					String message = Utils.buildMessage(args, 0);
					if(sender instanceof Player){
						User user = plg.getUser(sender.getName());
						if(user.getLastPMSender() != null){
							if(user.getLastPMSender() instanceof Player){
								Player PMSender = (Player)user.getLastPMSender();
								if(PMSender.isOnline()){
									PMSender.sendMessage(msgManager.getMessage("TELL_FORMAT_WHISPER").replaceAll("%SENDER%", sender.getName()).replaceAll("%MESSAGE%", message));
									sender.sendMessage(msgManager.getMessage("TELL_FORMAT_SELF").replaceAll("%RECEIVER%", PMSender.getName()).replaceAll("%MESSAGE%", message));
									plg.getUser(PMSender.getName()).setLastPM(sender);
								}else{
									sender.sendMessage(msgManager.getMessage("TELL_PLAYER_OFFLINE"));
								}
							}else if(user.getLastPMSender() instanceof ConsoleCommandSender){
								ConsoleCommandSender ccs = (ConsoleCommandSender)user.getLastPMSender();
								ccs.sendMessage(msgManager.getMessage("TELL_FORMAT_WHISPER").replaceAll("%SENDER%", sender.getName()).replaceAll("%MESSAGE%", message));
								sender.sendMessage(msgManager.getMessage("TELL_FORMAT_SELF").replaceAll("%RECEIVER%", ccs.getName()).replaceAll("%MESSAGE%", message));
								plg.setLastConsolePM(sender);
							}
						}else{
							sender.sendMessage(msgManager.getMessage("TELL_NO_RECIPIENT"));
						}
					}else if(sender instanceof ConsoleCommandSender){
						if(plg.getLastConsolePMSender() != null){
							if(plg.getLastConsolePMSender() instanceof Player){
								Player PMSender = (Player)plg.getLastConsolePMSender();
								if(PMSender.isOnline()){
									PMSender.sendMessage(msgManager.getMessage("TELL_FORMAT_WHISPER").replaceAll("%SENDER%", sender.getName()).replaceAll("%MESSAGE%", message));
									sender.sendMessage(msgManager.getMessage("TELL_FORMAT_SELF").replaceAll("%RECEIVER%", PMSender.getName()).replaceAll("%MESSAGE%", message));
									plg.getUser(PMSender.getName()).setLastPM(sender);
								}else{
									sender.sendMessage(msgManager.getMessage("TELL_PLAYER_OFFLINE"));
								}
							}
						}else{
							sender.sendMessage(msgManager.getMessage("TELL_NO_RECIPIENT"));
						}
					}
				}
			}else{
				sender.sendMessage(msgManager.getMessage("NO_PERMISSION"));
			}
			return true;
		}
		return false;
	}

}
