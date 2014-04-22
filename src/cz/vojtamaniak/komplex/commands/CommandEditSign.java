package cz.vojtamaniak.komplex.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandEditSign extends ICommand {

	public CommandEditSign(Komplex plg) {
		super(plg);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("editsign"))
			return false;
		
		if(!sender.hasPermission("komplex.editsign")){
			sm(sender, "NO_PERMISSION");
			return true;
		}
		
		if(args.length < 1){
			sm(sender, "WRONG_USAGE", "%USAGE%", cmd.getUsage());
			return true;
		}
		
		if(!Utils.isInt(args[0])){
			sm(sender, "EDITSIGN_LINE_INT");
			return true;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return true;
		}
		Player player = (Player)sender;
		
		String message = Utils.buildMessage(args, 1);
		int line = Integer.parseInt(args[0]) - 1;
		Block block = player.getTargetBlock(null, 30);
		
		if(message.length() > 15){
			sm(sender, "EDITSIGN_MAX_LENGTH");
			return true;
		}
		
		if(line < 0 || line > 3){
			sm(sender, "EDITSIGN_LINE_MINMAX");
			return true;
		}
		
		if(block.getType() != Material.SIGN && block.getType() != Material.SIGN_POST && block.getType() != Material.WALL_SIGN){
			sm(sender, "EDITSIGN_NOT_SIGN");
			return true;
		}
		
		Sign sign = (Sign)block.getState();
		
		sign.setLine(line, ChatColor.translateAlternateColorCodes('&', message));
		sign.update();
		sm(sender, "EDITSIGN_SUCCESS");
		return true;
	}

}
