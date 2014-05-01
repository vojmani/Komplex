package cz.vojtamaniak.komplex.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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

	private List<UUID> fbs;
	
	public EntityListener(Komplex plg) {
		super(plg);
		this.fbs = new ArrayList<UUID>();
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
		if(!plg.getConfigManager().getConfig().getBoolean("explodes-fallingblock"))
			return;
			
		for(Block b : e.blockList()){
			if(b.getType() == Material.TNT)
				continue;
			/*float x = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
			float y = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
			float z = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));*/

			float x = random(-0.5F, 0.5F);
			float y = random(0.5F, 1.0F);
			float z = random(-0.5F, 0.5F);
			/*double x = Math.random();
			double y = Math.random();
			double z = Math.random();*/
			
			/*double x = b.getLocation().getX() - e.getEntity().getLocation().getX();
			double y = b.getLocation().getY() - e.getEntity().getLocation().getY() + 0.5;
			double z = b.getLocation().getZ() - e.getEntity().getLocation().getZ();*/
			
			FallingBlock fblock = b.getLocation().getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
			fblock.setDropItem(false);
			fblock.setVelocity(new Vector(x, y, z));
			b.setType(Material.AIR);
			fbs.add(fblock.getUniqueId());
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onEntityChangeBlock(EntityChangeBlockEvent e){
		if(!plg.getConfigManager().getConfig().getBoolean("explodes-fallingblock"))
			return;
		
		if(e.getEntity() instanceof FallingBlock){
			FallingBlock fblock = (FallingBlock)e.getEntity();
			Iterator<UUID> i = fbs.iterator();
			while(i.hasNext()){
				UUID uuid = i.next();
				if(fblock.getUniqueId().equals(uuid)){
					e.setCancelled(true);
					e.getBlock().getWorld().playEffect(e.getBlock().getLocation(), Effect.STEP_SOUND, fblock.getMaterial());
					i.remove();
					fblock.remove();
					//e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(fblock.getMaterial()));
				}
			}
		}
	}
	
	private float random(float min, float max){
		Random rand = new Random();
		return rand.nextFloat() * (max - min) + min;
	}
}