package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.BanInfo;
import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandTempban extends ICommand {

	public CommandTempban(Komplex plg){
		super(plg);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("tempban"))
			return false;
		
		if(!sender.hasPermission("komplex.tempban")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(args.length < 3){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return true;
		}
		
		if(!Utils.isInt(args[1])){
			sm(sender, "TEMPBAN_AMOUNT_INT");
			return true;
		}
		
		if(api.isPermaBanned(args[0])){
			sm(sender, "TEMPBAN_PERMABANNED");
			return true;
		}
		
		long bantime = Utils.toMillis(Integer.parseInt(args[1]), args[2]);
		String reason = "Neuveden";
		String admin = "server";
		long temptime = System.currentTimeMillis() + bantime;
		
		if(bantime == -1){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return true;
		}
		
		if(args.length >= 4)
			reason = Utils.buildMessage(args, 3);
		if(sender instanceof Player)
			admin = sender.getName();
		
		if(api.isTempbanned(args[0])){
			BanInfo info = api.getTempBan(args[0]);
			reason = info.getReason() +", "+ reason;
			if(!info.getAdmin().equals(admin)){
				admin = info.getAdmin() +", "+ admin;
			}
			temptime = info.getTemptime() + bantime;
			api.unbanPlayer(args[0], "server", false);
			sm(sender, "TEMPBAN_ALREADY_EXTEND", "%NICK%", args[0]);
		}
		
		String date = Utils.dateFormat(temptime);
		api.tempbanPlayer(args[0], temptime, reason, admin);
		bm("TEMPBAN_BROADCAST", "", "%NICK%", args[0], "%ADMIN%", sender.getName(), "%TEMPTIME%", date, "%REASON%", reason);
		
		Player player = Bukkit.getPlayer(args[0]);
		if(player != null){
			player.kickPlayer(msgManager.getMessage("TEMPBAN_WHISPER").replaceAll("%ADMIN%", sender.getName()).replaceAll("%TEMPTIME%", date).replaceAll("%REASON%", reason));
		}
		return true;
	}

}
