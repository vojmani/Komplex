package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandWorkbench extends ICommand implements CommandExecutor {

	public CommandWorkbench(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(cmd.getName().equalsIgnoreCase("workbench")){
			if(sender instanceof Player){
				Player player = (Player)sender;
				if(player.isOp() || player.hasPermission("komplex.workbench")){
					player.openWorkbench(null, true);
				}else{
					sender.sendMessage(msgManager.getMessage("NO_PERMISSION"));
				}
			}
			return true;
		}
		return false;
	}

}
