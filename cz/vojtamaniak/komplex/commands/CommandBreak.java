package cz.vojtamaniak.komplex.commands;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import cz.vojtamaniak.komplex.Komplex;

public class CommandBreak extends ICommand implements CommandExecutor {

	public CommandBreak(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(cmd.getName().equalsIgnoreCase("break")){
			if(sender instanceof Player){
				Player player = (Player) sender;
				Block target = player.getTargetBlock(null, 30);
				if(target.getType() == Material.BEDROCK){
					this.breakBedrock(player, target);
				}else{
					this.breakOtherBlocks(player, target);
				}
			}
			return true;
		}
		return false;
	}
	
	private void breakOtherBlocks(Player player, Block target){
		if(player.isOp() || player.hasPermission("komplex.break")){
			BlockBreakEvent e = new BlockBreakEvent(target, player);
			Bukkit.getPluginManager().callEvent(e);
			if(!e.isCancelled()){
				target.setType(Material.AIR);
			}
			target.setType(Material.AIR);
		}else{
			player.sendMessage(msgManager.getMessage("NO_PERMISSION"));
		}
	}
	
	private void breakBedrock(Player player, Block target){
		if(player.isOp() || player.hasPermission("komplex.break.bedrock")){
			BlockBreakEvent e = new BlockBreakEvent(target, player);
			Bukkit.getPluginManager().callEvent(e);
			if(!e.isCancelled()){
				target.setType(Material.AIR);
			}
		}else{
			player.sendMessage(msgManager.getMessage("NO_PERMISSION_BEDROCK"));
		}
	}
}
