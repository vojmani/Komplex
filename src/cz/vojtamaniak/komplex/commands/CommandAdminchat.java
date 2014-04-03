package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandAdminchat extends ICommand{
	
	public CommandAdminchat(Komplex plg){
		super(plg);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if(!cmd.getName().equalsIgnoreCase("adminchat")){
			return false;
		}
		
		if(args.length < 1){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return true;
		}
		
		if(!sender.hasPermission("komplex.adminchat")){
			sm(sender, "NO_PERMISSION");
		}
		
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.hasPermission("komplex.adminchat")){
				sm(p, "ADMINCHAT_MESSAGE", "%NICK%", sender.getName(), "%MESSAGE%", Utils.buildMessage(args, 0));
			}
		}
		return true;
	}
}
