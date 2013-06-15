package cz.vojtamaniak.komplex;

import java.io.File;
import java.io.IOException;

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
			mess.set("FEED_SELF", "&7Nasytil ses.");
			mess.set("FEED_OTHER_WHISPER", "&7Nasytil te admin %NICK%.");
			mess.set("FEED_OTHER", "&7Nasytil jsi hrace %NICK%.");
			mess.set("PLAYER_OFFLINE", "&dTento hrac je offline.");
			//There it will be filled with another messages.
			
			try {
				mess.save(messFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String format(String message){
		//Change & char with §
		return message.replaceAll("&", "§");
	}
}
