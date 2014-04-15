package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandAdminchat extends ICommand{
	
	public CommandAdminchat(Komplex plg){
		super(plg);
	}

	@Override
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
		
		bm("ADMINCHAT_MESSAGE", "komplex.adminchat", "%NICK%", sender.getName(), "%MESSAGE%", Utils.buildMessage(args, 0));
		return true;
	}
}
