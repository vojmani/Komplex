package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
		
		long bantime = Utils.toMillis(Integer.parseInt(args[1]), args[2]);
		String reason = "Neuveden";
		String admin = "server";
		long temptime = System.currentTimeMillis() + bantime;
		String date = Utils.dateFormat(temptime);
		
		if(bantime == -1){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return true;
		}
		
		if(args.length > 4)
			reason = Utils.buildMessage(args, 3);
		if(sender instanceof Player)
			admin = sender.getName();
		
		api.tempbanPlayer(args[0], temptime, reason, admin);
		bm("TEMPBAN_BROADCAST", "", "%NICK%", args[0], "%ADMIN%", admin, "%TEMPTIME%", date, "%REASON%", reason);
		
		Player player = Bukkit.getPlayer(args[0]);
		if(player != null){
			player.kickPlayer(msgManager.getMessage("TEMPBAN_WHISPER").replaceAll("%ADMIN%", admin).replaceAll("%TEMPTIME%", date).replaceAll("%REASON%", reason));
		}
		return true;
	}

}
