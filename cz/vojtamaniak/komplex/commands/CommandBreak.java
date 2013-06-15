package cz.vojtamaniak.komplex.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import cz.vojtamaniak.komplex.Komplex;

public class CommandBreak extends ICommand implements CommandExecutor {

  public CommandBreak(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(cmd.toString().equalsIgnoreCase("break")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				if(player.getItemOnCursor().getType() == Material.BEDROCK){
					this.breakBedrock(player);
				}else{
					this.breakOtherBlocks(player);
				}
			}
			return true;
		}
		return false;
	}
	
	private void breakOtherBlocks(Player player){
		if(player.isOp() || player.hasPermission("komplex.break")){
			player.setItemOnCursor(new ItemStack(Material.AIR));
		}else{
			player.sendMessage(msgManager.getMessage("NO_PERMISSION"));
		}
	}
	
	private void breakBedrock(Player player){
		if(player.isOp() || player.hasPermission("komplex.break.bedrock")){
			player.setItemOnCursor(new ItemStack(Material.AIR));
		}else{
			player.sendMessage(msgManager.getMessage("NO_PERMISSION"));
		}
	}
}
