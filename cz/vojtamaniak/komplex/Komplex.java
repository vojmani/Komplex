package cz.vojtamaniak.komplex;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import cz.vojtamaniak.komplex.commands.CommandBreak;

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
		//Future executors register
	}
	
	private void registerListeners(){
		//Future listeners register
	}
}
