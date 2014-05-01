package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import cz.vojtamaniak.komplex.Komplex;

public class CommandItemid extends ICommand {
	
	public CommandItemid(Komplex plg){
		super(plg);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("itemid"))
			return false;
		
		if(!sender.hasPermission("komplex.itemid")){
			sendNoPermission(sender);
			return true;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		
		Player player = (Player)sender;
		
		ItemStack itemInHand = player.getItemInHand();
		String id = "";
		if(itemInHand.getData().getData() != 0){
			id = itemInHand.getTypeId() +":"+itemInHand.getData().getData();
		}else{
			id = String.valueOf(itemInHand.getTypeId());
		}
		sm(sender, "ITEMID", "%ID%", String.valueOf(id));
		return true;
	}

}
