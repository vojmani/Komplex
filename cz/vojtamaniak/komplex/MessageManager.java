package cz.vojtamaniak.komplex;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class MessageManager {
	
	private Komplex plg;
	private File messFile;
	private FileConfiguration mess;
	private Logger log;
	
	public MessageManager(Komplex plg){
		this.plg = plg;
		this.messFile = new File(plg.getDataFolder(), "messages.yml");
		this.mess = YamlConfiguration.loadConfiguration(messFile);
		this.log = plg.getLogger();
	}
	
	public String getMessage(String message){
		return format(mess.getString(message));
	}
	
	public void init(){
		boolean allOk = true;
		if(!mess.contains("NO_PERMISSION")){
			mess.set("NO_PERMISSION", "&4Nemas prava.");
			allOk = false;
		}
		if(!mess.contains("FEED_SELF")){
			mess.set("FEED_SELF", "&7Nasytil ses.");
			allOk = false;
		}
		if(!mess.contains("FEED_OTHER_WHISPER")){
			mess.set("FEED_OTHER_WHISPER", "&7Nasytil te admin %NICK%.");
			allOk = false;
		}
		if(!mess.contains("FEED_OTHER")){
			mess.set("FEED_OTHER", "&7Nasytil jsi hrace %NICK%.");
			allOk = false;
		}
		if(!mess.contains("PLAYER_OFFLINE")){
			mess.set("PLAYER_OFFLINE", "&4Tento hrac neni pripojen.");
			allOk = false;
		}
		if(!mess.contains("FLY_SELF_ON")){
			mess.set("FLY_SELF_ON", "&7Zapnul jsi si mod letani.");
			allOk = false;
		}
		if(!mess.contains("FLY_SELF_OFF")){
			mess.set("FLY_SELF_OFF", "&7Vypnul jsi si mod letani.");
			allOk = false;
		}
		if(!mess.contains("FLY_OTHER_ON")){
			mess.set("FLY_OTHER_ON", "&7Zapnul jsi mod letani hraci %NICK%");
			allOk = false;
		}
		if(!mess.contains("FLY_OTHER_OFF")){
			mess.set("FLY_OTHER_OFF", "&7Vypnul jsi mod letani hraci %NICK%");
			allOk = false;
		}
		if(!mess.contains("FLY_WHISPER_ON")){
			mess.set("FLY_WHISPER_ON", "&7Admin %NICK% ti zapnul mod letani.");
			allOk = false;
		}
		if(!mess.contains("FLY_WHISPER_OFF")){
			mess.set("FLY_WHISPER_OFF", "&7Admin %NICK% ti vypnul mod letani.");
			allOk = false;
		}
		if(!mess.contains("NO_PERMISSION_BEDROCK")){
			mess.set("NO_PERMISSION_BEDROCK", "&4Nemas prava na zniceni bedrocku.");
			allOk = false;
		}
			//There it will be filled with another messages.
		if(!allOk){
			log.info("repairing or creating new messages.yml file.");
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
