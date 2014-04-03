package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandDoubleJump extends ICommand {
	
	public CommandDoubleJump(Komplex plg){
		super(plg);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if(!cmd.getName().equalsIgnoreCase("doublejump"))
			return false;
		
		if(args.length == 0){
			self(sender);
		}else{
			other(sender, args);
		}
		return true;
	}
	
	private void self(CommandSender sender){
		if(!sender.hasPermission("komplex.doublejump")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return;
		}
		
		if(plg.getUser(sender.getName()).getDoubleJump()){
			plg.getUser(sender.getName()).setDoubleJump(false);
			sm(sender, "DOUBLEJUMP_SELF_OFF");
		}else{
			plg.getUser(sender.getName()).setDoubleJump(true);
			sm(sender, "DOUBLEJUMP_SELF_ON");
		}
	}
	
	private void other(CommandSender sender, String[] args){
		if(!sender.hasPermission("komplex.doublejump.other")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		Player player = Bukkit.getPlayer(args[0]);
		if(player == null){
			sm(sender, "PLAYER_OFFLINE");
			return;
		}
		
		if(plg.getUser(args[0]).getDoubleJump()){
			plg.getUser(args[0]).setDoubleJump(false);
			sm(sender, "DOUBLEJUMP_OTHER_OFF", "%NICK%", player.getName());
			sm(player, "DOUBLEJUMP_WHISPER_OFF", "%NICK%", sender.getName());
			player.setAllowFlight(false);
		}else{
			plg.getUser(args[0]).setDoubleJump(true);
			sm(sender, "DOUBLEJUMP_OTHER_ON", "%NICK%", player.getName());
			sm(player, "DOUBLEJUMP_WHISPER_ON", "%NICK%", sender.getName());
		}
	}
}
