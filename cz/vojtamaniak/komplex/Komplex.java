package cz.vojtamaniak.komplex;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import cz.vojtamaniak.komplex.commands.CommandBreak;
import cz.vojtamaniak.komplex.commands.CommandFeed;

public class Komplex extends JavaPlugin {
	
	private Logger log;
	private MessageManager msgManager;
	
	@Override
	public void onEnable(){
		log = getLogger();
		msgManager = new MessageManager(this);
		
		log.info("is enabled.");
		
		registerExecutors();
		registerListeners();
		
		msgManager.init();
	}
	
	@Override
	public void onDisable(){
		log.info("is disabled.");
	}
	
	public MessageManager getMessageManager(){
		return msgManager;
	}
	
	private void registerExecutors(){
		getCommand("break").setExecutor(new CommandBreak(this));
		getCommand("feed").setExecutor(new CommandFeed(this));
		getCommand("fly").setExecutor(new CommandFly(this));
	}
	
	private void registerListeners(){
		//Future listeners register
	}
}
