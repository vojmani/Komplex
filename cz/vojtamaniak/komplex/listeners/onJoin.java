package cz.vojtamaniak.komplex.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.User;

public class onJoin extends IListener {
  
	public onJoin(Komplex plg){
		super(plg);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void execute(PlayerJoinEvent e){
		e.setJoinMessage(null);
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.hasPermission("komplex.messages.onjoin.receive")){
				p.sendMessage(msgManager.getMessage("MESSAGE_JOIN").replaceAll("%NICK%", e.getPlayer().getName()));
			}
		}
		User user = new User(e.getPlayer());
		plg.addUser(user);
	}
}
