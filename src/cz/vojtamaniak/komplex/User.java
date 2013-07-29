package cz.vojtamaniak.komplex;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class User {
	
	private Player p;
	private boolean godMode;
	private boolean isAfk;
	private CommandSender lastPMSender;
	
	public User(Player p){
		this.p = p;
		this.godMode = false;
		this.isAfk = false;
		this.lastPMSender = null;
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
	
	public boolean isAfk() {
		return isAfk;
	}

	public void setAfk(boolean isAfk) {
		this.isAfk = isAfk;
	}
	
	public void setLastPM(CommandSender sender){
		this.lastPMSender = sender;
	}
	
	public CommandSender getLastPMSender(){
		return lastPMSender;
	}
}