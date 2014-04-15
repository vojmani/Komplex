package cz.vojtamaniak.komplex.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.User;

public class PlayerListener extends IListener {

	public PlayerListener(Komplex plg) {
		super(plg);
	}
	
	/**
	 * @param e - PlayerJoinEvent
	 * @return
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJoin(PlayerJoinEvent e){
		e.setJoinMessage(null);
		Bukkit.broadcast(msgManager.getMessage("MESSAGE_JOIN").replaceAll("%NICK%", e.getPlayer().getName()), "komplex.messages.onjoin");
		User user = new User(e.getPlayer());
		user.setIgnoredPlayers(database.getIgnoredPlayers(e.getPlayer().getName()));
		user.setCountOfMails(database.getCountOfMails(e.getPlayer().getName()));
		user.setCountOfNotices(database.getCountOfNotices(e.getPlayer().getName()));
		api.addUser(user);
	}
	
	/**
	 * @param e - PlayerQuitEvent
	 * @return
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerQuit(PlayerQuitEvent e){
		e.setQuitMessage(null);
		Bukkit.broadcast(msgManager.getMessage("MESSAGE_QUIT").replaceAll("%NICK%", e.getPlayer().getName()), "komplex.messages.onquit");				
		api.removeUser(e.getPlayer().getName());
	}
	
	/**
	 *  @param e - PlayerKickEvent
	 *  @return
	 *  */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerKick(PlayerKickEvent e){
		api.removeUser(e.getPlayer().getName());
	}
	
	/**
	 *  @param e - PlayerMoveEvent
	 *  @return
	 *  */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerMove(PlayerMoveEvent e){
		Player player = e.getPlayer();
		String pName = player.getName();
		if(api.isAfk(pName)){
			api.setAfk(pName, false);
			Bukkit.broadcast(msgManager.getMessage("AFK_LEAVE").replaceAll("%NICK%", e.getPlayer().getName()), "komplex.messages.afk");
		}
		
		if((api.isDoubleJump(pName)) && (player.getGameMode() != GameMode.CREATIVE) && (player.getLocation().getBlock().getRelative(0, -1, 0).getType() != Material.AIR) && (!player.isFlying())){
			player.setAllowFlight(true);
		}
	}
	
	/**
	 * @param e - PlayerToggleFlightEvent
	 * @return
	 * */
	@EventHandler(priority = EventPriority.LOW)
	public void onToggleFlight(PlayerToggleFlightEvent e){
		Player player = e.getPlayer();
		
		if((player.getGameMode() != GameMode.CREATIVE) && (api.isDoubleJump(player.getName()))){
			player.setFlying(false);
			player.setAllowFlight(false);
			e.setCancelled(true);
			player.setVelocity(player.getLocation().getDirection().multiply(1.4).setY(1.0));
			player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1.0F, 1.0F);
		}
	}
	
	/**
	 * @param e - PlayerTeleportEvent
	 * @return
	 * */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerTeleport(PlayerTeleportEvent e){
		if(e.getCause() == TeleportCause.COMMAND){
			e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerChat(AsyncPlayerChatEvent e){
		String pName = e.getPlayer().getName();
		
		for(Player p : e.getRecipients()){
			if(p.hasPermission("komplex.ignore.bypass") && api.getIgnoredPlayers(p.getName()).contains(pName.toLowerCase())){
				p.sendMessage(msgManager.getMessage("IGNORE_ADD_BYPASS").replaceAll("%PLAYER%", pName));
				api.getIgnoredPlayers(p.getName()).remove(pName.toLowerCase());
			}
			
			if(api.getIgnoredPlayers(p.getName()).contains(pName.toLowerCase())){
				e.getRecipients().remove(p);
			}
		}
		
		if((((api.getLastMessageTime(pName) - System.currentTimeMillis()) / 1000) <= 60) && (plg.getUser(pName).getLastMessage() == e.getMessage())){
			e.getPlayer().sendMessage(msgManager.getMessage("SPAM_WARNING"));
			e.setCancelled(true);
		}
		
		if(!e.isCancelled()){
			plg.getUser(pName).setLastMessage(e.getMessage(), System.currentTimeMillis());
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerRespawn(PlayerRespawnEvent e){
		if(!e.isBedSpawn()){
			if(plg.getSpawnLocation() != null){
				e.setRespawnLocation(plg.getSpawnLocation());
			}
		}
	}
}