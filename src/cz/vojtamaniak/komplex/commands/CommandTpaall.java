package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.TpaInfo;
import cz.vojtamaniak.komplex.TpaType;

public class CommandTpaall extends ICommand {

	public CommandTpaall(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("tpaall"))
			return false;
		
		if(!sender.hasPermission("komplex.tpaall")){
			sendNoPermission(sender);
			return true;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		Player player = (Player) sender;
		
		for(Player target : Bukkit.getOnlinePlayers()) {			
			if(api.getVanish(target.getName()) && !sender.hasPermission("komplex.vanish.bypass"))
				continue;
			
			TpaInfo info = new TpaInfo(player, target, TpaType.ALL, System.currentTimeMillis());
			
			api.setLastTpaInfo(target.getName(), info);
			
			sm(sender, "TPA_REQUEST_SENT");
			sm(sender, "TPAHERE_REQUEST");
		}
		return true;
	}

}
