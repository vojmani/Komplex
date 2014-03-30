package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandPtime extends ICommand {

	public CommandPtime(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(!cmd.getName().equalsIgnoreCase("ptime"))
			return false;
		
		if(arg.length == 0){
			getSelfTime(sender);
			return true;
		}
		
		switch(arg[0]){
		case "set":
			if(arg.length > 1 && arg.length < 3){
				switch(arg[1]){
				case "day":
					setSelfTime(sender, 0);
					break;
				case "night":
					setSelfTime(sender, 12500);
					break;
				default:
					setSelfTime(sender, Integer.parseInt(arg[1]));
					break;
				}
			}else if(arg.length > 2){
				switch(arg[1]){
				case "day":
					setOtherTime(sender, arg, 0);
					break;
				case "night":
					setOtherTime(sender, arg, 12500);
					break;
				default:
					setOtherTime(sender, arg, Integer.parseInt(arg[1]));
					break;
				}
			}else{
				sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			}
			break;
		case "reset":
			if(arg.length > 1){
				resetOther(sender, arg);
			}else{
				resetSelf(sender);
			}
			break;
		default:
			getOtherTime(sender, arg);
			break;
		}
		return true;
	}
	
	private void getOtherTime(CommandSender sender, String[] arg) {
		if(!sender.hasPermission("komplex.ptime.get.other")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		Player player = Bukkit.getPlayer(arg[0]);
		if(player == null){
			sm(sender, "PLAYER_OFFLINE");
			return;
		}
		
		sm(sender, "PTIME_GET_OTHER", "%NICK%", player.getName(), "%TIME%", ""+player.getPlayerTime());
	}

	private void getSelfTime(CommandSender sender) {
		if(!sender.hasPermission("komplex.ptime.get")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return;
		}
		
		sm(sender, "PTIME_GET_SELF", "%TIME%", ""+((Player)sender).getPlayerTime());
	}
	
	private void resetSelf(CommandSender sender){
		if(!sender.hasPermission("komplex.ptime.reset")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return;
		}
		
		((Player)sender).setPlayerTime(((Player)sender).getWorld().getTime(), true);
		sm(sender, "PTIME_RESET_SELF");
	}
	
	private void resetOther(CommandSender sender, String[] arg){
		if(!sender.hasPermission("komplex.ptime.reset.other")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		Player player = Bukkit.getPlayer(arg[1]);
		if(player == null){
			sm(sender, "PLAYER_OFFLINE");
			return;
		}
		
		player.setPlayerTime(player.getWorld().getTime(), true);
		sm(sender, "PTIME_RESET_OTHER", "%NICK%", player.getName());
		sm(player, "PTIME_RESET_WHISPER", "%NICK%", sender.getName());
	}
	
	private void setSelfTime(CommandSender sender, int ticks){
		if(!sender.hasPermission("komplex.ptime.set")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return;
		}
		
		((Player)sender).setPlayerTime(ticks, false);
		sm(sender, "PTIME_SET_SELF", "%TIME%", ""+ticks);
	}
	
	private void setOtherTime(CommandSender sender, String[] arg, int ticks){
		if(!sender.hasPermission("komplex.ptime.set.other")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		Player player = Bukkit.getPlayer(arg[2]);
		if(player == null){
			sm(sender, "PLAYER_OFFLINE");
			return;
		}
		
		player.setPlayerTime(ticks, false);
		sm(player, "PTIME_SET_WHISPER", "%NICK%", sender.getName(), "%TIME%", ""+ticks);
		sm(sender, "PTIME_SET_OTHER", "%NICK%", player.getName(), "%TIME%", ""+ticks);
	}
}