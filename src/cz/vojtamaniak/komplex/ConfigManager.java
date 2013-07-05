package cz.vojtamaniak.komplex;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	
	private Logger log;
	private File confFile;
	private FileConfiguration conf;
	private Komplex plg;
	
	public ConfigManager(Komplex plg){
		this.plg = plg;
		this.log = plg.getLogger();
		this.confFile = new File(plg.getDataFolder(), "config.yml");
		this.conf = YamlConfiguration.loadConfiguration(confFile);
	}
	
	public void init(){
		if(confFile.exists()){
			
			boolean allOk = true;
			
			if(!conf.contains("storage.type")){
				conf.set("storage.type", "file");
				allOk = false;
			}
			if(!conf.contains("storage.mysql-host")){
				conf.set("storage.mysql-host", "localhost");
				allOk = false;
			}
			if(!conf.contains("storage.mysql-port")){
				conf.set("storage.mysql-port", "3306");
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
			if(!conf.contains("storage.mysql-table-prefix")){
				conf.set("storage.mysql-table-prefix", "k_");
				allOk = false;
			}
			
			if(!allOk){
				log.info("Repairing config.yml file.");
				try{
					conf.save(confFile);
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}else{
			log.info("Creating new config.yml file.");
			InputStream inStream = Komplex.class.getClassLoader().getResourceAsStream("config.yml");
			File dest = new File(plg.getDataFolder(), "config.yml");
			try {
				OutputStream outStream = new FileOutputStream(dest);
				int readBytes;
				byte[] buffer = new byte[4096];
				while((readBytes = inStream.read(buffer)) > 0){
					outStream.write(buffer, 0, readBytes);
				}
				inStream.close();
				outStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Object[] getMySQLData(){
		Object[] array = {conf.getString("storage.mysql-host"), conf.getInt("storage.mysql-port"), conf.getString("storage.mysql-user"), conf.getString("storage.mysql-pass"), conf.getString("storage.mysql-data"), conf.getString("storage.mysql-table-prefix")};
		return array;
	}
	
}