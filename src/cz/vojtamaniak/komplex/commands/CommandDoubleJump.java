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
	
	@Override
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
		
		boolean isDoubleJump = api.isDoubleJump(sender.getName());
		
		sm(sender, isDoubleJump ? "DOUBLEJUMP_SELF_OFF" : "DOUBLEJUMP_SELF_ON");
		api.setDoubleJump(sender.getName(), !isDoubleJump);
		((Player)sender).setAllowFlight(!isDoubleJump);
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
		
		boolean isDoubleJump = api.isDoubleJump(player.getName());
		
		sm(sender, isDoubleJump ? "DOUBLEJUMP_OTHER_OFF" : "DOUBLEJUMP_OTHER_ON", "%NICK%", player.getName());
		sm(player, isDoubleJump ? "DOUBLEJUMP_WHISPER_OFF" : "DOUBLEJUMP_WHISPER_ON", "%NICK%", sender.getName());
		api.setDoubleJump(player.getName(), !isDoubleJump);
	}
}
