package cz.vojtamaniak.komplex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import cz.vojtamaniak.komplex.Komplex;

public class CommandHat extends ICommand implements CommandExecutor {

	public CommandHat(Komplex plg) {
		super(plg);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg){
		if(cmd.getName().equalsIgnoreCase("hat")){
			if(sender instanceof Player){
				Player player = (Player)sender;
				if(player.isOp() || player.hasPermission("komplex.hat")){
					PlayerInventory inv = player.getInventory();
					ItemStack hat = player.getInventory().getHelmet();
					ItemStack hand = player.getItemInHand();
					hand.setAmount(1);
					inv.remove(hand);
					inv.setHelmet(hand);
					inv.setItemInHand(hat);
					player.sendMessage(msgManager.getMessage("HAT_SUCCESS"));
				}else{
					player.sendMessage(msgManager.getMessage("NO_PERMISSION"));
				}
			}
			return true;
		}
		return false;
	}
}