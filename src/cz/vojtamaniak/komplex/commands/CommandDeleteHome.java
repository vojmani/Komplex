package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandDeleteHome extends ICommand {

	public CommandDeleteHome(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("deletehome"))
			return false;
		
		if(!sender.hasPermission("komplex.deletehome")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		
		if(args.length == 0){
			api.deleteHome(sender.getName(), "home");
		}else{
			api.deleteHome(sender.getName(), args[0]);
		}
		return true;
	}

}
