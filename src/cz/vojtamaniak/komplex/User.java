package cz.vojtamaniak.komplex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
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
	private int countOfNotices;
	private String lastMessage;
	private long lastMessageTime;
	private long lastMoveTime;
	private boolean vanish;
	private HashMap<Material, String> tools;
	
	public User(Player p){
		this.p = p;
		this.godMode = false;
		this.isAfk = false;
		this.lastPMSender = null;
		this.ignoredPlayers = new ArrayList<String>();
		this.countOfMails = 0;
		this.countOfNotices = 0;
		this.doublejump = false;
		this.lastMessage = "";
		this.lastMessageTime = 0L;
		this.lastMoveTime = System.currentTimeMillis();
		this.vanish = false;
		this.tools = new HashMap<Material, String>();
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
	
	public int getCountOfNotices(){
		return countOfNotices;
	}
	
	public void setCountOfNotices(int countOfNotices){
		this.countOfNotices = countOfNotices;
	}
	
	public long getLastMessageTime(){
		return lastMessageTime;
	}
	
	public String getLastMessage(){
		return lastMessage;
	}
	
	public void setLastMessage(String message, long time){
		this.lastMessage = message;
		this.lastMessageTime = time;
	}

	public boolean isVanish() {
		return vanish;
	}

	public void setVanish(boolean vanish) {
		this.vanish = vanish;
	}
	
	public long getLastMoveTime(){
		return lastMoveTime;
	}
	
	public void setLastMoveTime(long time){
		this.lastMoveTime = time;
	}

	public HashMap<Material, String> getTools() {
		return tools;
	}
	
	public void setTools(HashMap<Material, String> tools){
		this.tools = tools;
	}
}