package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.User;

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
		
		User user = plg.getUser(sender.getName());
		if(user.isAfk()){
			bm("AFK_LEAVE", "komplex.message.afk", "%NICK%", sender.getName());
			user.setAfk(false);
		}else{
			bm("AFK_ENTER", "komplex.message.afk", "%NICK%", sender.getName());
			user.setAfk(true);
		}
		return true;
	}

}
