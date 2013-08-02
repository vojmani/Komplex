package cz.vojtamaniak.komplex;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class MessageManager {
	
	private File messFile;
	private FileConfiguration mess;
	private Logger log;
	private boolean allOk;
	
	public MessageManager(Komplex plg){
		this.messFile = new File(plg.getDataFolder(), "messages.yml");
		this.mess = YamlConfiguration.loadConfiguration(messFile);
		this.log = plg.getLogger();
		this.allOk = true;
	}
	
	public String getMessage(String message){
		return format(mess.getString(message));
	}
	
	public void init(){
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
		if(!mess.contains("CHAT_CLEARED")){
			mess.set("CHAT_CLEARED", "&bChat promazan adminem %NICK%");
			allOk = false;
		}
		if(!mess.contains("MESSAGE_JOIN")){
			mess.set("MESSAGE_JOIN", "&f%NICK% &ese pripojil.");
			allOk = false;
		}
		if(!mess.contains("MESSAGE_QUIT")){
			mess.set("MESSAGE_QUIT", "&f%NICK% &ese odpojil.");
			allOk = false;
		}
		if(!mess.contains("GOD_SELF_ON")){
			mess.set("GOD_SELF_ON", "&7Zapnul jsi si nesmrtelnost.");
			allOk = false;
		}
		if(!mess.contains("GOD_SELF_OFF")){
			mess.set("GOD_SELF_OFF", "&7Vypnul jsi si nesmrtelnost.");
			allOk = false;
		}
		if(!mess.contains("GOD_OTHER_ON")){
			mess.set("GOD_OTHER_ON", "&7Zapnul jsi nesmrtelnost hraci %NICK%");
			allOk = false;
		}
		if(!mess.contains("GOD_OTHER_OFF")){
			mess.set("GOD_OTHER_OFF", "&7Vypnul jsi nesmrtelnost hraci %NICK%");
			allOk = false;
		}
		if(!mess.contains("GOD_WHISPER_ON")){
			mess.set("GOD_WHISPER_ON", "&7Admin %NICK% ti zapnul nesmrtelnost.");
			allOk = false;
		}
		if(!mess.contains("GOD_WHISPER_OFF")){
			mess.set("GOD_WHISPER_OFF", "&7Admin %NICK% ti vypnul nesmrtelnost.");
			allOk = false;
		}
		if(!mess.contains("HAT_SUCCESS")){
			mess.set("HAT_SUCCESS", "&7Hlava zmenena.");
			allOk = false;
		}
		if(!mess.contains("HEAL_SELF")){
			mess.set("HEAL_SELF", "&7Doplnil jsis zdravi.");
			allOk = false;
		}
		if(!mess.contains("HEAL_OTHER")){
			mess.set("HEAL_OTHER", "&7Doplnil jsi zdravi hraci %NICK%");
			allOk = false;
		}
		if(!mess.contains("HEAL_WHISPER")){
			mess.set("HEAL_WHISPER", "&7Admin %NICK% ti doplnil zdrav�.");
			allOk = false;
		}
		if(!mess.contains("WRONG_USAGE")){
			mess.set("WRONG_USAGE", "&4Spatny format prikazu. Spravny format: %USAGE%");
			allOk = false;
		}
		if(!mess.contains("PTIME_GET_SELF")){
			mess.set("PTIME_GET_SELF", "&7Tvuj cas je %TIME% ticku.");
			allOk = false;
		}
		if(!mess.contains("PTIME_GET_OTHER")){
			mess.set("PTIME_GET_OTHER", "&7Cas hrace %NICK% je %TIME% ticku.");
			allOk = false;
		}
		if(!mess.contains("PTIME_RESET_SELF")){
			mess.set("PTIME_RESET_SELF", "&7Tvuj cas je nyni stejny jako na serveru.");
			allOk = false;
		}
		if(!mess.contains("PTIME_RESET_OTHER")){
			mess.set("PTIME_RESET_OTHER", "&7Cas hrace %NICK% je nyni stejny jako na serveru.");
			allOk = false;
		}
		if(!mess.contains("PTIME_RESET_WHISPER")){
			mess.set("PTIME_RESET_WHISPER", "&7Admin %NICK% ti nastavil cas stejny, jako je na serveru.");
			allOk = false;
		}
		if(!mess.contains("PTIME_SET_SELF")){
			mess.set("PTIME_SET_SELF", "&7Nastavil jsi svuj cas na %TIME% ticku.");
			allOk = false;
		}
		if(!mess.contains("PTIME_SET_OTHER")){
			mess.set("PTIME_SET_OTHER", "&7Nastavil jsi cas hrace %NICK% na %TIME% ticku.");
			allOk = false;
		}
		if(!mess.contains("PTIME_SET_WHISPER")){
			mess.set("PTIME_SET_WHISPER", "&7Admin %NICK% ti nastavil cas na %TIME% ticku.");
			allOk = false;
		}
		if(!mess.contains("AFK_KICK_WHISPER")){
			mess.set("AFK_KICK_WHISPER", "Byl jsi vyhozen za neaktivitu delsi nez 3 minuty");
			allOk = false;
		}
		if(!mess.contains("AFK_KICK_BROADCAST")){
			mess.set("AFK_KICK_BROADCAST", "&f%NICK% &ebyl vyhozen za neaktivitu delsi nez 3 minuty.");
			allOk = false;
		}
		if(!mess.contains("AFK_ENTER")){
			mess.set("AFK_ENTER", "&f%NICK% je nyni AFK.");
			allOk = false;
		}
		if(!mess.contains("AFK_LEAVE")){
			mess.set("AFK_LEAVE", "&f%NICK% uz neni AFK.");
			allOk = false;
		}
		if(!mess.contains("HELPOP_MESSAGE")){
			mess.set("HELPOP_MESSAGE", "&6[HelpOp] &f%NICK%: %MESSAGE%");
			allOk = false;
		}
		if(!mess.contains("LIST")){
			mess.set("LIST", "&9Prave je pripojeno &b%NUMBEROFPLAYERS% &9z &b%MAXPLAYERS% &9hracu: &b%PLAYERS%");
			allOk = false;
		}
		if(!mess.contains("HIDDEN_PREFIX")){
			mess.set("HIDDEN_PREFIX", "&7[NEVIDITELNY]");
			allOk = false;
		}
		if(!mess.contains("MAIL_FORMAT")){
			mess.set("MAIL_FORMAT", "&f%SENDER%: %MESSAGE%");
			allOk = false;
		}
		if(!mess.contains("MAIL_CLEARED")){
			mess.set("MAIL_CLEARED", "&7Mail(y) vymazany!");
			allOk = false;
		}
		if(!mess.contains("MAIL_SEND")){
			mess.set("MAIL_SEND", "&7Mail odeslan!");
			allOk = false;
		}
		if(!mess.contains("MAIL_EMPTY")){
			mess.set("MAIL_EMPTY", "&7Nemas zadne nove maily.");
			allOk = false;
		}
		if(!mess.contains("MAIL_CLEAR")){
			mess.set("MAIL_CLEAR", "&4Pokud chces vymazat mail, napis &7/mail clear&4.");
			allOk = false;
		}
		if(!mess.contains("TELL_FORMAT_WHISPER")){
			mess.set("TELL_FORMAT_WHISPER", "&7[%SENDER% -> ty] &f%MESSAGE%");
			allOk = false;
		}
		if(!mess.contains("TELL_FORMAT_SELF")){
			mess.set("TELL_FORMAT_SELF", "&7[ty -> %RECEIVER%] &f%MESSAGE%");
			allOk = false;
		}
		if(!mess.contains("TELL_NO_RECIPIENT")){
			mess.set("TELL_NO_RECIPIENT", "&4Jeste jsi neobdrzel zadnou soukromou zpravu.");
			allOk = false;
		}
		if(!mess.contains("TELL_PLAYER_OFFLINE")){
			mess.set("TELL_PLAYER_OFFLINE", "&4Hrac, na jehoz zpravu chces odpovedet, neni pripojen.");
			allOk = false;
		}
		check("NEAR_NOBODY", "&4nikdo");
		check("NEAR", "&7Hraci v tve blizkosti: %PLAYERS%");
		check("NEAR_PLAYER", "&f%NICK%(&4%DISTANCE%&f)");
		check("NEAR_OTHER", "&7Hraci v blizkosti hrace %NICK%: %PLAYERS%");
		
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
		//Change & char with �
		return message.replaceAll("&", "�");
	}
	
	private void check(String key, String message){
		if(!mess.contains(key)){
			mess.set(key, message);
		}
	}
}