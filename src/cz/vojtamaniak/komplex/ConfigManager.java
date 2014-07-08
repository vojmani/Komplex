package cz.vojtamaniak.komplex;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	
	private Logger log;
	private File confFile;
	private FileConfiguration conf;
	private boolean allOk;
	
	public ConfigManager(Komplex plg){
		this.log = plg.getLogger();
		this.confFile = new File(plg.getDataFolder(), "config.yml");
		this.conf = YamlConfiguration.loadConfiguration(confFile);
	}
	
	public FileConfiguration getConfig(){
		return conf;
	}
	
	public void init(){			
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
			check("home.admin", 10);
			
			
			check("gadgets.locked.MaterialName", "IRON_DOOR");
			check("gadgets.locked.ItemName", "§4Zamceno :(");
			List<String> lores = new ArrayList<String>();
			lores.add("§cTento odznacek jsi jeste neziskal :(");
			check("gadgets.locked.ItemLores", lores);
			lores = new ArrayList<String>();
			check("gadgets.1.MaterialName", "PAPER");
			check("gadgets.1.ItemName", "§eTest!");
			lores.add("§2Uzasna testovaci vec..");
			lores.add("§3Sice neni uzitecna...");
			lores.add("§4Ale je super!");
			check("gadgets.1.ItemLores", lores);
			
			if(!allOk){
				log.info("Repairing or creating config.yml file.");
				save();
			}
	}
	
	private void check(String key, Object value){
		if(!conf.contains(key)){
			conf.set(key, value);
			allOk = false;
		}
	}
	
	private void save(){
		try {
			conf.save(confFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addArena(String name){
		conf.createSection("ctfarenas."+name);
		save();
	}
	
	public void deleteArena(String name){
		conf.set("ctfarenas."+name, null);
		save();
	}
	
	public void enableArena(String name, boolean enabled){
		conf.set("ctfarenas."+name+".enabled", enabled);
		save();
	}
	
	public void setArenaTimeLimit(String name, int timelimit){
		conf.set("ctfarenas."+name+"timelimit", timelimit);
		save();
	}
	
	public void setArenaFlagLocation(String name, String team, int x, int y, int z){
		conf.set("ctfarenas."+name+".flags."+team+".x", x);
		conf.set("ctfarenas."+name+".flags."+team+".y", y);
		conf.set("ctfarenas."+name+".flags."+team+".z", z);
		save();
	}
	
	public void setArenaArea(String name, int x1, int y1, int z1, int x2, int y2, int z2){
		conf.set("ctfarenas."+name+".area.x1", x1);
		conf.set("ctfarenas."+name+".area.y1", y1);
		conf.set("ctfarenas."+name+".area.z1", z1);
		conf.set("ctfarenas."+name+".area.x2", x2);
		conf.set("ctfarenas."+name+".area.y2", y2);
		conf.set("ctfarenas."+name+".area.z2", z2);
		save();
	}
	
	public void setArenaTeamArea(String name, String team, int x1, int y1, int z1, int x2, int y2, int z2){
		conf.set("ctfarenas."+name+".teamarea."+team+".x1", x1);
		conf.set("ctfarenas."+name+".teamarea."+team+".y1", y1);
		conf.set("ctfarenas."+name+".teamarea."+team+".z1", z1);
		conf.set("ctfarenas."+name+".teamarea."+team+".x2", x2);
		conf.set("ctfarenas."+name+".teamarea."+team+".y2", y2);
		conf.set("ctfarenas."+name+".teamarea."+team+".z2", z2);
		save();
	}
	
	public void setArenaFlagRestoreTime(String name, int time){
		conf.set("ctfarenas."+name+".flagrestoretime", time);
		save();
	}
	
	public void setArenaMinPlayers(String name, int min){
		conf.set("ctfarenas."+name+".minplayers", min);
		save();
	}
	
	public void setArenaMaxPlayers(String name, int max){
		conf.set("ctfarenas."+name+".maxplayers", max);
		save();
	}
	
	public void addSign(String name, int x, int y, int z){
		conf.set("ctfsigns."+name+".x", x);
		conf.set("ctfsigns."+name+".y", y);
		conf.set("ctfsigns."+name+".z", z);
		save();
	}

	public void setTimeBeforeStart(String name, int time) {
		conf.set("ctfarenas."+name+"timebeforestart", time);
		save();
	}
}