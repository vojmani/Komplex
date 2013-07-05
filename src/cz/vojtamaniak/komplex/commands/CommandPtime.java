package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandPtime extends ICommand implements CommandExecutor{

	public CommandPtime(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(cmd.getName().equalsIgnoreCase("ptime")){
			if(arg.length > 0){
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
						sender.sendMessage(msgManager.getMessage("WRONG_USAGE").replaceAll("%USAGE%", cmd.getUsage()));
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
			}else{
				getSelfTime(sender);
			}
			return true;
		}
		return false;
	}
	
	private void getOtherTime(CommandSender sender, String[] arg) {
		OfflinePlayer offP = Bukkit.getOfflinePlayer(arg[0]);
		if(offP.isOnline()){
			Player player = (Player)offP;
			if(sender.isOp() || sender.hasPermission("komplex.ptime.get.other")){
				sender.sendMessage(msgManager.getMessage("PTIME_GET_OTHER").replaceAll("%NICK%", player.getName()).replaceAll("%TIME%", ""+player.getPlayerTime()));
			}else{
				sender.sendMessage(msgManager.getMessage("NO_PERMISSION"));
			}
		}else{
			sender.sendMessage(msgManager.getMessage("PLAYER_OFFLINE"));
		}
	}

	private void getSelfTime(CommandSender sender) {
		if(sender instanceof Player){
			Player player = (Player)sender;
			if(player.isOp() || player.hasPermission("komplex.ptime.get")){
				player.sendMessage(msgManager.getMessage("PTIME_GET_SELF").replaceAll("%TIME%", ""+player.getPlayerTime()));
			}else{
				player.sendMessage(msgManager.getMessage("NO_PERMISSION"));
			}
		}
	}
	
	private void resetSelf(CommandSender sender){
		if(sender instanceof Player){
			Player player = (Player)sender;
			if(player.isOp() || player.hasPermission("komplex.ptime.reset")){
				player.setPlayerTime(player.getWorld().getTime(), true);
				player.sendMessage(msgManager.getMessage("PTIME_RESET_SELF"));
			}else{
				player.sendMessage(msgManager.getMessage("NO_PERMISSION"));
			}
		}
	}
	
	private void resetOther(CommandSender sender, String[] arg){
		OfflinePlayer offP = Bukkit.getOfflinePlayer(arg[1]);
		if(offP.isOnline()){
			Player player = (Player)offP;
			if(sender.isOp() || sender.hasPermission("komplex.ptime.reset.other")){
				player.setPlayerTime(player.getWorld().getTime(), true);
				player.sendMessage(msgManager.getMessage("PTIME_RESET_WHISPER").replaceAll("%NICK%", sender.getName()));
				sender.sendMessage(msgManager.getMessage("PTIME_RESET_OTHER").replaceAll("%NICK%", player.getName()));
			}else{
				sender.sendMessage(msgManager.getMessage("NO_PERMISSION"));
			}
		}
	}
	
	private void setSelfTime(CommandSender sender, int ticks){
		if(sender instanceof Player){
			Player player = (Player)sender;
			if(player.isOp() || player.hasPermission("komplex.ptime.set")){
				player.setPlayerTime((long)ticks, false);
				sender.sendMessage(msgManager.getMessage("PTIME_SET_SELF").replaceAll("%TIME%", ""+ticks));
			}else{
				sender.sendMessage(msgManager.getMessage("NO_PERMISSION"));
			}
		}
	}
	
	private void setOtherTime(CommandSender sender, String[] arg, int ticks){
		OfflinePlayer offP = Bukkit.getOfflinePlayer(arg[2]);
		if(offP.isOnline()){
			Player player = (Player)offP;
			if(sender.isOp() || sender.hasPermission("komplex.ptime.set.other")){
				player.setPlayerTime(ticks, false);
				player.sendMessage(msgManager.getMessage("PTIME_SET_WHISPER").replaceAll("%NICK%", sender.getName()).replaceAll("%TIME%", ticks+""));
				sender.sendMessage(msgManager.getMessage("PTIME_SET_OTHER").replaceAll("%NICK%", player.getName()).replaceAll("%TIME%", ticks+""));
			}
		}
	}
}