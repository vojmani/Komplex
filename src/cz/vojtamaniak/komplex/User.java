package cz.vojtamaniak.komplex;

import org.bukkit.entity.Player;

public class User {
	
	private Player p;
	private boolean godMode;
	private long afkStart;
	
	public User(Player p){
		this.p = p;
		this.godMode = false;
		this.afkStart = 0;
	}
	
	public Player getPlayer(){
		return p;
	}
	
	public boolean getGodMode(){
		return godMode;
	}
	
	public void setGodMode(boolean godmode){
		this.godMode = godmode;
	}
}