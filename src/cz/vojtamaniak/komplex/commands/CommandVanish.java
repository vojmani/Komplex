package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandVanish extends ICommand {

	public CommandVanish(Komplex plg){
		super(plg);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("vanish"))
			return false;
		
		if(args.length == 0){
			vanishSelf(sender);
		}else{
			vanishOther(sender, args);
		}
		return true;
	}
	
	private void vanishSelf(CommandSender sender){
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return;
		}
		
		if(!sender.hasPermission("komplex.vanish")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		boolean vanish = api.getVanish(sender.getName());
		
		sm(sender, vanish ? "VANISH_SELF_OFF" : "VANISH_SELF_ON");
		api.setVanish((Player)sender, !vanish);
	}
	
	private void vanishOther(CommandSender sender, String[] args){
		if(!sender.hasPermission("komplex.vanish.other")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		Player player = Bukkit.getPlayer(args[0]);
		if(player == null){
			sm(sender, "PLAYER_OFFLINE");
			return;
		}
		
		boolean vanish = api.getVanish(player.getName());
		
		sm(sender, vanish ? "VANISH_OTHER_OFF" : "VANISH_OTHER_ON", "%NICK%", player.getName());
		sm(player, vanish ? "VANISH_WHISPER_OFF" : "VANISH_WHISPER_ON", "%NICK%", sender.getName());
		api.setVanish(player, !vanish);
	}
}
