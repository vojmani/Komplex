package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandMute extends ICommand {

	public CommandMute(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("mute"))
			return false;
		
		if(!sender.hasPermission("komplex.mute")){
			sendNoPermission(sender);
			return true;
		}
		
		if(args.length < 1){
			sendWrongUsageMessage(sender, cmd);
			return true;
		}
		
		if(api.isMuted(args[0])){
			sm(sender, "MUTE_ALREADY");
			return true;
		}
		
		String name = args[0];
		String reason = "Neuveden";
		String admin = "server";
		
		if(args.length > 1)
			reason = Utils.buildMessage(args, 1);
		if(sender instanceof Player)
			admin = sender.getName();
		
		api.mutePlayer(name, admin, reason);
		bm("MUTE_BROADCAST", "", "%NICK%", name, "%ADMIN%", admin, "%REASON%", reason);
		api.addNotice(args[0], gm("MUTE_NOTICE", "%ADMIN%", admin, "%REASON%", reason));
		return true;
	}

}
