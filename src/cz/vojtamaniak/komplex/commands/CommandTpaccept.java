package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.TpaInfo;

public class CommandTpaccept extends ICommand {

	public CommandTpaccept(Komplex plg) {
		super(plg);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("tpaccept"))
			return false;
		
		if(!sender.hasPermission("komplex.tpaccept")){
			sendNoPermission(sender);
			return true;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		
		Player player = (Player) sender;
		
		TpaInfo info = api.getLastTpaInfo(player.getName());
		
		if(info == null){
			sm(sender, "TPA_NO_REQUEST");
			return true;
		}
		
		if((info.getTime() + 120000) <= System.currentTimeMillis()){
			sm(sender, "TPACCEPT_REQUEST_EXPIRED");
			return true;
		}
		Player requester = info.getRequester();
		Player receiver = info.getTarget();
		
		if(!requester.isOnline()){
			sm(sender, "TPACCEPT_TARGET_OFFLINE", "%NICK%", requester.getName());
			return true;
		}
		
		switch(info.getType()){
			case TPA:				
				requester.teleport(receiver);
				sm(requester, "TPACCEPT_ACCEPTED");
				sm(receiver, "TPACCEPT_ACCEPTED");
				break;
			case HERE:
				receiver.teleport(requester);
				sm(requester, "TPACCEPT_ACCEPTED");
				sm(receiver, "TPACCEPT_ACCEPTED");
				break;
			case ALL:
				receiver.teleport(requester);
				sm(receiver, "TPACCEPT_ACCEPTED");
				break;
		}
		api.setLastTpaInfo(sender.getName(), null);
		return true;
	}

}
