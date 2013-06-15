package cz.vojtamaniak.komplex;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Komplex extends JavaPlugin {
  
	private Logger log;
	
	@Override
	public void onEnable(){
		log = getLogger();
		
		
		log.info("is enabled.");
	}
	
	@Override
	public void onDisable(){
		log.info("is disabled.");
	}
}
