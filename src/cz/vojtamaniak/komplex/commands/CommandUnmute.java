package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.BanInfo;
import cz.vojtamaniak.komplex.BanType;
import cz.vojtamaniak.komplex.Komplex;

public class CommandUnmute extends ICommand {

	public CommandUnmute(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("unmute"))
			return false;
		
		if(!sender.hasPermission("komplex.unmute")){
			sendNoPermission(sender);
			return true;
		}
		
		if(args.length < 1){
			sendWrongUsageMessage(sender, cmd);
			return true;
		}
		
		if(!api.isMuted(args[0])){
			sm(sender, "UNMUTE_NOT_MUTED");
			return true;
		}
		
		//REMOVE
		BanInfo info = null;
		for(BanInfo i : api.getBanCacheUser(args[0])){
			if(i.getType() == BanType.MUTE || i.getType() == BanType.TEMPMUTE){
				info = i;
			}
		}
		
		String name = info.getName();
		String admin = "server";
		
		if(sender instanceof Player)
			admin = sender.getName();
		
		api.unmutePlayer(name, admin, true);
		return true;
	}

}
