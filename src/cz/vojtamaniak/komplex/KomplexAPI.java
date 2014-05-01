package cz.vojtamaniak.komplex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class KomplexAPI {
	
	private Komplex plg;
	private Database database;
	final private BukkitScheduler sched;
	
	public KomplexAPI(Komplex plg){
		this.plg = plg;
		this.database = plg.getDB();
		this.sched = Bukkit.getScheduler();
	}
	
	public User getUser(String player){
		return plg.getUser(player);
	}
	
	public List<BanInfo> getBanCacheUser(String player){
		return plg.banCache.get(player.toLowerCase());
	}
	
	public void addUser(User user){
		plg.addUser(user);
	}
	
	public void removeUser(String player){
		plg.removeUser(player);
	}
	
	public void setAfk(String player, boolean afk){
		getUser(player).setAfk(afk);
	}
	
	public boolean isAfk(String player){
		return getUser(player).isAfk();
	}
	
	public void addNotice(final String player, final String text){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.addNotice(player, text);
				
				if(Bukkit.getPlayer(player) != null){
					plg.getUser(player).setCountOfNotices(database.getCountOfNotices(player));
				}
			}
		});
	}
	
	public void addGlobalNotice(final String text){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.addNotice("-GLOBAL-", text);
				
				for(Player p : Bukkit.getOnlinePlayers()){
					plg.getUser(p.getName()).setCountOfNotices(database.getCountOfNotices(p.getName()));
				}
			}
		});
	}
	
	public void closeTicket(final int id, final boolean close, String creator, String admin){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.closeTicket(id, close);
				database.refreshTicket(id);
			}
		});
		if(close){
			addNotice(creator, "Admin "+ admin +" uzavrel tvuj ticket #"+ id);
		}else{
			addNotice(creator, "Admin "+ admin +" znovuotevrel tvuj ticket #"+ id);
		}
	}
	
	public void deleteTicket(final int id, String creator, String admin, String reason){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.deleteTicket(id);
			}
		});
		
		if(reason == ""){
			addNotice(creator, "Admin "+ admin +" smazal tvuj ticket #"+ id +".");
		}else{
			addNotice(creator, "Admin "+ admin +" smazal tvuj ticket #"+ id +". Duvod: "+ reason);
		}
	}
	
	public HashMap<String, Object> getTicket(int id){
		return database.getTicket(id);
	}
	
	public void deleteWarp(final String name){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.deleteWarp(name);
			}
		});
	}
	
	public Location getWarpLocation(final String name){
		return database.getWarpLocation(name);
	}

	public boolean isDoubleJump(String player){
		return getUser(player).getDoubleJump();
	}
	
	public void setDoubleJump(String player, boolean enabled){
		getUser(player).setDoubleJump(enabled);
	}

	public boolean isGod(String player){
		return getUser(player).getGodMode();
	}
	
	public void setGod(String player, boolean god){
		getUser(player).setGodMode(god);
	}

	public Location getHomeLocation(final String player, final String name){
		return database.getHomeLocation(player, name);
	}
	
	public int getCountOfHomes(final String player){
		return database.getCountOfHomes(player);
	}
	
	public List<String> getHomeList(final String player){
		return database.getHomeList(player);
	}
	
	public List<String> getIgnoredPlayers(final String player){
		return getUser(player).getIgnoredPlayers();
	}
	
	public void addIgnoredPlayer(final String player, final String ignoredPlayer){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.addIgnored(player, ignoredPlayer);
			}
		});
		
		getUser(player).getIgnoredPlayers().add(ignoredPlayer.toLowerCase());
	}
	
	public void removeIgnoredPlayer(final String player, final String ignoredPlayer){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.removeIgnored(player, ignoredPlayer);
			}
		});
		
		getUser(player).getIgnoredPlayers().remove(ignoredPlayer.toLowerCase());
	}
	
	public long getLastMessageTime(String player){
		return getUser(player).getLastMessageTime();
	}
	
	public String getLastMessage(String player){
		return getUser(player).getLastMessage();
	}
	
	public void setLastMessage(String player, String message, long time){
		getUser(player).setLastMessage(message, time);
	}
	
	public void sendMail(final String sender, final String receiver, final String message){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.sendMail(sender, receiver, message);
				
				if(Bukkit.getPlayer(receiver) != null){
					getUser(receiver).setCountOfMails(database.getCountOfMails(receiver));
				}
			}
		});
	}
	
	public HashMap<String, String> getMails(String receiver){
		return database.getMails(receiver);
	}
	
	public void clearMails(final String receiver){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.clearMails(receiver);
			}
		});
		
		getUser(receiver).setCountOfMails(0);
	}

	public TreeMap<Long, String> getNotices(String player, boolean read){
		return database.getNotices(player, read);
	}
	
	public void setReadNotices(final String player){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.setReadNotices(player);
			}
		});
		
		if(Bukkit.getPlayer(player) != null)
			getUser(player).setCountOfNotices(0);
	}
	
	public CommandSender getLastPMSender(String player){
		return getUser(player).getLastPMSender();
	}
	
	public void setLastConsolePM(CommandSender sender){
		plg.setLastConsolePM(sender);
	}
	
	public void setLastPMSender(String player, CommandSender sender){
		getUser(player).setLastPM(sender);
	}
	
	public CommandSender getLastConsolePMSender(){
		return plg.getLastConsolePMSender();
	}
	
	public void addAdminReplyTicket(final int id, final String message, final String adminN, final String creator){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.addReplyTicket(id, true, message);
				database.refreshTicket(id);
				database.setTicketAdmin(id, adminN);
			}
		});
		addNotice(creator, "Admin "+ adminN +" odpovedel do tveho ticketu #"+ id);
	}
	
	public void addReplyTicket(final int id, final String message){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.addReplyTicket(id, false, message);
				database.refreshTicket(id);
			}
		});
	}
	
	public void deleteAllHomes(final String player){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.deleteAllHomes(player);
			}
		});
	}
	
	public void addHome(final String player, final String name, final String world, final int x, final int y, final int z){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.addHome(player, name, world, x, y, z);
			}	
		});
	}
	
	public void deleteHome(final String player, final String name){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.deleteHome(player, name);
			}
		});
	}
	
	public void setSpawn(final String world, final double x, final double y, final double z, final float yaw, final float pitch){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.setSpawn(world, x, y, z, yaw, pitch);
			}
		});
		plg.reloadSpawnLocation();
	}
	
	public void addWarp(final String name, final String world, final double x, final double y, final double z, final float yaw, final float pitch){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.addWarp(name, world, (int)x, (int)y, (int)z, yaw, pitch);
			}
		});
	}
	
	public void taketicket(final int id, final String admin){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.refreshTicket(id);
				database.setTicketAdmin(id, admin);
			}
		});
	}
	
	public void addTicket(final String creator, final String message, final String world, final double x, final double y, final double z, final float yaw, final float pitch){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.addTicket(creator, message, world, x, y, z, yaw, pitch);
			}
		});
	}
	
	public List<String> getTickets(boolean all){
		return database.getTickets(all);
	}
	
	public List<String> getWarpList(){
		return database.getWarpList();
	}

	public void setVanish(Player player, boolean vanish){
		if(vanish){
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.hasPermission("komplex.vanish.bypass")){
					p.hidePlayer(player);
				}
			}
			
			plg.getHiddenPlayers().add(player);
		}else{
			for(Player p : Bukkit.getOnlinePlayers()){
				p.showPlayer(player);
			}
			
			plg.getHiddenPlayers().remove(player);
		}
		getUser(player.getName()).setVanish(vanish);
	}
	
	public boolean getVanish(String player){
		return getUser(player).isVanish();
	}
	
	public void addRecord(final String name, final String reason, final String admin, final BanType type, final long temptime){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.addBan(name, reason, admin, type, temptime);
			}	
		});
		
		List<BanInfo> list = new ArrayList<BanInfo>();
		if(plg.banCache.containsKey(name.toLowerCase()))
			list = plg.banCache.get(name.toLowerCase());
		
		list.add(new BanInfo(name, reason, admin, type, System.currentTimeMillis(), temptime));
		plg.banCache.put(name.toLowerCase(), list);
	}

	public void kickPlayer(String name, String reason, String admin) {
		Bukkit.broadcastMessage(plg.getMessageManager().getMessage("KICK_BROADCAST").replaceAll("%NICK%", name).replaceAll("%ADMIN%", admin).replaceAll("%REASON%", reason));
		addRecord(name, reason, admin, BanType.KICK, 0L);
	}

	public void setLastMoveTime(String name, long currentTimeMillis) {
		getUser(name).setLastMoveTime(currentTimeMillis);
	}
	
	public boolean isFirstTimeOnline(String player){
		return database.isFirstTimeOnline(player);
	}
	
	public void addUserDB(final String name, final int currency, final String ip){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.addUser(name, currency, ip);
			}
		});
	}

	public void setUsersLastIP(final String name, final String ip) {
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.setUsersLastIP(name, ip);
			}
		});	
	}
	
	public List<String> getPlayersByIP(String ip){
		return database.getPlayersByIP(ip);
	}
	
	public String getPlayersIP(String player){
		return database.getPlayersIP(player);
	}

	public void addWarn(String name, String reason, String admin) {
		addRecord(name, reason, admin, BanType.WARN, 0L);
	}

	public int getCountOfWarns(String name) {
		int count = 0;
		for(BanInfo info : getBanCacheUser(name)){
			if(info.getType() == BanType.WARN){
				count++;
			}
		}
		
		return count;
	}
	
	public void clearWarns(final String name){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.clearWarns(name);
			}
		});
		
		if(!plg.banCache.containsKey(name.toLowerCase()))
			return;
		
		List<BanInfo> list = plg.banCache.get(name.toLowerCase());
		
		Iterator<BanInfo> i = list.iterator();
		while(i.hasNext()){
			BanInfo info = i.next();
			if(info.getType() == BanType.WARN){
				i.remove();
			}
		}
		
		plg.banCache.put(name.toLowerCase(), list);
	}

	public void tempbanPlayer(String name, long temptime, String reason, String admin) {
		addRecord(name, reason, admin, BanType.TEMPBAN, temptime);
	}
	
	public void addBan(String name, String reason, String admin){
		addRecord(name, reason, admin, BanType.BAN, 0L);
	}
	
	public boolean isPermaBanned(String name){
		if(!plg.banCache.containsKey(name.toLowerCase()))
			return false;
		
		boolean isBanned = false;
		for(BanInfo info : plg.banCache.get(name.toLowerCase())){
			if(info.getType() == BanType.BAN){
				isBanned = true;
			}
		}
		
		return isBanned;
	}
	
	public boolean isBanned(String name){
		if(!plg.banCache.containsKey(name.toLowerCase()))
			return false;
		
		boolean isBanned = false;
		for(BanInfo info : plg.banCache.get(name.toLowerCase())){
			if(info.getType() == BanType.BAN || info.getType() == BanType.TEMPBAN){
				isBanned = true;
			}
		}
		return isBanned;
	}
	
	public void unbanPlayer(final String name, final String admin){
		sched.runTaskAsynchronously(plg, new Runnable(){
			@Override
			public void run(){
				database.removeBan(name);
			}
		});
		
		List<BanInfo> list = plg.banCache.get(name.toLowerCase());
		Iterator<BanInfo> i = list.iterator();
		while(i.hasNext()){
			final BanInfo info = i.next();
			if(info.getType() == BanType.BAN || info.getType() == BanType.TEMPBAN){
				i.remove();
				sched.runTaskAsynchronously(plg, new Runnable(){
					@Override
					public void run(){
						database.addBan(name, "Odbanovan: "+ info.getReason(), admin, BanType.UNBAN, 0L);
					}
				});
				break;
			}
		}
		
		plg.banCache.put(name.toLowerCase(), list);
	}

	public List<BanInfo> getPlayersBanRecords(String name) {
		return database.getPlayersBanRecords(name);
	}
}
