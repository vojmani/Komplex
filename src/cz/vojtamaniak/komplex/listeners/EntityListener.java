package cz.vojtamaniak.komplex.listeners;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import cz.vojtamaniak.komplex.Komplex;

public class EntityListener extends IListener {

	public EntityListener(Komplex plg) {
		super(plg);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDamage(EntityDamageEvent e){
		if(e.getEntityType() == EntityType.PLAYER){
			Player player = (Player)e.getEntity();
			if(api.isGod(player.getName()) && (e.getCause() != DamageCause.VOID && e.getCause() != DamageCause.SUICIDE)){
				player.setFireTicks(0);
				e.setCancelled(true);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOW)
	public void onEntityExplode(EntityExplodeEvent e){
		for(Block b : e.blockList()){
			if(b.getType() == Material.TNT)
				return;
			float x = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
			float y = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
			float z = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
			
			FallingBlock fblock = b.getLocation().getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
			fblock.setDropItem(false);
			fblock.setVelocity(new Vector(x, y, z));
			b.setType(Material.AIR);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOW)
	public void onEntityChangeBlock(EntityChangeBlockEvent e){
		if(e.getEntityType() == EntityType.FALLING_BLOCK){
			if(plg.getFallingblocks().contains(e.getEntity().getUniqueId())){
				e.setCancelled(true);
				plg.getFallingblocks().remove(e.getEntity().getUniqueId());
				e.getEntity().getWorld().playEffect(e.getEntity().getLocation(), Effect.STEP_SOUND, ((FallingBlock)e.getEntity()).getMaterial().getId(), 30);
			}
		}
	}
}