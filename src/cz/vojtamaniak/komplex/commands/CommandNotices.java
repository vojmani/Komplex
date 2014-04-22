package cz.vojtamaniak.komplex.commands;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandNotices extends ICommand {

	public CommandNotices(Komplex plg){
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if(!cmd.getName().equalsIgnoreCase("notices"))
			return false;
		
		if(args.length == 0 || (args.length != 0 && args[0].equals("-all"))){
			if(!sender.hasPermission("komplex.notices")){
				sm(sender, "NO_PERMISSION");
				return true;
			}
			
			HashMap<Long, String> notices = null;
			if(args.length != 0 && args[0].equals("-all")){
				notices = api.getNotices(sender.getName(), true);
			}else{
				notices = api.getNotices(sender.getName(), false);
			}
			
			if(notices.isEmpty()){
				sm(sender, "NOTICE_LIST_EMPTY");
				return true;
			}
			
			for(Entry<Long, String> e : notices.entrySet()){
				sm(sender, "NOTICE_LIST_FORMAT", "%TIME%", Utils.dateFormat(e.getKey()), "%TEXT%", e.getValue());
			}
			if(args.length == 0 || (args.length != 0 && !args[0].equals("-all"))){
				sm(sender, "NOTICE_MESSAGE_ALL");
				api.setReadNotices(sender.getName());
			}
		}else{
			switch(args[0]){
			case "global":
				addGlobal(sender, args, cmd);
				break;
			case "add":
				add(sender, args, cmd);
				break;
			default:
				sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
				return true;
			}
		}
		return true;
	}
	
	private void addGlobal(CommandSender sender, String[] args, Command cmd){
		if(!sender.hasPermission("komplex.notices.global")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		if(args.length < 2){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return;
		}
		
		api.addGlobalNotice(Utils.buildMessage(args, 1));
		sm(sender, "NOTICE_GLOBAL_ADD");
	}
	
	private void add(CommandSender sender, String[] args, Command cmd){
		if(!sender.hasPermission("komplex.notices.other")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		if(args.length < 3){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return;
		}
		
		api.addNotice(args[1], Utils.buildMessage(args, 2));
		sm(sender, "NOTICE_OTHER_ADD", "%NICK%", args[1]);
	}
}
