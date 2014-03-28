package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import cz.vojtamaniak.komplex.Komplex;

public class CommandHat extends ICommand {

	public CommandHat(Komplex plg) {
		super(plg);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg){
		
		if(!cmd.getName().equalsIgnoreCase("hat"))
			return false;
		
		if(!sender.hasPermission("komplex.hat")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		
		Player player = (Player)sender;
		ItemStack hat = player.getInventory().getHelmet();
		ItemStack hand = player.getItemInHand();
		hand.setAmount(1);
		
		PlayerInventory inv = player.getInventory();
		inv.setHelmet(hand);
		inv.setItemInHand(hat);
		return true;
	}
}