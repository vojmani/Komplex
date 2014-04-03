package cz.vojtamaniak.komplex.commands;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import cz.vojtamaniak.komplex.Komplex;

public class CommandHome extends ICommand {
	
	public CommandHome(Komplex plg){
		super(plg);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if(!cmd.getName().equalsIgnoreCase("home"))
			return false;
		
		if(!sender.hasPermission("komplex.home")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		
		Player player = (Player)sender;
		
		if(args.length == 0){
			Location loc = database.getHomeLocation(sender.getName(), "home");
			int count = database.getCountOfHomes(sender.getName());
			
			if(count == 0){
				sm(sender, "HOME_NOONE");
				return true;
			}
			else if((count == 1) && (loc != null)){
				player.teleport(loc, TeleportCause.COMMAND);
				sm(sender, "HOME_TP_DEFAULT");
			}else{
				StringBuilder sb = new StringBuilder();
				List<String> homes = database.getHomeList(sender.getName());
				
				for(String home : homes){
					sb.append(home + ", ");
				}
				
				sb.replace(sb.length() - 2, sb.length(), "");
				sm(sender, "HOME_LIST", "%HOMES%", sb.toString());
			}
		}else{
			Location loc = database.getHomeLocation(sender.getName(), args[0]);
			if(loc == null){
				sm(sender, "HOME_NOT_EXISTS");
				return true;
			}
			
			player.teleport(loc, TeleportCause.COMMAND);
			sm(sender, "HOME_TP", "%HOME%", args[0]);
		}
		return true;
	}
}
