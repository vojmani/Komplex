package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.TpaInfo;

public class CommandTpdeny extends ICommand {

	public CommandTpdeny(Komplex plg) {
		super(plg);
	}

	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("tpdeny"))
			return false;
		
		if(!sender.hasPermission("komplex.tpdeny")){
			sendNoPermission(sender);
			return true;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		
		TpaInfo info = api.getLastTpaInfo(sender.getName());
		
		if(info == null){
			sm(sender, "TPA_NO_REQUEST");
			return true;
		}
		
		sm(sender, "TPDENY_DENIED");
		api.setLastTpaInfo(sender.getName(), null);
		return true;
	}
}
