package cz.vojtamaniak.komplex.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;

import cz.vojtamaniak.komplex.Komplex;

public class onQuit extends IListener {

	public onQuit(Komplex plg) {
		super(plg);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void execute(PlayerQuitEvent e){
		if(Bukkit.getOnlinePlayers().length > 0){
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.hasPermission("komplex.messages.onquit.receive")){
					p.sendMessage(msgManager.getMessage("MESSAGE_QUIT"));
				}
			}
		}
		plg.removeUser(e.getPlayer().getName());
	}
}