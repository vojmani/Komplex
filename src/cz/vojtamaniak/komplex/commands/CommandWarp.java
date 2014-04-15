package cz.vojtamaniak.komplex.commands;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import cz.vojtamaniak.komplex.Komplex;

public class CommandWarp extends ICommand {

	public CommandWarp(Komplex plg){
		super(plg);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if(!cmd.getName().equalsIgnoreCase("warp"))
			return false;
		
		if(!sender.hasPermission("komplex.warp")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(args.length == 0){
			List<String> warps = api.getWarpList();
			if(warps.isEmpty()){
				sm(sender, "WARP_LIST_EMPTY");
				return true;
			}
			
			StringBuilder sb = new StringBuilder();
			for(String warp : warps){
				if(sender.hasPermission("komplex.warp."+ warp)){
					sb.append(warp +", ");
				}
			}
			
			sb.replace(sb.length() - 2, sb.length(), "");
			sm(sender, "WARP_LIST", "%WARPS%", sb.toString());
		}else{
			if(!(sender instanceof Player)){
				sm(sender, "PLAYER_ONLY");
				return true;
			}
			
			Location loc = null;
			if((loc = api.getWarpLocation(args[0])) == null){
				sm(sender, "WARP_NOT_EXISTS");
				return true;
			}
			
			((Player)sender).teleport(loc, TeleportCause.COMMAND);
			sm(sender, "WARP_SUCCESS", "%WARP%", args[0]);
		}
		return true;
	}
}
