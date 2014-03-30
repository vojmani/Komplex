package cz.vojtamaniak.komplex;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class User {
	
	private Player p;
	private boolean godMode;
	private boolean isAfk;
	private boolean doublejump;
	private CommandSender lastPMSender;
	private List<String> ignoredPlayers;
	private int countOfMails;
	
	public User(Player p){
		this.p = p;
		this.godMode = false;
		this.isAfk = false;
		this.lastPMSender = null;
		this.ignoredPlayers = new ArrayList<String>();
		this.countOfMails = 0;
		this.doublejump = false;
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
	
	public List<String> getIgnoredPlayers(){
		return ignoredPlayers;
	}

	public void setIgnoredPlayers(List<String> iPlayers){
		this.ignoredPlayers = iPlayers;
	}
	
	public void setCountOfMails(int countOfMails){
		this.countOfMails = countOfMails;
	}
	
	public int getCountOfMails(){
		return countOfMails;
	}
	
	public boolean getDoubleJump(){
		return doublejump;
	}
	
	public void setDoubleJump(boolean doublejump){
		this.doublejump = doublejump;
	}
}