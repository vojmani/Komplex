package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.BanInfo;
import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandTempmute extends ICommand {

	public CommandTempmute(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("tempmute"))
			return false;
		
		if(!sender.hasPermission("komplex.tempmute")){
			sendNoPermission(sender);
			return true;
		}
		
		if(args.length < 3){
			sendWrongUsageMessage(sender, cmd);
			return true;
		}
		
		if(!Utils.isInt(args[1])){
			sm(sender, "TEMPBAN_AMOUNT_INT");
			return true;
		}
		
		if(api.isPermaMuted(args[0])){
			sm(sender, "TEMPMUTE_PERMAMUTED");
			return true;
		}
		
		String name = args[0];
		String admin = "server";
		String reason = "Neuveden";
		long bantime = Utils.toMillis(Integer.parseInt(args[1]), args[2]);
		long temptime = bantime + System.currentTimeMillis();
		
		if(bantime == -1){
			sendWrongUsageMessage(sender, cmd);
			return true;
		}
		
		if(args.length >= 4){
			reason = Utils.buildMessage(args, 3);
		}
		
		if(sender instanceof Player)
			admin = sender.getName();
		
		if(api.isTempmuted(args[0])){
			BanInfo info = api.getTempmute(args[0]);
			reason = info.getReason() +", "+ reason;
			if(!info.getAdmin().equals(admin)){
				admin = info.getAdmin() +", "+ admin;
			}
			temptime = info.getTemptime() + bantime;
			api.unmutePlayer(args[0], "server", false);
			sm(sender, "TEMPMUTE_ALREADY_EXTEND", "%NICK%", args[0]);
		}
		
		api.tempMutePlayer(name, admin, reason, temptime);
		bm("TEMPMUTE_BROADCAST", "", "%NICK%", name, "%ADMIN%", sender.getName(), "%TEMPTIME%", Utils.dateFormat(temptime), "%REASON%", reason);
		api.addNotice(name, gm("TEMPMUTE_NOTICE", "%ADMIN%", sender.getName(), "%TEMPTIME%", Utils.dateFormat(temptime), "%REASON%", reason));
		return true;
	}

}
