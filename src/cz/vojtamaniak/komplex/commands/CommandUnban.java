package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.BanInfo;
import cz.vojtamaniak.komplex.BanType;
import cz.vojtamaniak.komplex.Komplex;

public class CommandUnban extends ICommand {
	
	public CommandUnban(Komplex plg){
		super(plg);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("unban"))
			return false;
		
		if(!sender.hasPermission("komplex.unban")){
			sendNoPermission(sender);
			return true;
		}
		
		if(args.length < 1){
			sendWrongUsageMessage(sender, cmd);
			return true;
		}
		
		if(!api.isBanned(args[0])){
			sm(sender, "UNBAN_NOT_BANNED");
			return true;
		}
		
		BanInfo info = null;
		for(BanInfo i : api.getBanCacheUser(args[0])){
			if(i.getType() == BanType.BAN || i.getType() == BanType.TEMPBAN){
				info = i;
			}
		}
		
		String name = info.getName();
		String admin = "server";
		
		if(sender instanceof Player)
			admin = sender.getName();
		
		api.unbanPlayer(name, admin);
		bm("UNBAN_BROADCAST", "", "%NICK%", name, "%ADMIN%", admin);
		return true;
	}

}
