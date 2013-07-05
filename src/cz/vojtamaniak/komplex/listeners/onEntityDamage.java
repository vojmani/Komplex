package cz.vojtamaniak.komplex.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.EventHandler;

import cz.vojtamaniak.komplex.Komplex;

public class onEntityDamage extends IListener {

	public onEntityDamage(Komplex plg) {
		super(plg);
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onEntityDamageEvent(EntityDamageEvent e){
		if(e.getEntityType() == EntityType.PLAYER){
			Player player = (Player)e.getEntity();
			if(plg.getUser(player.getName()).getGodMode() && e.getCause() != DamageCause.VOID){
				player.setFireTicks(0);
				if(player.getRemainingAir() != player.getMaximumAir()){
					player.setRemainingAir(player.getMaximumAir());
				}
				e.setCancelled(true);
			}
		}
	}
}