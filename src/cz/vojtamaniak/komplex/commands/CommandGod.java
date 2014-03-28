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
		
		if(plg.getUser(player.getName()).getGodMode()){
			plg.getUser(player.getName()).setGodMode(false);
			sm(player, "GOD_WHISPER_OFF", "%NICK%", sender.getName());
			sm(sender, "GOD_OTHER_OFF", "%NICK%", player.getName());
		}else{
			plg.getUser(player.getName()).setGodMode(true);
			sm(player, "GOD_WHISPER_ON", "%NICK%", sender.getName());
			sm(sender, "GOD_OTHER_ON", "%NICK%", player.getName());
		}
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
		
		if(plg.getUser(sender.getName()).getGodMode()){
			plg.getUser(sender.getName()).setGodMode(false);
			sm(sender, "GOD_SELF_OFF");
		}else{
			plg.getUser(sender.getName()).setGodMode(true);
			sm(sender, "GOD_SELF_ON");
		}
	}
}