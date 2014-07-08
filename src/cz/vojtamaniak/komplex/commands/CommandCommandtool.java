package cz.vojtamaniak.komplex.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandCommandtool extends ICommand {

	public CommandCommandtool(Komplex plg) {
		super(plg);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if(!cmd.getName().equalsIgnoreCase("commandtool"))
			return false;
		
		if(!sender.hasPermission("komplex.commandtool")){
			sendNoPermission(sender);
			return true;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		Player player = (Player)sender;
		
		Material holdItem = player.getItemInHand().getType();
		
		if(holdItem == Material.AIR){
			sm(sender, "COMMANDTOOL_ITEM_AIR");
			return true;
		}
		
		if(args.length == 0){
			api.removeCommandFromTool(sender.getName(), holdItem);
			sm(sender, "COMMANDTOOL_REMOVE", "%ITEM%", holdItem.name().replaceAll("_", " "));
		}else{
			api.addCommandTool(sender.getName(), holdItem, Utils.buildMessage(args, 0));
			sm(sender, "COMMANDTOOL_ADD", "%ITEM%", holdItem.name().replaceAll("_", " "), "%COMMAND%", Utils.buildMessage(args, 0));
		}
		return true;
	}
}
