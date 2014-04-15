package cz.vojtamaniak.komplex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import cz.vojtamaniak.komplex.commands.CommandAdminchat;
import cz.vojtamaniak.komplex.commands.CommandAfk;
import cz.vojtamaniak.komplex.commands.CommandBreak;
import cz.vojtamaniak.komplex.commands.CommandCheckTicket;
import cz.vojtamaniak.komplex.commands.CommandClearChat;
import cz.vojtamaniak.komplex.commands.CommandCloseTicket;
import cz.vojtamaniak.komplex.commands.CommandDeleteHome;
import cz.vojtamaniak.komplex.commands.CommandDeleteTicket;
import cz.vojtamaniak.komplex.commands.CommandDeleteWarp;
import cz.vojtamaniak.komplex.commands.CommandDoubleJump;
import cz.vojtamaniak.komplex.commands.CommandEditSign;
import cz.vojtamaniak.komplex.commands.CommandFeed;
import cz.vojtamaniak.komplex.commands.CommandFly;
import cz.vojtamaniak.komplex.commands.CommandGod;
import cz.vojtamaniak.komplex.commands.CommandHat;
import cz.vojtamaniak.komplex.commands.CommandHeal;
import cz.vojtamaniak.komplex.commands.CommandHelpOp;
import cz.vojtamaniak.komplex.commands.CommandHome;
import cz.vojtamaniak.komplex.commands.CommandIgnore;
import cz.vojtamaniak.komplex.commands.CommandList;
import cz.vojtamaniak.komplex.commands.CommandMail;
import cz.vojtamaniak.komplex.commands.CommandNear;
import cz.vojtamaniak.komplex.commands.CommandNotices;
import cz.vojtamaniak.komplex.commands.CommandPtime;
import cz.vojtamaniak.komplex.commands.CommandReply;
import cz.vojtamaniak.komplex.commands.CommandReplyTicket;
import cz.vojtamaniak.komplex.commands.CommandSetHome;
import cz.vojtamaniak.komplex.commands.CommandSetSpawn;
import cz.vojtamaniak.komplex.commands.CommandSetWarp;
import cz.vojtamaniak.komplex.commands.CommandSpawn;
import cz.vojtamaniak.komplex.commands.CommandTakeTicket;
import cz.vojtamaniak.komplex.commands.CommandTell;
import cz.vojtamaniak.komplex.commands.CommandTicket;
import cz.vojtamaniak.komplex.commands.CommandTickets;
import cz.vojtamaniak.komplex.commands.CommandWarp;
import cz.vojtamaniak.komplex.commands.CommandWorkbench;
import cz.vojtamaniak.komplex.listeners.EntityListener;
import cz.vojtamaniak.komplex.listeners.PlayerListener;

public class Komplex extends JavaPlugin {
	
	private Logger log;
	private MessageManager msgManager;
	private ConfigManager confManager;
	private HashMap<String, User> users;
	private Database database;
	private CommandSender lastPMSender;
	private Location spawnLoc;
	private KomplexAPI api;
	private List<UUID> fallingblocks;
	
