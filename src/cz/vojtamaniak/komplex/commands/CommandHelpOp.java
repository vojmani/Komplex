package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandHelpOp extends ICommand {

	public CommandHelpOp(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(cmd.getName().equalsIgnoreCase("helpop")){
			if(sender.hasPermission("komplex.helpop")){
				if(arg.length > 0){
					Bukkit.broadcast(msgManager.getMessage("HELPOP_MESSAGE").replaceAll("%NICK%", sender.getName()).replaceAll("%MESSAGE%", Utils.buildMessage(arg, 0)), "komplex.helpop.receive");
					sender.sendMessage(msgManager.getMessage("HELPOP_MESSAGE").replaceAll("%NICK%", sender.getName()).replaceAll("%MESSAGE%", Utils.buildMessage(arg, 0)));
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
