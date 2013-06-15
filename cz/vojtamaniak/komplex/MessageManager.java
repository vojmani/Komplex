package cz.vojtamaniak.komplex;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class MessageManager {
  
	private Komplex plg;
	private File messFile;
	private FileConfiguration mess;
	
	public MessageManager(Komplex plg){
		this.plg = plg;
		this.messFile = new File(plg.getDataFolder(), "messages.yml");
		this.mess = YamlConfiguration.loadConfiguration(messFile);
	}
	
	public String getMessage(String message){
		return format(mess.getString(message));
	}
	
	public void init(){
		if(!messFile.exists()){
			mess.set("NO_PERMISSION", "&dNemas prava.");
			//There it will be filled with another messages.
		}
	}
	
	private String format(String message){
		//Change & char with §
		return message.replaceAll("&", "§");
	}
}
