package cz.vojtamaniak.komplex;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Scheduler implements Runnable {
	
	private Komplex plg;
	
	public Scheduler(Komplex plg){
		this.plg = plg;
	}
	
	@Override
	public void run() {
		for(Player player : Bukkit.getOnlinePlayers()){
			User user = plg.getUser(player.getName());
		}
	}
}