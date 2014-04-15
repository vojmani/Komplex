package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandFeed extends ICommand {

	public CommandFeed(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(!cmd.getName().equalsIgnoreCase("feed"))
			return false;
		
		if(arg.length > 0){
			feedOther(sender, arg);
		}else{
			feedSelf(sender);
		}
		return true;
	}
	
	private void feedSelf(CommandSender sender){
		if(!sender.hasPermission("komplex.feed")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return;
		}
		
		Player player = (Player) sender;
		player.setFoodLevel(20);
		sm(sender, "FEED_SELF");
		player.playSound(player.getLocation(), Sound.BURP, 1.0F, 1.0F);
	}
	
	private void feedOther(CommandSender sender, String[] arg){		
		if(!sender.hasPermission("komplex.feed.other")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		Player player = Bukkit.getPlayer(arg[0]);
		
		if(player == null){
			sm(sender, "PLAYER_OFFLINE");
			return;
		}
		
		player.setFoodLevel(20);
		sm(player, "FEED_OTHER_WHISPER", "%NICK%", sender.getName());
		sm(sender, "FEED_OTHER", "%NICK%", player.getName());
		player.playSound(player.getLocation(), Sound.BURP, 1.0F, 1.0F);
	} 
}