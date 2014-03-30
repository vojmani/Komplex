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
		check("NO_PERMISSION", "&4Na toto nemas prava.");
		check("FEED_SELF", "&7Nasytil ses.");
		check("FEED_OTHER_WHISPER", "&7Nasytil te admin %NICK%.");
		check("FEED_OTHER", "&7Nasytil jsi hrace %NICK%.");
		check("PLAYER_OFFLINE", "&4Tento hrac neni pripojen.");
		check("FLY_SELF_ON", "&7Zapnul sis letani.");
		check("FLY_SELF_OFF", "&7Vypnul sis letani.");
		check("FLY_OTHER_ON", "&7Zapnul si letani hraci %NICK%.");
		check("FLY_OTHER_OFF", "&7Vypnul jsi letani hraci %NICK%.");
		check("FLY_WHISPER_ON", "&7Admin %NICK% ti zapnul letani.");
		check("FLY_WHISPER_OFF", "&7Admin %NICK% ti vypnul letani.");
		check("NO_PERMISSION_BEDROCK", "&4Nemas prava na zniceni bedrocku.");
		check("CHAT_CLEARED", "&bChat byl promazan adminem %NICK%.");
		check("MESSAGE_JOIN", "&f%NICK% &ese pripojil.");
		check("MESSAGE_QUIT", "&f%NICK% &ese odpojil.");
		check("GOD_SELF_ON", "&7Zapnul sis nesmrtelnost.");
		check("GOD_SELF_OFF", "&7Vypnul sis nesmrtelnost.");
		check("GOD_OTHER_ON", "&7Zapnul jsi nesmrtelnost hraci %NICK%.");
		check("GOD_OTHER_OFF", "&7Vypnul jsi nesmrtelnost hraci %NICK%.");
		check("GOD_WHISPER_ON", "&7Admin %NICK% ti zapnul nesmrtelnost.");
		check("GOD_WHISPER_OFF", "&7Admin %NICK% ti vypnul nesmrtelnost.");
		check("HAT_SUCCESS", "&7Hlava zmenena.");
		check("HEAL_SELF", "&7Uzdravil ses.");
		check("HEAL_OTHER", "&7Uzdravil jsi hrace %NICK%.");
		check("HEAL_WHISPER", "&7Admin %NICK% te uzdravil.");
		check("WRONG_USAGE", "&4Spatny format prikazu. Spravny format: %USAGE%");
		check("PTIME_GET_SELF", "&7Tvuj cas je %TIME% ticku.");
		check("PTIME_GET_OTHER", "&7Cas hrace %NICK% je %TIME% ticku.");
		check("PTIME_RESET_SELF", "&7Tvuj cas je nyni stejny jako globalni serverovy cas.");
		check("PTIME_RESET_OTHER", "&7Cas hrace %NICK% je nyni stejny jako globalni serverovy cas.");
		check("PTIME_RESET_WHISPER", "Admin %NICK% ti nastavil cas stejny, jako je globalni na serveru.");
		check("PTIME_SET_SELF", "&7Nastavil jsi svuj cas na %TIME% ticku.");
		check("PTIME_SET_OTHER", "&7Nastavil jsi cas hrace %NICK% na %TIME% ticku.");
		check("PTIME_SET_WHISPER", "&7Admin %NICK% ti nastavil cas na %TIME% ticku.");
		check("AFK_KICK_WHISPER", "Byl jsi vyhozen za neaktivitu.");
		check("AFK_KICK_BROADCAST", "&f%NICK% &ebyl vyhozen za neaktivitu delsi nez 3 minuty.");
		check("AFK_ENTER", "&f%NICK% je nyni AFK.");
		check("AFK_LEAVE", "&f%NICK% uz neni AFK.");
		check("HELPOP_MESSAGE", "&6[HelpOp] &f%NICK%: %MESSAGE%");
		check("LIST", "&9Prave je pripojeno &b%NUMBEROFPLAYERS% &9z &b%MAXPLAYERS% &9hracu: &b%PLAYERS%");
		check("HIDDEN_PREFIX", "&7[NEVIDITELNY]");
		check("MAIL_FORMAT", "&f%SENDER%: %MESSAGE%");
		check("MAIL_CLEARED", "&7Mail(y) vymazany!");
		check("MAIL_SEND", "&7Mail odeslan!");
		check("MAIL_EMPTY", "&7Nemas zadne nove maily.");
		check("MAIL_CLEAR", "&4Pokud chces vymazat vsechny maily, napis &7/mail clear&4.");
		check("MAIL_INBOX", "&4Mas &7%COUNT%&4 novych mailu! Pro precteni napis &8/mail read&4");
		check("TELL_FORMAT_WHISPER", "&7[%SENDER% -> ty] &f%MESSAGE%");
		check("TELL_FORMAT_SELF", "&7[ty -> %RECEIVER%] &f%MESSAGE%");
		check("TELL_NO_RECIPIENT", "&4Jeste jsi neobdrzel zadnou soukromou zpravu.");
		check("TELL_PLAYER_OFFLINE", "&4Hrac, na jehoz zpravu chces odpovedet, neni pripojen.");
		check("NEAR_NOBODY", "&4nikdo");
		check("NEAR", "&7Hraci v tve blizkosti: %PLAYERS%");
		check("NEAR_PLAYER", "&f%NICK%(&4%DISTANCE%&f)");
		check("NEAR_OTHER", "&7Hraci v blizkosti hrace %NICK%: %PLAYERS%");
		check("IGNORE_LIST_EMPTY", "&4Nikoho neignorujes.");
		check("IGNORE_LIST_FORMAT", "&fIgnorovani hraci: %PLAYERS%");
		check("IGNORE_ADD_SUCCESS", "&7Hrac %PLAYER% byl uspesne pridan do tveho ignorelistu.");
		check("IGNORE_REMOVE_NOTIGNORED", "&4Tohoto hrace neignorujes.");
		check("IGNORE_REMOVE_SUCCESS", "&7Tento hrac byl uspesne smazan z tveho ignorelistu.");
		check("IGNORE_ADD_SELF", "&4Nemuzes ignorovat sam sebe!");
		check("IGNORE_ADD_ALREADY", "&4Tohoto hrace jiz ignorujes.");
		check("IGNORE_ADD_BYPASS", "&4Nemuzes ignorovat hrace %PLAYER%");
		check("ADMINCHAT_MESSAGE", "&4[AdminChat] &f%NICK%: %MESSAGE%");
		check("PLAYER_ONLY", "&4Tento prikaz nemuzes spustit z konzole.");
		check("WARP_SET_SUCCESS", "&7Warp nastaven.");
		check("WARP_SET_EXISTS", "&4Warp s timto jmenem jiz existuje.");
		check("WARP_LIST_EMPTY", "&4Nejsou k dispozici zadne warpy.");
		check("WARP_LIST", "&fWarpy: %WARPS%");
		check("WARP_NOT_EXISTS", "&4Tento warp neexistuje. Zobraz si seznam warpu pomoci &7/warp&4.");
		check("WARP_SUCCESS", "&7Byl jsi teleportovan na warp &f%WARP%&7.");
		check("WARP_DELETED", "&7Warp %WARP% byl uspesne smazan.");
		check("DOUBLEJUMP_SELF_ON", "&7Zapnul sis doublejump.");
		check("DOUBLEJUMP_SELF_OFF", "&7Vypnul sis doublejump.");
		check("DOUBLEJUMP_OTHER_ON", "&7Zapnul jsi doublejump hraci %NICK%.");
		check("DOUBLEJUMP_OTHER_OFF", "&7Vypnul jsi doublejump hraci %NICK%.");
		check("DOUBLEJUMP_WHISPER_ON", "&7Admin %NICK% ti zapl doublejump.");
		check("DOUBLEJUMP_WHISPER_OFF", "&7Admin %NICK% ti vypl doublejump.");
		
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
		return message.replaceAll("&", "§");
	}
	
	private void check(String key, String message){
		if(!mess.contains(key)){
			mess.set(key, message);
			allOk = false;
		}
	}
}