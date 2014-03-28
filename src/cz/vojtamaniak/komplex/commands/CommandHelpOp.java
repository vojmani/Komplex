package cz.vojtamaniak.komplex.commands;

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
		if(!cmd.getName().equalsIgnoreCase("helpop"))
			return false;
		
		if(!sender.hasPermission("komplex.helpop")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(arg.length == 0){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return true;
		}
		
		bm("HELPOP_MESSAGE", "komplex.helpop.receive", "%NICK%", sender.getName(), "%MESSAGE%", Utils.buildMessage(arg, 0));
		return true;
	}

}
