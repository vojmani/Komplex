package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandTell extends ICommand {

	public CommandTell(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(cmd.getName().equalsIgnoreCase("tell")){
			if(sender.hasPermission("komplex.tell")){
				if(arg.length > 1){
					OfflinePlayer offP = Bukkit.getOfflinePlayer(arg[0]);
					if(offP.isOnline()){
						Player p = (Player)offP;
						String message = Utils.buildMessage(arg, 1);
						p.sendMessage(msgManager.getMessage("TELL_FORMAT_WHISPER").replaceAll("%SENDER%", sender.getName()).replaceAll("%MESSAGE%", message));
						sender.sendMessage(msgManager.getMessage("TELL_FORMAT_SELF").replaceAll("%RECEIVER%", p.getName()).replaceAll("%MESSAGE%", message));
						plg.getUser(p.getName()).setLastPM(sender);
						if(sender instanceof Player){
							plg.getUser(sender.getName()).setLastPM(p);
						}else if(sender instanceof ConsoleCommandSender){
							plg.setLastConsolePM(p);
						}
					}else{
						sender.sendMessage(msgManager.getMessage("PLAYER_OFFLINE"));
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
