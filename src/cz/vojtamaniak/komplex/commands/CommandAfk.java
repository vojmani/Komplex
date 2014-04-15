package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandAfk extends ICommand {

	public CommandAfk(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(!cmd.getName().equalsIgnoreCase("afk"))
			return false;
		
		if(!sender.hasPermission("komplex.afk")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		
		boolean isAfk = api.isAfk(sender.getName());
		
		bm(isAfk ? "AFK_LEAVE" : "AFK_ENTER", "komplex.message.afk", "%NICK%", sender.getName());
		api.setAfk(sender.getName(), !isAfk);
		return true;
	}

}
