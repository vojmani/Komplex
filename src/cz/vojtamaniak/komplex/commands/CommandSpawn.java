package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import cz.vojtamaniak.komplex.Komplex;

public class CommandSpawn extends ICommand {
	
	public CommandSpawn(Komplex plg){
		super(plg);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if(!cmd.getName().equalsIgnoreCase("spawn"))
			return false;
		
		if(args.length == 0){
			if(!sender.hasPermission("komplex.spawn")){
				sm(sender, "NO_PERMISSION");
				return true;
			}
			
			if(!(sender instanceof Player)){
				sm(sender, "PLAYER_ONLY");
				return true;
			}
			
			((Player)sender).teleport(plg.getSpawnLocation(), TeleportCause.PLUGIN);
			sm(sender, "SPAWN_TP_SELF");
		}else{
			if(!sender.hasPermission("komplex.spawn.other")){
				sm(sender, "NO_PERMISSION");
				return true;
			}
			
			Player player = Bukkit.getPlayer(args[0]);
			
			if(player == null){
				sm(sender, "PLAYER_OFFLINE");
				return true;
			}
			
			player.teleport(plg.getSpawnLocation(), TeleportCause.PLUGIN);
			sm(sender, "SPAWN_TP_OTHER", "%NICK%", player.getName());
			sm(player, "SPAWN_TP_WHISPER", "%NICK%", sender.getName());
		}
		
		return true;
	}
}
