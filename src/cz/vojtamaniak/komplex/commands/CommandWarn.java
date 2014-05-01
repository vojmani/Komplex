package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandWarn extends ICommand {

	public CommandWarn(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("warn"))
			return false;
		
		if(!sender.hasPermission("komplex.warn")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(args.length < 2){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return true;
		}
		
		String reason = Utils.buildMessage(args, 1);
		String admin = "server";
		
		if(sender instanceof Player)
			admin = sender.getName();
		
		api.addWarn(args[0], reason, admin);
		bm("WARN_BROADCAST", "", "%NICK%", args[0], "%ADMIN%", admin, "%REASON%", reason);
		
		if(api.getCountOfWarns(args[0]) >= 3){
			Bukkit.dispatchCommand(sender, "tempban "+ args[0] +" 1 day 3x varovan");
			api.clearWarns(args[0]);
		}
		
		Player player = Bukkit.getPlayer(args[0]);
		if(player == null){
			api.addNotice(args[0], gm("WARN_WHISPER", "%ADMIN%", admin, "%REASON%", reason));
		}else{
			sm(player, "WARN_WHISPER", "%ADMIN%", admin, "%REASON%", reason);
		}
		return true;
	}

}
