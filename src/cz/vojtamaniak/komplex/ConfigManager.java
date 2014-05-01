package cz.vojtamaniak.komplex;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	
	private Logger log;
	private File confFile;
	private FileConfiguration conf;
	
	public ConfigManager(Komplex plg){
		this.log = plg.getLogger();
		this.confFile = new File(plg.getDataFolder(), "config.yml");
		this.conf = YamlConfiguration.loadConfiguration(confFile);
	}
	
	public FileConfiguration getConfig(){
		return conf;
	}
	
	public void init(){
			boolean allOk = true;
			
			if(!conf.contains("storage.type")){
				conf.set("storage.type", "file");
				allOk = false;
			}
			if(!conf.contains("storage.mysql-host")){
				conf.set("storage.mysql-host", "localhost");
				allOk = false;
			}
			if(!conf.contains("storage.mysql-user")){
				conf.set("storage.mysql-user", "root");
				allOk = false;
			}
			if(!conf.contains("storage.mysql-pass")){
				conf.set("storage.mysql-pass", "topsecretpassword");
				allOk = false;
			}
			if(!conf.contains("storage.mysql-data")){
				conf.set("storage.mysql-data", "minecraft");
				allOk = false;
			}
			if(!conf.contains("home")){
				conf.createSection("home");
				allOk = false;
			}
			if(!conf.contains("explodes-fallingblock")){
				conf.set("explodes-fallingblock", false);
				allOk = false;
			}
			if(!conf.contains("feed-heal-effects")){
				conf.set("feed-heal-effects", false);
				allOk = false;
			}
			if(!conf.contains("jump-use-velocity")){
				conf.set("jump-use-velocity", false);
				allOk = false;
			}
			
			if(!allOk){
				log.info("Repairing or creating config.yml file.");
				try{
					conf.save(confFile);
				}catch(IOException e){
					e.printStackTrace();
				}
			}
	}
}