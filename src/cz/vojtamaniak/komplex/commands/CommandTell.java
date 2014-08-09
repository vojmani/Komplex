package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandTell extends ICommand {

	public CommandTell(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(!cmd.getName().equalsIgnoreCase("tell"))
			return false;
		
		if(!sender.hasPermission("komplex.tell")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(arg.length <= 1){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return true;
		}
		
		Player player = Bukkit.getPlayer(arg[0]);
		if(player == null){
			sm(sender, "PLAYER_OFFLINE");
			return true;
		}
		
		if(player.hasPermission("komplex.ignore.bypass") && api.getIgnoredPlayers(player.getName()).contains(sender.getName().toLowerCase())){
			sm(sender, "IGNORE_ADD_BYPASS", "%PLAYER%", sender.getName());
			api.getIgnoredPlayers(player.getName()).remove(sender.getName().toLowerCase());
		}
		if(api.getIgnoredPlayers(player.getName()).contains(sender.getName().toLowerCase())){
			return true;
		}
		
		if(api.getVanish(player.getName()) && !sender.hasPermission("komplex.vanish.bypass")){
			sm(sender, "PLAYER_OFFLINE");
			return true;
		}
		
		String message = Utils.buildMessage(arg, 1);
		sm(player, "TELL_FORMAT_WHISPER", "%SENDER%", sender.getName(), "%MESSAGE%", message);
		sm(sender, "TELL_FORMAT_SELF", "%RECEIVER%", player.getName(), "%MESSAGE%", message);
		api.setLastPMSender(player.getName(), sender);
		if(sender instanceof Player){
			api.setLastPMSender(sender.getName(), player);
		}else{
			api.setLastConsolePM(sender);
		}
		return true;
	}

}
