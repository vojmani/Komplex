package cz.vojtamaniak.komplex.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cz.vojtamaniak.komplex.Komplex;

public class CommandDupeIP extends ICommand {
	
	public CommandDupeIP(Komplex plg){
		super(plg);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if(!cmd.getName().equalsIgnoreCase("dupeip"))
			return false;
		
		if(!sender.hasPermission("komplex.dupeip")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(args.length < 1){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return true;
		}
		
		String regex = "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
		if(args[0].matches(regex)){
			List<String> nicks = api.getPlayersByIP(args[0]);
			
			if(nicks.isEmpty()){
				sm(sender, "DUPEIP_IP_EMPTY");
				return true;
			}
			
			StringBuilder sb = new StringBuilder();
			for(String nick : nicks){
				sb.append(nick +", ");
			}
			
			sb.replace(sb.length() - 2, sb.length(), "");
			sm(sender, "DUPEIP_IP", "%IP%", args[0], "%NICKS%", sb.toString());
		}else{
			String ip = api.getPlayersIP(args[0]);
			if(ip == null){
				sm(sender, "DUPEIP_NICK_NOT_EXISTS");
				return true;
			}
			List<String> nicks = api.getPlayersByIP(ip);
			if(nicks.isEmpty()){
				sm(sender, "DUPEIP_IP_EMPTY");
				return true;
			}
			
			StringBuilder sb = new StringBuilder();
			for(String nick : nicks){
				sb.append(nick +", ");
			}
			
			sb.replace(sb.length() - 2, sb.length(), "");
			sm(sender, "DUPEIP_IP", "%IP%", ip, "%NICKS%", sb.toString());
		}
		return true;
	}
}
