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
		check("NO_PERMISSION", "&cNa toto nemas prava.");
		check("FEED_SELF", "&aNasytil ses.");
		check("FEED_OTHER_WHISPER", "&7Nasytil te admin &f%NICK%&7.");
		check("FEED_OTHER", "&7Nasytil jsi hrace &f%NICK%&7.");
		check("PLAYER_OFFLINE", "&cTento hrac neni pripojen.");
		check("FLY_SELF_ON", "&aZapnul sis letani.");
		check("FLY_SELF_OFF", "&aVypnul sis letani.");
		check("FLY_OTHER_ON", "&7Zapnul si letani hraci &f%NICK%&7.");
		check("FLY_OTHER_OFF", "&7Vypnul jsi letani hraci &f%NICK%&7.");
		check("FLY_WHISPER_ON", "&7Admin &f%NICK%&7 ti zapnul letani.");
		check("FLY_WHISPER_OFF", "&7Admin &f%NICK%&7 ti vypnul letani.");
		check("NO_PERMISSION_BEDROCK", "&cNemas prava na zniceni bedrocku.");
		check("CHAT_CLEARED", "&bChat byl promazan adminem &f%NICK%&7.");
		check("MESSAGE_JOIN", "&f%NICK% &ese pripojil.");
		check("MESSAGE_QUIT", "&f%NICK% &ese odpojil.");
		check("GOD_SELF_ON", "&aZapnul sis nesmrtelnost.");
		check("GOD_SELF_OFF", "&aVypnul sis nesmrtelnost.");
		check("GOD_OTHER_ON", "&7Zapnul jsi nesmrtelnost hraci &f%NICK%&7.");
		check("GOD_OTHER_OFF", "&7Vypnul jsi nesmrtelnost hraci &f%NICK%&7.");
		check("GOD_WHISPER_ON", "&7Admin &f%NICK%&7 ti zapnul nesmrtelnost.");
		check("GOD_WHISPER_OFF", "&7Admin &f%NICK%&7 ti vypnul nesmrtelnost.");
		check("HAT_SUCCESS", "&aHlava zmenena.");
		check("HAT_AMOUNT", "&cMusis mit v ruce pouze jeden nebo zadny item!");
		check("HEAL_SELF", "&aUzdravil ses.");
		check("HEAL_OTHER", "&7Uzdravil jsi hrace &f%NICK%&7.");
		check("HEAL_WHISPER", "&7Admin &f%NICK%&7 te uzdravil.");
		check("WRONG_USAGE", "&cSpatny format prikazu. &cSpravny format: &f%USAGE%");
		check("PTIME_GET_SELF", "&7Tvuj cas je &f%TIME%&7 ticku.");
		check("PTIME_GET_OTHER", "&7Cas hrace &f%NICK%&7 je &f%TIME%&7 ticku.");
		check("PTIME_RESET_SELF", "&aTvuj cas je nyni stejny jako globalni serverovy cas.");
		check("PTIME_RESET_OTHER", "&7Cas hrace &f%NICK%&7 je nyni stejny jako globalni serverovy cas.");
		check("PTIME_RESET_WHISPER", "&7Admin &f%NICK%&7 ti nastavil cas stejny, jako je globalni na serveru.");
		check("PTIME_SET_SELF", "&7Nastavil jsi svuj cas na &f%TIME%&7 ticku.");
		check("PTIME_SET_OTHER", "&7Nastavil jsi cas hrace &f%NICK%&7 na &f%TIME%&7 ticku.");
		check("PTIME_SET_WHISPER", "&7Admin &f%NICK%&7 ti nastavil cas na &f%TIME%&7 ticku.");
		check("AFK_KICK_WHISPER", "&cByl jsi vyhozen za neaktivitu delsi nez 5 minut.");
		check("AFK_KICK_BROADCAST", "&f%NICK% &ebyl vyhozen za neaktivitu delsi nez 5 minut.");
		check("AFK_ENTER", "&f%NICK% &eje nyni AFK.");
		check("AFK_LEAVE", "&f%NICK% &euz neni AFK.");
		check("HELPOP_MESSAGE", "&6[HelpOp] &f%NICK%: %MESSAGE%");
		check("LIST", "&9Prave je pripojeno &b%NUMBEROFPLAYERS% &9z &b%MAXPLAYERS% &9hracu: &b%PLAYERS%");
		check("HIDDEN_PREFIX", "&7[NEVIDITELNY]");
		check("MAIL_FORMAT", "&f%SENDER%: %MESSAGE%");
		check("MAIL_CLEARED", "&aMail(y) vymazany!");
		check("MAIL_SEND", "&aMail odeslan!");
		check("MAIL_EMPTY", "&7Nemas zadne nove maily.");
		check("MAIL_CLEAR", "&cPokud chces vymazat vsechny maily, napis &f/mail clear&c.");
		check("MAIL_INBOX", "&cMas &7%COUNT%&c novych mailu! Pro precteni napis &f/mail read&c.");
		check("TELL_FORMAT_WHISPER", "&7[%SENDER% -> ty] &f%MESSAGE%");
		check("TELL_FORMAT_SELF", "&7[ty -> %RECEIVER%] &f%MESSAGE%");
		check("TELL_NO_RECIPIENT", "&cJeste jsi neobdrzel zadnou soukromou zpravu.");
		check("TELL_PLAYER_OFFLINE", "&cHrac, na jehoz zpravu chces odpovedet, neni pripojen.");
		check("NEAR_NOBODY", "&cnikdo");
		check("NEAR", "&7Hraci v tve blizkosti: %PLAYERS%");
		check("NEAR_PLAYER", "&f%NICK%(&c%DISTANCE%&f)");
		check("NEAR_OTHER", "&7Hraci v blizkosti hrace &f%NICK%&7: %PLAYERS%");
		check("IGNORE_LIST_EMPTY", "&cNikoho neignorujes.");
		check("IGNORE_LIST_FORMAT", "&fIgnorovani hraci: %PLAYERS%");
		check("IGNORE_ADD_SUCCESS", "&aHrac &f%PLAYER%&a byl uspesne pridan do tveho ignorelistu.");
		check("IGNORE_REMOVE_NOTIGNORED", "&cTohoto hrace neignorujes.");
		check("IGNORE_REMOVE_SUCCESS", "&aTento hrac byl uspesne smazan z tveho ignorelistu.");
		check("IGNORE_ADD_SELF", "&cNemuzes ignorovat sam sebe!");
		check("IGNORE_ADD_ALREADY", "&cTohoto hrace jiz ignorujes.");
		check("IGNORE_ADD_BYPASS", "&cNemuzes ignorovat hrace &f%PLAYER%&c.");
		check("ADMINCHAT_MESSAGE", "&c[AdminChat] &f%NICK%: %MESSAGE%");
		check("PLAYER_ONLY", "&cTento prikaz nemuzes spustit z konzole.");
		check("WARP_SET_SUCCESS", "&aWarp nastaven.");
		check("WARP_SET_EXISTS", "&cWarp s timto jmenem jiz existuje.");
		check("WARP_LIST_EMPTY", "&cNejsou k dispozici zadne warpy.");
		check("WARP_LIST", "&fWarpy: %WARPS%");
		check("WARP_NOT_EXISTS", "&cTento warp neexistuje. Zobraz si seznam warpu pomoci &7/warp&c.");
		check("WARP_SUCCESS", "&aByl jsi teleportovan na warp &f%WARP%&a.");
		check("WARP_DELETED", "&aWarp &f%WARP%&a byl uspesne smazan.");
		check("DOUBLEJUMP_SELF_ON", "&aZapnul sis doublejump.");
		check("DOUBLEJUMP_SELF_OFF", "&aVypnul sis doublejump.");
		check("DOUBLEJUMP_OTHER_ON", "&7Zapnul jsi doublejump hraci &f%NICK%&7.");
		check("DOUBLEJUMP_OTHER_OFF", "&7Vypnul jsi doublejump hraci &f%NICK%&7.");
		check("DOUBLEJUMP_WHISPER_ON", "&7Admin &f%NICK%&7 ti zapl doublejump.");
		check("DOUBLEJUMP_WHISPER_OFF", "&7Admin &f%NICK%&7 ti vypl doublejump.");
		check("HOME_SET_NONAME", "&cMusis napsat i nazev domova -> &f/sethome <nazev>&c.");
		check("HOME_ADDED", "&aDomov byl pridan!");
		check("HOME_ADD_DENIED", "&cMuzes mit maximalne jeden domov!");
		check("HOME_ADD_MAX", "&cMuzes mit maximalne &f%MAX%&c domovu.");
		check("HOME_NOONE", "&cNemas zadny domov!");
		check("HOME_TP", "&aByl jsi teleportovan na domov &f%HOME%&a.");
		check("HOME_TP_DEFAULT", "&aByl jsi teleportovan domu.");
		check("HOME_LIST", "&fTve domovy: %HOMES%");
		check("HOME_NOT_EXISTS", "&cTento domov neexistuje.");
		check("TICKET_ADDED", "&aVytvoril jsi ticket. Nyni pockej, az ti na nej nekdo z adminu odpovi.");
		check("TICKET_ID_INT", "&cId ticketu musi byt cislo!");
		check("TICKET_NOT_EXISTS", "&cTicket s timto id neexistuje.");
		check("TICKET_INFO_ID", "&9Ticket &6#%ID%");
		check("TICKET_INFO_CREATOR", "&9Vytvoril hrac: &6%CREATOR%");
		check("TICKET_INFO_CREATED", "&9Vytvoren: &6%CREATED%");
		check("TICKET_INFO_REFRESH", "&9Aktualizovan: &6%REFRESHED%");
		check("TICKET_INFO_STATUS", "&9Status: &6%STATUS%");
		check("TICKET_INFO_ADMIN", "&9Naposledy resil: &6%ADMIN%");
		check("TICKET_INFO_TEXT", "&9Popis: &6%TEXT%");
		check("TICKET_INFO_USERREPLY", "&9Odpoved hrace: &6%REPLY%");
		check("TICKET_INFO_ADMINREPLY", "&9Odpoved admina: &6%REPLY%");
		check("TICKET_WARNING_CLOSED", "&eUpozorneni: Tento ticket je uzavren.");
		check("TICKET_TAKE_ADMINS", "&9Admin &6%ADMIN%&9 resi ticket cislo &6%ID%&9.");
		check("TICKET_TAKE_CREATOR", "&9Admin &6%ADMIN%&9 resi tvuj ticket cislo &6%ID%&9.");
		check("TICKET_OPENED", "&7Ticket &2#%ID%&7 byl znovuotevren.");
		check("TICKET_CLOSED", "&7Ticket &2#%ID%&7 byl uzavren.");
		check("TICKET_REPLIED", "&9Odpovedel jsi do ticketu &6#%ID%&9.");
		check("TICKET_REPLY_CLOSED", "&cTento ticket je uzavren, proto do nej nemuzes odpovedet.");
		check("TICKET_NOT_YOURS", "&cTento ticket neni tvuj.");
		check("TICKET_DELETED", "&aTicket #%ID% byl smazan.");
		check("SPAWN_SET", "&aSpawn nastaven.");
		check("SPAWN_TP_SELF", "&aByl jsi teleportovan na spawn.");
		check("SPAWN_TP_OTHER", "&7Teleportoval jsi hrace &f%NICK%&7 na spawn.");
		check("SPAWN_TP_WHISPER", "&7Admin &f%NICK%&7 te teleportoval na spawn.");
		check("NOTICE_SCHED", "&6Mas &f%COUNT%&6 neprectenych upozorneni. Zobraz si je prikazem &f/notices&6.");
		check("NOTICE_LIST_EMPTY", "&cNemas zadna nova oznameni.");
		check("NOTICE_LIST_FORMAT", "&f%TIME%: %TEXT%");
		check("NOTICE_MESSAGE_ALL", "&6Pokud chces zobrazit vsechny oznameni, napis &f/notices -all&6.");
		check("NOTICE_GLOBAL_ADD", "&aGlobalni oznameni pridano!");
		check("NOTICE_OTHER_ADD", "&aPridal jsi oznameni hraci &f%NICK%&a.");
		check("EDITSIGN_LINE_INT", "&cRadek musi byt cislo.");
		check("EDITSIGN_MAX_LENGTH", "&cMaximalni delka textu je 15 znaku.");
		check("EDITSIGN_NOT_SIGN", "&cBlock, na ktery se divas, neni cedule.");
		check("EDITSIGN_SUCCESS", "&aCedule upravena!");
		check("EDITSIGN_LINE_MINMAX", "&cRadek musi byt v rozmezi 1 - 4");
		check("SPAM_WARNING", "&cProsim, neopakuj jednu zpravu vicekrat. Diky!");
		check("VANISH_SELF_ON", "&aZapnul sis neviditelnost.");
		check("VANISH_SELF_OFF", "&aVypnul sis neviditelnost.");
		check("VANISH_OTHER_ON", "&7Zapnul jsi neviditelnost hraci &f%NICK%&7.");
		check("VANISH_OTHER_OFF", "&7Vypnul jsi neviditelnost hraci &f%NICK%&7.");
		check("VANISH_WHISPER_ON", "&7Admin &f%NICK%&7 ti zapnul neviditelnost.");
		check("VANISH_WHISPER_OFF", "&7Admin &f%NICK%&7 ti vypnul neviditelnost.");
		check("CHAT_FORMAT", "%PREFIX%%NAME% > %SUFFIX%%MESSAGE%");
		check("KICK_WHISPER", "&2Byl jsi vyhozen adminem &7%ADMIN%&2. Duvod: &7%REASON%");
		check("KICK_BROADCAST", "&7%NICK%&2 byl vyhozen adminem &7%ADMIN%&2. &2Duvod: &7%REASON%");
		check("NEWBIE", "&f%NICK%&d je zde poprve! Vitej!");
		check("DUPEIP_IP_EMPTY", "&cS touto IP se jeste nikdo nepripojil.");
		check("DUPEIP_IP", "&7Hraci hrajici na IP &f%IP%&7: %NICKS%");
		check("DUPEIP_NICK_NOT_EXISTS", "&cS timto nickem nikdo nehraje.");
		check("WARN_BROADCAST", "&7%NICK%&2 byl varovan adminem &7%ADMIN%&2. &2Duvod: &7%REASON%");
		check("WARN_WHISPER", "Byl jsi varovan adminem %ADMIN%. Duvod: %REASON%");
		check("TEMPBAN_AMOUNT_INT", "&cPocet sekund/minut/hodin/dni/mesicu/roku musi byt cislo!");
		check("TEMPBAN_BROADCAST", "&7%NICK%&2 byl docasne zabanovan adminem &7%ADMIN%&2 do &7%TEMPTIME%&2. &2Duvod: &7%REASON%");
		check("TEMPBAN_WHISPER", "&2Byl jsi docasne zabanovan adminem &7%ADMIN%&2 do &7%TEMPTIME%&2. &2Duvod: &7%REASON%");
		check("TEMPBAN_JOIN", "&2Jsi docasne zabanovan adminem &7%ADMIN%&2 do &7%TEMPTIME%&2. Duvod: &7%REASON%");
		check("BAN_ALREADY", "&cTento hrac je jiz zabanovan.");
		check("BAN_BROADCAST", "&7%NICK%&2 byl zabanovan adminem &7%ADMIN%&2. &2Duvod: &7%REASON%");
		check("BAN_WHISPER", "&2Byl jsi zabanovan adminem &7%ADMIN%&2. Duvod: &7%REASON%");
		check("BAN_JOIN", "&2Jsi zabanovan adminem &7%ADMIN%&2. Duvod: &7%REASON%");
		check("UNBAN_NOT_BANNED", "&cTento hrac neni zabanovan.");
		check("UNBAN_BROADCAST", "&7%NICK%&2 byl odbanovan adminem &7%ADMIN%&2.");
		check("CHECKBAN_NO_RECORDS", "&cNebyly nalezeny zadne zaznamy pro hrace &f%NICK%&c.");
		check("CHECKBAN", "&aZobrazuji &f%COUNT%&a zaznamu pro hrace &f%NICK%&a:");
		check("CHECKBAN_LINE", "&4%TYPE%: &e%REASON% &aod: &f%TIME% &aAdmin: &f%ADMIN%");
		check("CHECKBAN_LINE_TB", "&4%TYPE%: &e%REASON% &aod: &f%TIME% &ado: &f%TEMPTIME% &aAdmin: &f%ADMIN%");
		check("CHECKBAN_LINE_WARN", "&4%TYPE%: &e%REASON% &aDatum: &f%TIME% &aAdmin: &f%ADMIN%");
		check("ITEMID", "&aId itemu, ktery drzis: &f%ID%&a.");
		
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