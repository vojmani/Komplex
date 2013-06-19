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
	
	public void init(){
		boolean allOk = true;
		
		if(!allOk){
			try{
				conf.save(confFile);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
}
