package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandHeal extends ICommand implements CommandExecutor {

  public CommandHeal(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(cmd.getName().equalsIgnoreCase("heal")){
			if(arg.length > 0){
				healOther(sender, arg);
			}else{
				healSelf(sender);
			}
			return true;
		}
		return false;
	}

	private void healSelf(CommandSender sender) {
		if(sender instanceof Player){
			Player player = (Player)sender;
			if(sender.isOp() || sender.hasPermission("komplex.heal")){
				player.setHealth(player.getMaxHealth());
				player.sendMessage(msgManager.getMessage("HEAL_SELF"));
			}else{
				player.sendMessage(msgManager.getMessage("NO_PERMISSION"));
			}
		}
	}

	private void healOther(CommandSender sender, String[] arg) {
		OfflinePlayer offP = Bukkit.getOfflinePlayer(arg[0]);
		if(offP.isOnline()){
			Player player = (Player)offP;
			if(sender.isOp() || sender.hasPermission("komplex.heal.other")){
				player.setHealth(player.getMaxHealth());
				player.sendMessage(msgManager.getMessage("HEAL_WHISPER").replaceAll("%NICK%", sender.getName()));
				sender.sendMessage(msgManager.getMessage("HEAL_OTHER").replaceAll("%NICK%", player.getName()));
			}else{
				sender.sendMessage(msgManager.getMessage("NO_PERMISSION"));
			}
		}else{
			sender.sendMessage(msgManager.getMessage("PLAYER_OFFLINE"));
		}
	}

}
