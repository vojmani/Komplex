package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandFly extends ICommand {

	public CommandFly(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg){
		if(!cmd.getName().equalsIgnoreCase("fly"))
			return false;
		
		if(arg.length > 0){
			flyOther(sender, arg);
		}else{
			flySelf(sender);
		}
		return true;
	}
	
	private void flySelf(CommandSender sender){
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return;
		}
		
		if(!sender.hasPermission("komplex.fly")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		Player player = (Player)sender;
		if(player.getAllowFlight()){
			player.setAllowFlight(false);
			sm(sender, "FLY_SELF_OFF");
		}else{
			player.setAllowFlight(true);
			sm(sender, "FLY_SELF_ON");
		}
	}
	
	private void flyOther(CommandSender sender, String[] arg){
		if(!sender.hasPermission("komplex.fly.other")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		Player player = Bukkit.getPlayer(arg[0]);
		
		if(player == null){
			sm(sender, "PLAYER_OFFLINE");
			return;
		}
		
		if(player.getAllowFlight()){
			player.setAllowFlight(false);
			sm(sender, "FLY_OTHER_OFF", "%NICK%", player.getName());
			sm(player, "FLY_WHISPER_OFF", "%NICK%", sender.getName());
		}else{
			player.setAllowFlight(true);
			sm(sender, "FLY_OTHER_ON", "%NICK%", player.getName());
			sm(player, "FLY_WHISPER_ON", "%NICK%", sender.getName());
		}
	}
}