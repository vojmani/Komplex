package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandBan extends ICommand {

	public CommandBan(Komplex plg){
		super(plg);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("ban"))
			return false;
		
		if(!sender.hasPermission("komplex.ban")){
			sendNoPermission(sender);
			return true;
		}
		
		if(args.length < 1){
			sendWrongUsageMessage(sender, cmd);
			return true;
		}
		
		if(api.isPermaBanned(args[0])){
			sm(sender, "BAN_ALREADY");
			return true;
		}
		
		String name = args[0];
		String reason = "Neuveden";
		String admin = "server";
		
		if(args.length > 1)
			reason = Utils.buildMessage(args, 1);
		if(sender instanceof Player)
			admin = sender.getName();
		
		api.addBan(name, reason, admin);
		bm("BAN_BROADCAST", "", "%NICK%", name, "%ADMIN%", admin, "%REASON%", reason);
		
		Player player = Bukkit.getPlayer(name);
		if(player != null)
			kick(player, "BAN_WHISPER", "%ADMIN%", admin, "%REASON%", reason);
		return true;
	}

}
