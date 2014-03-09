package cz.vojtamaniak.komplex.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.User;
import cz.vojtamaniak.komplex.Utils;

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
		plg.addUser(user);
	}
	
	/**
	 * @param e - PlayerQuitEvent
	 * @return
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerQuit(PlayerQuitEvent e){
		e.setQuitMessage(null);
		Bukkit.broadcast(msgManager.getMessage("MESSAGE_QUIT").replaceAll("%NICK%", e.getPlayer().getName()), "komplex.messages.onquit");				
		plg.removeUser(e.getPlayer().getName());
	}
	
	/**
	 *  @param e - PlayerKickEvent
	 *  @return
	 *  */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerKick(PlayerKickEvent e){
		plg.removeUser(e.getPlayer().getName());
	}
	
	/**
	 *  @param e - PlayerMoveEvent
	 *  @return
	 *  */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerMove(PlayerMoveEvent e){
		User user = plg.getUser(e.getPlayer().getName());
		if(user.isAfk()){
			user.setAfk(false);
			Bukkit.broadcast(msgManager.getMessage("AFK_LEAVE").replaceAll("%NICK%", e.getPlayer().getName()), "komplex.messages.onafkleave");
		}
	}
}