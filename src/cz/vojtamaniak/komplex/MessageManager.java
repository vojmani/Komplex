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
		check("FEED_SELF", "&aNasytil ses.");
		check("FEED_OTHER_WHISPER", "&7Nasytil te admin %NICK%.");
		check("FEED_OTHER", "&7Nasytil jsi hrace %NICK%.");
		check("PLAYER_OFFLINE", "&4Tento hrac neni pripojen.");
		check("FLY_SELF_ON", "&aZapnul sis letani.");
		check("FLY_SELF_OFF", "&aVypnul sis letani.");
		check("FLY_OTHER_ON", "&7Zapnul si letani hraci %NICK%.");
		check("FLY_OTHER_OFF", "&7Vypnul jsi letani hraci %NICK%.");
		check("FLY_WHISPER_ON", "&7Admin %NICK% ti zapnul letani.");
		check("FLY_WHISPER_OFF", "&7Admin %NICK% ti vypnul letani.");
		check("NO_PERMISSION_BEDROCK", "&4Nemas prava na zniceni bedrocku.");
		check("CHAT_CLEARED", "&bChat byl promazan adminem %NICK%.");
		check("MESSAGE_JOIN", "&f%NICK% &ese pripojil.");
		check("MESSAGE_QUIT", "&f%NICK% &ese odpojil.");
		check("GOD_SELF_ON", "&aZapnul sis nesmrtelnost.");
		check("GOD_SELF_OFF", "&aVypnul sis nesmrtelnost.");
		check("GOD_OTHER_ON", "&7Zapnul jsi nesmrtelnost hraci %NICK%.");
		check("GOD_OTHER_OFF", "&7Vypnul jsi nesmrtelnost hraci %NICK%.");
		check("GOD_WHISPER_ON", "&7Admin %NICK% ti zapnul nesmrtelnost.");
		check("GOD_WHISPER_OFF", "&7Admin %NICK% ti vypnul nesmrtelnost.");
		check("HAT_SUCCESS", "&aHlava zmenena.");
		check("HAT_AMOUNT", "&4Musis mit v ruce pouze jeden nebo zadny item!");
		check("HEAL_SELF", "&aUzdravil ses.");
		check("HEAL_OTHER", "&7Uzdravil jsi hrace %NICK%.");
		check("HEAL_WHISPER", "&7Admin %NICK% te uzdravil.");
		check("WRONG_USAGE", "&4Spatny format prikazu. Spravny format: %USAGE%");
		check("PTIME_GET_SELF", "&7Tvuj cas je %TIME% ticku.");
		check("PTIME_GET_OTHER", "&7Cas hrace %NICK% je %TIME% ticku.");
		check("PTIME_RESET_SELF", "&aTvuj cas je nyni stejny jako globalni serverovy cas.");
		check("PTIME_RESET_OTHER", "&7Cas hrace %NICK% je nyni stejny jako globalni serverovy cas.");
		check("PTIME_RESET_WHISPER", "Admin %NICK% ti nastavil cas stejny, jako je globalni na serveru.");
		check("PTIME_SET_SELF", "&7Nastavil jsi svuj cas na %TIME% ticku.");
		check("PTIME_SET_OTHER", "&7Nastavil jsi cas hrace %NICK% na %TIME% ticku.");
		check("PTIME_SET_WHISPER", "&7Admin %NICK% ti nastavil cas na %TIME% ticku.");
		check("AFK_KICK_WHISPER", "Byl jsi vyhozen za neaktivitu delsi nez 3 minuty.");
		check("AFK_KICK_BROADCAST", "&f%NICK% &ebyl vyhozen za neaktivitu delsi nez 3 minuty.");
		check("AFK_ENTER", "&f%NICK% je nyni AFK.");
		check("AFK_LEAVE", "&f%NICK% uz neni AFK.");
		check("HELPOP_MESSAGE", "&6[HelpOp] &f%NICK%: %MESSAGE%");
		check("LIST", "&9Prave je pripojeno &b%NUMBEROFPLAYERS% &9z &b%MAXPLAYERS% &9hracu: &b%PLAYERS%");
		check("HIDDEN_PREFIX", "&7[NEVIDITELNY]");
		check("MAIL_FORMAT", "&f%SENDER%: %MESSAGE%");
		check("MAIL_CLEARED", "&aMail(y) vymazany!");
		check("MAIL_SEND", "&aMail odeslan!");
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
		check("IGNORE_ADD_SUCCESS", "&aHrac &f%PLAYER%&a byl uspesne pridan do tveho ignorelistu.");
		check("IGNORE_REMOVE_NOTIGNORED", "&4Tohoto hrace neignorujes.");
		check("IGNORE_REMOVE_SUCCESS", "&aTento hrac byl uspesne smazan z tveho ignorelistu.");
		check("IGNORE_ADD_SELF", "&4Nemuzes ignorovat sam sebe!");
		check("IGNORE_ADD_ALREADY", "&4Tohoto hrace jiz ignorujes.");
		check("IGNORE_ADD_BYPASS", "&4Nemuzes ignorovat hrace %PLAYER%");
		check("ADMINCHAT_MESSAGE", "&4[AdminChat] &f%NICK%: %MESSAGE%");
		check("PLAYER_ONLY", "&4Tento prikaz nemuzes spustit z konzole.");
		check("WARP_SET_SUCCESS", "&aWarp nastaven.");
		check("WARP_SET_EXISTS", "&4Warp s timto jmenem jiz existuje.");
		check("WARP_LIST_EMPTY", "&4Nejsou k dispozici zadne warpy.");
		check("WARP_LIST", "&fWarpy: %WARPS%");
		check("WARP_NOT_EXISTS", "&4Tento warp neexistuje. Zobraz si seznam warpu pomoci &7/warp&4.");
		check("WARP_SUCCESS", "&aByl jsi teleportovan na warp &f%WARP%&a.");
		check("WARP_DELETED", "&aWarp &f%WARP%&a byl uspesne smazan.");
		check("DOUBLEJUMP_SELF_ON", "&aZapnul sis doublejump.");
		check("DOUBLEJUMP_SELF_OFF", "&aVypnul sis doublejump.");
		check("DOUBLEJUMP_OTHER_ON", "&7Zapnul jsi doublejump hraci %NICK%.");
		check("DOUBLEJUMP_OTHER_OFF", "&7Vypnul jsi doublejump hraci %NICK%.");
		check("DOUBLEJUMP_WHISPER_ON", "&7Admin %NICK% ti zapl doublejump.");
		check("DOUBLEJUMP_WHISPER_OFF", "&7Admin %NICK% ti vypl doublejump.");
		check("HOME_SET_NONAME", "&4Musis napsat i nazev domova -> &7/sethome <nazev>&4.");
		check("HOME_ADDED", "&aDomov byl pridan!");
		check("HOME_ADD_DENIED", "&4Muzes mit maximalne jeden domov!");
		check("HOME_ADD_MAX", "&4Muzes mit maximalne %MAX% domovu.");
		check("HOME_NOONE", "&4Nemas zadny domov!");
		check("HOME_TP", "&aByl jsi teleportovan na domov %HOME%.");
		check("HOME_TP_DEFAULT", "&aByl jsi teleportovan domu.");
		check("HOME_LIST", "&fTve domovy: %HOMES%");
		check("HOME_NOT_EXISTS", "&4Tento domov neexistuje.");
		check("TICKET_ADDED", "&aVytvoril jsi ticket. Nyni pockej, az ti na nej nekdo z adminu odpovi.");
		check("TICKET_ID_INT", "&4Id ticketu musi byt cislo!");
		check("TICKET_NOT_EXISTS", "&4Ticket s timto id neexistuje.");
		check("TICKET_INFO_ID", "&1Ticket &6#%ID%");
		check("TICKET_INFO_CREATOR", "&1Vytvoril hrac: &6%CREATOR%");
		check("TICKET_INFO_CREATED", "&1Vytvoren: &6%CREATED%");
		check("TICKET_INFO_REFRESH", "&1Aktualizovan: &6%REFRESHED%");
		check("TICKET_INFO_STATUS", "&1Status: &6%STATUS%");
		check("TICKET_INFO_ADMIN", "&1Naposledy resil: &6%ADMIN%");
		check("TICKET_INFO_TEXT", "&1Popis: &6%TEXT%");
		check("TICKET_INFO_USERREPLY", "&1Odpoved hrace: &6%REPLY%");
		check("TICKET_INFO_ADMINREPLY", "&1Odpoved admina: &6%REPLY%");
		check("TICKET_WARNING_CLOSED", "&eUpozorneni: Tento ticket je uzavren.");
		check("TICKET_TAKE_ADMINS", "&1Admin &6%ADMIN%&1 resi ticket cislo &6%ID%&1.");
		check("TICKET_TAKE_CREATOR", "&1Admin &6%ADMIN%&1 resi tvuj ticket cislo &6%ID%&1.");
		check("TICKET_OPENED", "&7Ticket &2#%ID%&7 byl znovuotevren.");
		check("TICKET_CLOSED", "&7Ticket &2#%ID%&7 byl uzavren.");
		check("TICKET_REPLIED", "&1Odpovedel jsi do ticketu &6#%ID%&1.");
		check("TICKET_REPLY_CLOSED", "&4Tento ticket je uzavren, proto do nej nemuzes odpovedet.");
		check("TICKET_NOT_YOURS", "&4Tento ticket neni tvuj.");
		check("TICKET_DELETED", "&aTicket #%ID% byl smazan.");
		check("SPAWN_SET", "&aSpawn nastaven.");
		check("SPAWN_TP_SELF", "&aByl jsi teleportovan na spawn.");
		check("SPAWN_TP_OTHER", "&7Teleportoval jsi hrace %NICK% na spawn.");
		check("SPAWN_TP_WHISPER", "&7Admin %NICK% te teleportoval na spawn.");
		check("NOTICE_SCHED", "&6Mas %COUNT% neprectenych upozorneni. Zobraz si je prikazem &7/notices&6.");
		check("NOTICE_LIST_EMPTY", "&4Nemas zadna nova oznameni.");
		check("NOTICE_LIST_FORMAT", "&f%TIME%: %TEXT%");
		check("NOTICE_MESSAGE_ALL", "&6Pokud chces zobrazit vsechny oznameni, napis &7/notices -all&6.");
		check("NOTICE_GLOBAL_ADD", "&aGlobalni oznameni pridano!");
		check("NOTICE_OTHER_ADD", "&aPridal jsi oznameni hraci %NICK%");
		check("EDITSIGN_LINE_INT", "&4Radek musi byt cislo.");
		check("EDITSIGN_MAX_LENGTH", "&4Maximalni delka textu je 15 znaku.");
		check("EDITSIGN_NOT_SIGN", "&4Block, na ktery se divas, neni cedule.");
		check("EDITSIGN_SUCCESS", "&aCedule upravena!");
		check("EDITSIGN_LINE_MINMAX", "&4Radek musi byt v rozmezi 1 - 4");
		check("SPAM_WARNING", "&4Prosim, neopakuj jednu zpravu vicekrat. Diky!");
		
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
		//Change "and" character with selection sign 
		return message.replaceAll("&", "§");
	}
	
	private void check(String key, String message){
		if(!mess.contains(key)){
			mess.set(key, message);
			allOk = false;
		}
	}
}