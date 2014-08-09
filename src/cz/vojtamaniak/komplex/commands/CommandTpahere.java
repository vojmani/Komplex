package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.TpaInfo;
import cz.vojtamaniak.komplex.TpaType;

public class CommandTpahere extends ICommand {

	public CommandTpahere(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("tpahere"))
			return false;
		
		if(!sender.hasPermission("komplex.tpahere")){
			sendNoPermission(sender);
			return true;
		}
		
		if(args.length < 1){
			sendWrongUsageMessage(sender, cmd);
			return true;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		Player player = (Player) sender;
		
		Player target = Bukkit.getPlayer(args[0]);
		if(target == null){
			sm(sender, "PLAYER_OFFLINE");
			return true;
		}
		
		if(api.getVanish(target.getName()) && !sender.hasPermission("komplex.vanish.bypass")){
			sm(sender, "PLAYER_OFFLINE");
			return true;
		}
		
		TpaInfo info = new TpaInfo(player, target, TpaType.HERE, System.currentTimeMillis());
		
		api.setLastTpaInfo(target.getName(), info);
		
		sm(sender, "TPA_REQUEST_SENT");
		sm(sender, "TPAHERE_REQUEST");
		return true;
	}

}
