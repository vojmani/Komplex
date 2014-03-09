package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.User;

public class CommandAfk extends ICommand {

	public CommandAfk(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(cmd.getName().equalsIgnoreCase("afk")){
			if(sender instanceof Player){
				User user = plg.getUser(sender.getName());
				if(sender.isOp() || sender.hasPermission("komplex.afk")){
					if(user.isAfk()){
						Bukkit.broadcast(msgManager.getMessage("AFK_LEAVE").replaceAll("%NICK%", sender.getName()), "komplex.messages.onafkleave.receive");
						user.setAfk(false);
					}else{
						Bukkit.broadcast(msgManager.getMessage("AFK_ENTER").replaceAll("%NICK%", sender.getName()), "komplex.messages.onafkenter.receive");
						user.setAfk(true);
					}
				}else{
					sender.sendMessage(msgManager.getMessage("NO_PERMISSION"));
				}
			}
			return true;
		}
		return false;
	}

}
