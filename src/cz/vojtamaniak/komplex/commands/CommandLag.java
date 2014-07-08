package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Lag;

public class CommandLag extends ICommand {

	public CommandLag(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("lag"))
			return false;
		
		if(!sender.hasPermission("komplex.lag")){
			sendNoPermission(sender);
			return true;
		}
		
		double tps = Lag.getTPS();
		double percentage = Math.round((1.0D - tps / 20.0D) * 100.0D);
		long uptime = (System.currentTimeMillis() - plg.getUptime()) / 1000 / 60;
		
		sm(sender, "LAG_TPS", "%TPS%", String.valueOf(tps));
		sm(sender, "LAG_PERCENTAGE", "%PERCENTAGE%", String.valueOf(percentage));
		sm(sender, "LAG_UPTIME", "%UPTIME%", String.valueOf(uptime));
		for(World w : Bukkit.getWorlds()){
			sm(sender, "LAG_WORLD", "%WORLD%", w.getName(), "%ENTITIES%", String.valueOf(w.getEntities().size()), "%CHUNKS%", String.valueOf(w.getLoadedChunks().length));
		}
		return true;
	}

}
