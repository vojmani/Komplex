package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandKick extends ICommand {

	public CommandKick(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("kick"))
			return false;
		
		if(!sender.hasPermission("komplex.kick")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(args.length < 1){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return true;
		}
		
		Player player = Bukkit.getPlayer(args[0]);
		String reason = "Neuveden";
		String admin = "server";
		
		if(sender instanceof Player)
			admin = sender.getName();
		if(args.length > 1){
			reason = Utils.buildMessage(args, 1);
		}
		if(player == null){
			sm(sender, "PLAYER_OFFLINE");
			return true;
		}
		
		api.kickPlayer(player.getName(), reason, admin);
		player.kickPlayer(msgManager.getMessage("KICK_WHISPER").replaceAll("%ADMIN%", admin).replaceAll("%REASON%", reason));
		return true;
	}

}
