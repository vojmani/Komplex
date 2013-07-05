package cz.vojtamaniak.komplex.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import cz.vojtamaniak.komplex.Komplex;

public class EntityListener extends IListener {

	public EntityListener(Komplex plg) {
		super(plg);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDamage(EntityDamageEvent e){
		if(e.getEntityType() == EntityType.PLAYER){
			Player player = (Player)e.getEntity();
			if(plg.getUser(player.getName()).getGodMode() && e.getCause() != DamageCause.VOID){
				player.setFireTicks(0);
				e.setCancelled(true);
			}
		}
	}
}