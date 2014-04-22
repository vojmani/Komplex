package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandGod extends ICommand {

	public CommandGod(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(!cmd.getName().equalsIgnoreCase("god"))
			return false;
		
		if(arg.length > 0){
			godOther(sender, arg);
		}else{
			godSelf(sender);
		}
		return true;
	}
	
	private void godOther(CommandSender sender, String[] arg){
		if(!sender.hasPermission("komplex.god.other")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		Player player = Bukkit.getPlayer(arg[0]);
		if(player == null){
			sm(sender, "PLAYER_OFFLINE");
			return;
		}
		
		boolean god = api.isGod(player.getName());
		sm(player, god ? "GOD_WHISPER_OFF" : "GOD_WHISPER_ON", "%NICK%", sender.getName());
		sm(sender, god ? "GOD_OTHER_OFF" : "GOD_OTHER_ON", "%NICK%", player.getName());
		api.setGod(player.getName(), !god);
	}
	
	private void godSelf(CommandSender sender){
		if(!sender.hasPermission("komplex.god")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return;
		}

		boolean god = api.isGod(sender.getName());
		sm(sender, god ? "GOD_SELF_OFF" : "GOD_SELF_ON");
		api.setGod(sender.getName(), !god);
	}
}