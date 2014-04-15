package cz.vojtamaniak.komplex.commands;

import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;

public class CommandSetHome extends ICommand {
	
	public CommandSetHome(Komplex plg){
		super(plg);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String args[]){
		if(!cmd.getName().equalsIgnoreCase("sethome"))
			return false;
		
		if(!sender.hasPermission("komplex.sethome")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		Player player = (Player)sender;
		int coh = api.getCountOfHomes(sender.getName());
		
		if(args.length == 0){
			if((coh != 0)
					&& (sender.hasPermission("komplex.sethome.multiple"))){
				sm(sender, "HOME_SET_NONAME");
				return true;
			}
			
			if(coh == 1)
				api.deleteAllHomes(sender.getName());
			
			Location loc = player.getLocation();
			api.addHome(sender.getName(), "home", loc.getWorld().getName(), (int)loc.getX(), (int)loc.getY(), (int)loc.getZ());
			sm(sender, "HOME_ADDED");
		}else{
			if(!sender.hasPermission("komplex.sethome.multiple")){
				sm(sender, "HOME_ADD_MAX", "%MAX%", "1");
				return true;
			}
			
			Location loc = player.getLocation();
			if(sender.hasPermission("komplex.sethome.multiple.unlimited")){
				api.addHome(sender.getName(), args[0], loc.getWorld().getName(), (int)loc.getX(), (int)loc.getY(), (int)loc.getZ());
				sm(sender, "HOME_ADDED");
			}else{
				int maxHomes = 0;
				for(Entry<String, Object> e : plg.getConfigManager().getConfig().getConfigurationSection("home").getValues(false).entrySet()){
					if(e.getValue() instanceof Integer){
						if(sender.hasPermission(e.getKey())){
							maxHomes = (int)e.getValue();
							break;
						}
					}
				}
				
				if(coh >= maxHomes){
					sm(sender, "HOME_ADD_MAX", "%MAX%", Integer.toString(maxHomes));
					return true;
				}
				
				if(api.getHomeLocation(sender.getName(), args[0]) != null){
					api.deleteHome(sender.getName(), args[0]);
				}
				
				api.addHome(sender.getName(), args[0], loc.getWorld().getName(), (int)loc.getX(), (int)loc.getY(), (int)loc.getZ());
				sm(sender, "HOME_ADDED");
			}
		}
		return true;
	}
}