	@Override
	public void onEnable(){
		log = getLogger();
		msgManager = new MessageManager(this);
		confManager = new ConfigManager(this);
		users = new HashMap<String, User>();
		database = new Database(this);
		lastPMSender = null;
		spawnLoc = database.getSpawnLocation();
		api = new KomplexAPI(this);
		fallingblocks = new ArrayList<UUID>();
		
		registerExecutors();
		registerListeners();
		
		msgManager.init();
		confManager.init();
		database.load();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			@Override
			public void run(){
				for(Player p : Bukkit.getOnlinePlayers()){
					if(getUser(p.getName()).getCountOfMails() > 0){
						p.sendMessage(msgManager.getMessage("MAIL_INBOX").replaceAll("%COUNT%", ""+ getUser(p.getName()).getCountOfMails()));
					}
					
					if(getUser(p.getName()).getCountOfNotices() > 0){
						p.sendMessage(msgManager.getMessage("NOTICE_SCHED").replaceAll("%COUNT%", ""+ getUser(p.getName()).getCountOfNotices()));
					}
				}
			}
		}, 20L, 20L*60L);
		
		if(Bukkit.getOnlinePlayers().length != 0){
			for(Player p : Bukkit.getOnlinePlayers()){
				User user = new User(p);
				
				for(String player : database.getIgnoredPlayers(p.getName())){
					user.getIgnoredPlayers().add(player.toLowerCase());
				}
				
				user.setCountOfMails(database.getCountOfMails(p.getName()));
				user.setCountOfNotices(database.getCountOfNotices(p.getName()));
				
				addUser(user);
			}
		}
		
		log.info("is enabled.");
	}
	
	@Override
	public void onDisable(){
		log.info("is disabled.");
	}
	
	public MessageManager getMessageManager(){
		return msgManager;
	}
	
	private void registerExecutors(){
		getCommand("afk").setExecutor(new CommandAfk(this));
		getCommand("break").setExecutor(new CommandBreak(this));
		getCommand("feed").setExecutor(new CommandFeed(this));
		getCommand("fly").setExecutor(new CommandFly(this));
		getCommand("clearchat").setExecutor(new CommandClearChat(this));
		getCommand("god").setExecutor(new CommandGod(this));
		getCommand("hat").setExecutor(new CommandHat(this));
		getCommand("heal").setExecutor(new CommandHeal(this));
		getCommand("ptime").setExecutor(new CommandPtime(this));
		getCommand("workbench").setExecutor(new CommandWorkbench(this));
		getCommand("helpop").setExecutor(new CommandHelpOp(this));
		getCommand("list").setExecutor(new CommandList(this));
		getCommand("mail").setExecutor(new CommandMail(this));
		getCommand("tell").setExecutor(new CommandTell(this));
		getCommand("reply").setExecutor(new CommandReply(this));
		getCommand("near").setExecutor(new CommandNear(this));
		getCommand("ignore").setExecutor(new CommandIgnore(this));
		getCommand("adminchat").setExecutor(new CommandAdminchat(this));
		getCommand("setwarp").setExecutor(new CommandSetWarp(this));
		getCommand("warp").setExecutor(new CommandWarp(this));
		getCommand("deleteWarp").setExecutor(new CommandDeleteWarp(this));
		getCommand("doublejump").setExecutor(new CommandDoubleJump(this));
		getCommand("home").setExecutor(new CommandHome(this));
		getCommand("sethome").setExecutor(new CommandSetHome(this));
		getCommand("spawn").setExecutor(new CommandSpawn(this));
		getCommand("setspawn").setExecutor(new CommandSetSpawn(this));
		getCommand("notices").setExecutor(new CommandNotices(this));
		getCommand("taketicket").setExecutor(new CommandTakeTicket(this));
		getCommand("ticket").setExecutor(new CommandTicket(this));
		getCommand("tickets").setExecutor(new CommandTickets(this));
		getCommand("closeticket").setExecutor(new CommandCloseTicket(this));
		getCommand("editsign").setExecutor(new CommandEditSign(this));
		getCommand("replyticket").setExecutor(new CommandReplyTicket(this));
		getCommand("deleteticket").setExecutor(new CommandDeleteTicket(this));
		getCommand("checkticket").setExecutor(new CommandCheckTicket(this));
		getCommand("deletehome").setExecutor(new CommandDeleteHome(this));
	}
	
	private void registerListeners(){
		Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
		Bukkit.getPluginManager().registerEvents(new EntityListener(this), this);
	}
	
	public void addUser(User user){
		if(!users.containsValue(user)){
			users.put(user.getPlayer().getName().toLowerCase(), user);
		}
	}
	
	public User getUser(String name) {
		if(users.containsKey(name.toLowerCase()))
			return users.get(name.toLowerCase());
		return null;
	}
	
	public void removeUser(String name){
		users.remove(name.toLowerCase());
	}
	
	public ConfigManager getConfigManager(){
		return confManager;
	}
	
	public Database getDB(){
		return database;
	}
	
	public void setLastConsolePM(CommandSender sender){
		this.lastPMSender = sender;
	}
	
	public CommandSender getLastConsolePMSender(){
		return lastPMSender;
	}
	
	public Location getSpawnLocation(){
		return spawnLoc;
	}

	public void reloadSpawnLocation() {
		this.spawnLoc = database.getSpawnLocation();
	}
	
	public KomplexAPI getAPI(){
		return api;
	}

	public List<UUID> getFallingblocks() {
		return fallingblocks;
	}
}