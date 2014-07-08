package cz.vojtamaniak.komplex.commands;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

import cz.vojtamaniak.komplex.Komplex;

public class CommandHeal extends ICommand {

	public CommandHeal(Komplex plg) {
		super(plg);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		if(!cmd.getName().equalsIgnoreCase("heal"))
			return false;
		
		if(arg.length > 0){
			healOther(sender, arg);
		}else{
			healSelf(sender);
		}
		return true;
	}

	private void healSelf(CommandSender sender) {
		if(!sender.hasPermission("komplex.heal")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		if(!(sender instanceof Player)){
			sm(sender, "PLAYER_ONLY");
			return;
		}
		
		Player player = (Player) sender;
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.setFireTicks(0);
		sm(player, "HEAL_SELF");
		if(plg.getConfigManager().getConfig().getBoolean("feed-heal-effects")){
			playEffect(player.getLocation());
		}
	}

	private void healOther(CommandSender sender, String[] arg) {
		if(!sender.hasPermission("komplex.heal.other")){
			sm(sender, "NO_PERMISSION");
			return;
		}
		
		Player player = Bukkit.getPlayer(arg[0]);
		if(player == null){
			sm(sender, "PLAYER_OFFLINE");
			return;
		}
		
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.setFireTicks(0);
		sm(sender, "HEAL_OTHER", "%NICK%", player.getName());
		sm(player, "HEAL_WHISPER", "%NICK%", sender.getName());
		if(plg.getConfigManager().getConfig().getBoolean("feed-heal-effects")){
			playEffect(player.getLocation());
		}
	}

	private void playEffect(Location loc){
		/*Entity e = loc.getWorld().spawnEntity(loc, EntityType.WOLF);
		e.playEffect(EntityEffect.WOLF_HEARTS);
		e.remove();*/
		PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.WORLD_PARTICLES);
		packet.getModifier().writeDefaults();
		packet.getStrings().write(0, "heart");
		packet.getFloat().write(0, (float)loc.getX());
		packet.getFloat().write(1, (float)loc.getY());
		packet.getFloat().write(2, (float)loc.getZ());
		packet.getIntegers().write(0, 5);
		
		ProtocolLibrary.getProtocolManager().broadcastServerPacket(packet, loc, 20);
	}
}