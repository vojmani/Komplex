package cz.vojtamaniak.komplex.commands;

import java.util.Iterator;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cz.vojtamaniak.komplex.BanInfo;
import cz.vojtamaniak.komplex.BanType;
import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandCheckban extends ICommand {
	
	public CommandCheckban(Komplex plg){
		super(plg);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("checkban"))
			return false;
		
		if(!sender.hasPermission("komplex.checkban")){
			sendNoPermission(sender);
			return true;
		}
		
		if(args.length < 1){
			sendWrongUsageMessage(sender, cmd);
			return true;
		} 
		
		List<BanInfo> list = api.getPlayersBanRecords(args[0]);
		if(list.isEmpty()){
			sm(sender, "CHECKBAN_NO_RECORDS", "%NICK%", args[0]);
			return true;
		}
		
		sm(sender, "CHECKBAN", "%NICK%", args[0], "%COUNT%", String.valueOf(list.size()));
		Iterator<BanInfo> i = list.iterator();
		while(i.hasNext()){
			BanInfo info = i.next();
			if(info.getType() == BanType.TEMPBAN || info.getType() == BanType.TEMPJAIL)
				sm(sender, "CHECKBAN_LINE_TB", "%TYPE%", info.getType().toCode(), "%REASON%", info.getReason(), "%TEMPTIME%", Utils.dateFormat(info.getTemptime()), "%ADMIN%", info.getAdmin(), "%TIME%", Utils.dateFormat(info.getTime()));
			else if(info.getType() == BanType.KICK || info.getType() == BanType.UNBAN || info.getType() == BanType.WARN)
				sm(sender, "CHECKBAN_LINE_WARN", "%TYPE%", info.getType().toCode(), "%REASON%", info.getReason(), "%ADMIN%", info.getAdmin(), "%TIME%", Utils.dateFormat(info.getTime()));
			else
				sm(sender, "CHECKBAN_LINE", "%TYPE%", info.getType().toCode(), "%REASON%", info.getReason(), "%ADMIN%", info.getAdmin(), "%TIME%", Utils.dateFormat(info.getTime()));
		}
		return true;
	}

}
