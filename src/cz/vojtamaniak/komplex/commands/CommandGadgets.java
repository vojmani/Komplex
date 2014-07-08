package cz.vojtamaniak.komplex.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class CommandGadgets extends ICommand {

	private FileConfiguration conf;
	
	public CommandGadgets(Komplex plg) {
		super(plg);
		conf = plg.getConfigManager().getConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("gadgets"))
			return false;
		
		if(args.length == 0){
			list(sender);
		}else{
			switch(args[0].toLowerCase()){
			case "add":
				add(sender, args, cmd);
				break;
			case "remove":
				remove(sender, args, cmd);
				break;
			default:
				sendWrongUsageMessage(sender, cmd);
				break;
			}
		}
		return true;
	}
	
	private void add(CommandSender sender, String[] args, Command cmd) {
		if(!sender.hasPermission("komplex.gadgets.add")){
			sendNoPermission(sender);
			return;
		}
		
		if(args.length < 3){
			sendWrongUsageMessage(sender, cmd);
			return;
		}
		
		if(!conf.contains("gadgets."+args[2]+".MaterialName")){
			sm(sender, "GADGETS_INVALID_SLOT");
			return;
		}
		
		api.addGadget(args[1], (Integer.parseInt(args[2]) - 1));
		sm(sender, "GADGETS_SUCCESS", "%NICK%", args[1]);
		
		api.addNotice(args[1], gm("GADGETS_WHISPER", "%ADMIN%", sender.getName()));
	}

	private void list(CommandSender sender){
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return;
		}
		Player player = (Player)sender;
		
		if(!player.hasPermission("komplex.gadgets")){
			sendNoPermission(player);
			return;
		}
		List<Integer> gadgetsList = api.getPlayerGadgets(player.getName());
		Inventory gadgetsInv = Bukkit.createInventory(null, 54, "Odznaky");
		
		int slot = 0;
		while(slot < 54){
			String matName = "";
			String itemName = "";
			List<String> itemLores = null;
			if(!gadgetsList.contains(slot)){
				matName = conf.getString("gadgets.locked.MaterialName");
				itemName = conf.getString("gadgets.locked.ItemName");
				itemLores = conf.getStringList("gadgets.locked.ItemLores");
			}else{
				matName = conf.getString("gadgets."+slot+".MaterialName");
				itemName = conf.getString("gadgets."+slot+".ItemName");
				itemLores = conf.getStringList("gadgets."+slot+".ItemLores");
			}
			ItemStack is = new ItemStack(Material.matchMaterial(matName), 1);
			ItemMeta im = is.getItemMeta();
			
			im.setDisplayName(itemName);
			im.setLore(itemLores);
			
			is.setItemMeta(im);
			
			gadgetsInv.setItem(slot, is);
			++slot;
		}
		player.openInventory(gadgetsInv);
	}
	
	private void remove(CommandSender sender, String[] args, Command cmd) {
		if(!sender.hasPermission("komplex.gadgets.remove")){
			sendNoPermission(sender);
			return;
		}
		
		if(args.length < 3){
			sendWrongUsageMessage(sender, cmd);
			return;
		}
		
		if(!Utils.isInt(args[2]))
			return;
		
		if(!api.hasGadget(args[1], Integer.parseInt(args[2]))){
			sm(sender, "GADGETS_REMOVE_NOT_HAVE");
			return;
		}
		
		api.addGadget(args[1], (Integer.parseInt(args[2]) - 1));
		sm(sender, "GADGETS_SUCCESS_REMOVE", "%NICK%", args[1]);
	}
}
