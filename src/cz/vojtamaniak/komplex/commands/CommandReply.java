package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandReply extends ICommand {

	public CommandReply(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("reply"))
			return false;
		
		if(!sender.hasPermission("komplex.reply")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(args.length == 0){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return true;
		}
		
		String message = Utils.buildMessage(args, 0);
		if(sender instanceof Player){
			CommandSender lastPMSender = api.getLastPMSender(sender.getName());
			
			if(lastPMSender == null){
				sm(sender, "TELL_NO_RECIPIENT");
				return true;
			}
			
			if(lastPMSender instanceof Player){
				Player lastPMPlayer = (Player)lastPMSender;
				
				if(!lastPMPlayer.isOnline()){
					sm(sender, "TELL_PLAYER_OFFLINE");
					return true;
				}
				
				sm(lastPMPlayer, "TELL_FORMAT_WHISPER", "%SENDER%", sender.getName(), "%MESSAGE%", message);
				sm(sender, "TELL_FORMAT_SELF", "%RECEIVER%", lastPMPlayer.getName(), "%MESSAGE%", message);
				api.setLastPMSender(lastPMSender.getName(), sender);
			}else if(lastPMSender instanceof ConsoleCommandSender){
				sm(lastPMSender, "TELL_FORMAT_WHISPER", "%SENDER%", sender.getName(), "%MESSAGE%", message);
				sm(sender, "TELL_FORMAT_SELF", "%RECEIVER%", lastPMSender.getName(), "%MESSAGE%", message);
				api.setLastConsolePM(sender);
			}
		}else{
			CommandSender lastPMSender = api.getLastConsolePMSender();
			
			if(lastPMSender == null){
				sm(sender, "TELL_NO_RECIPIENT");
				return true;
			}
			
			if(lastPMSender instanceof Player){
				Player lastPMPlayer = (Player)lastPMSender;
				if(!lastPMPlayer.isOnline()){
					sm(sender, "TELL_PLAYER_OFFLINE");
					return true;
				}
				
				sm(lastPMPlayer, "TELL_FORMAT_WHISPER", "%SENDER%", "KONZOLE", "%MESSAGE%", message);
				sm(sender, "TELL_FORMAT_SELF", "%RECEIVER%", lastPMSender.getName(), "%MESSAGE%", message);
				api.setLastPMSender(lastPMSender.getName(), sender);
			}
		}
		return true;
	}

}
