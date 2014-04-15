package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cz.vojtamaniak.komplex.Komplex;

public class CommandDeleteWarp extends ICommand {
	
	public CommandDeleteWarp(Komplex plg){
		super(plg);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if(!cmd.getName().equalsIgnoreCase("deletewarp"))
			return false;
		
		if(!sender.hasPermission("komplex.deletewarp")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(api.getWarpLocation(args[0]) == null){
			sm(sender, "WARP_NOT_EXISTS");
			return true;
		}
		
		api.deleteWarp(args[0]);
		sm(sender, "WARP_DELETED", "%WARP%", args[0]);
		return true;
	}
}
