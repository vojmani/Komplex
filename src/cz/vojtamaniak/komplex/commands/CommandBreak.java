package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import cz.vojtamaniak.komplex.Komplex;

public class CommandBreak extends ICommand {

	public CommandBreak(Komplex plg) {
		super(plg);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(!cmd.getName().equalsIgnoreCase("break"))
			return false;
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		
		Player player = (Player)sender;
		Block block = player.getTargetBlock(null, 30);
		
		if(block.getType() == Material.BEDROCK)
			breakBedrock(player, block);
		else
			breakOtherBlocks(player, block);		
		return true;
	}
	
	private void breakOtherBlocks(Player player, Block block){		
		if(!player.hasPermission("komplex.break")){
			sm(player, "NO_PERMISSION");
			return;
		}
		
		BlockBreakEvent e = new BlockBreakEvent(block, player);
		Bukkit.getPluginManager().callEvent(e);
		if(!e.isCancelled())
			block.setType(Material.AIR);
	}
	
	private void breakBedrock(Player player, Block block){
		if(!player.hasPermission("komplex.break.bedrock")){
			sm(player, "NO_PERMISSION_BEDROCK");
			return;
		}
		
		BlockBreakEvent e = new BlockBreakEvent(block, player);
		Bukkit.getPluginManager().callEvent(e);
		
		if(!e.isCancelled())
			block.setType(Material.AIR);
	}
}