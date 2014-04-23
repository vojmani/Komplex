package cz.vojtamaniak.komplex.listeners;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import ru.tehkode.permissions.bukkit.PermissionsEx;
import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.User;

public class PlayerListener extends IListener {

	public PlayerListener(Komplex plg) {
		super(plg);
	}
	
	/**
	 * @param e - PlayerJoinEvent
	 * @return
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJoin(PlayerJoinEvent e){
		e.setJoinMessage(null);
		Bukkit.broadcast(msgManager.getMessage("MESSAGE_JOIN").replaceAll("%NICK%", e.getPlayer().getName()), "komplex.messages.onjoin");
		User user = new User(e.getPlayer());
		user.setIgnoredPlayers(api.getIgnoredPlayers(e.getPlayer().getName()));
		user.setCountOfMails(database.getCountOfMails(e.getPlayer().getName()));
		user.setCountOfNotices(database.getCountOfNotices(e.getPlayer().getName()));
		api.addUser(user);
		for(Player hp : plg.getHiddenPlayers()){
			e.getPlayer().hidePlayer(hp);
		}
		
		String prefix = PermissionsEx.getUser(e.getPlayer()).getPrefix();
		String group = PermissionsEx.getUser(e.getPlayer()).getGroups()[0].getName();
		String suffix = PermissionsEx.getUser(e.getPlayer()).getSuffix();
		
		Scoreboard sb = e.getPlayer().getScoreboard();
		Team team = sb.getTeam(group);
		if(team == null)
			team = sb.registerNewTeam(group);
		
		team.addPlayer(e.getPlayer());
		team.setSuffix(" "+ChatColor.translateAlternateColorCodes('&', prefix));
		e.getPlayer().setPlayerListName(ChatColor.translateAlternateColorCodes('&', suffix + e.getPlayer().getName()));
		if(api.isFirstTimeOnline(e.getPlayer().getName())){
			Bukkit.broadcastMessage(msgManager.getMessage("NEWBIE").replaceAll("%NICK%", e.getPlayer().getName()));
			api.addUserDB(e.getPlayer().getName(), 0, e.getPlayer().getAddress().getHostString());
		}else{
			api.setUsersLastIP(e.getPlayer().getName(), e.getPlayer().getAddress().getHostString());
		}
	}
	
	/**
	 * @param e - PlayerQuitEvent
	 * @return
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerQuit(PlayerQuitEvent e){
		e.setQuitMessage(null);
		Bukkit.broadcast(msgManager.getMessage("MESSAGE_QUIT").replaceAll("%NICK%", e.getPlayer().getName()), "komplex.messages.onquit");				
		api.removeUser(e.getPlayer().getName());
	}
	
	/**
	 *  @param e - PlayerKickEvent
	 *  @return
	 *  */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerKick(PlayerKickEvent e){
		api.removeUser(e.getPlayer().getName());
	}
	
	/**
	 *  @param e - PlayerMoveEvent
	 *  @return
	 *  */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerMove(PlayerMoveEvent e){
		Player player = e.getPlayer();
		String pName = player.getName();
		if(api.isAfk(pName)){
			api.setAfk(pName, false);
			Bukkit.broadcast(msgManager.getMessage("AFK_LEAVE").replaceAll("%NICK%", e.getPlayer().getName()), "komplex.messages.afk");
		}
		
		if((api.isDoubleJump(pName)) && (player.getGameMode() != GameMode.CREATIVE) && (player.getLocation().getBlock().getRelative(0, -1, 0).getType() != Material.AIR) && (!player.isFlying())){
			player.setAllowFlight(true);
		}
		
		api.setLastMoveTime(player.getName(), System.currentTimeMillis());
	}
	
	/**
	 * @param e - PlayerToggleFlightEvent
	 * @return
	 * */
	@EventHandler(priority = EventPriority.LOW)
	public void onToggleFlight(PlayerToggleFlightEvent e){
		Player player = e.getPlayer();
		
		if((player.getGameMode() != GameMode.CREATIVE) && (api.isDoubleJump(player.getName()))){
			player.setFlying(false);
			player.setAllowFlight(false);
			e.setCancelled(true);
			player.setVelocity(player.getLocation().getDirection().multiply(1.4).setY(1.0));
			player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1.0F, 1.0F);
		}
	}
	
	/**
	 * @param e - PlayerTeleportEvent
	 * @return
	 * */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerTeleport(PlayerTeleportEvent e){
		if(e.getCause() == TeleportCause.COMMAND){
			e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerChat(AsyncPlayerChatEvent e){
		final String pName = e.getPlayer().getName();
		
		for(Player p : e.getRecipients()){
			if(p.hasPermission("komplex.ignore.bypass") && api.getIgnoredPlayers(p.getName()).contains(pName.toLowerCase())){
				p.sendMessage(msgManager.getMessage("IGNORE_ADD_BYPASS").replaceAll("%PLAYER%", pName));
				api.getIgnoredPlayers(p.getName()).remove(pName.toLowerCase());
			}
			
			if(api.getIgnoredPlayers(p.getName()).contains(pName.toLowerCase())){
				e.getRecipients().remove(p);
			}
		}
		
		if(((System.currentTimeMillis() - plg.getUser(pName).getLastMessageTime()) < 60000) && (plg.getUser(pName).getLastMessage().equals(e.getMessage())) && (!e.getPlayer().hasPermission("komplex.spam.bypass"))){
			e.getPlayer().sendMessage(msgManager.getMessage("SPAM_WARNING"));
			e.setCancelled(true);
		}
		
		String regex = ".{0,}([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5]):[\\d]{1,5}.{0,}";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(e.getMessage());
		if((m.matches()) && (!e.getPlayer().hasPermission("komplex.ipcensor.bypass"))){
			Bukkit.getScheduler().runTask(plg, new Runnable(){
				@Override
				public void run(){
					plg.getServer().dispatchCommand(Bukkit.getConsoleSender(), "kick "+pName+" reklama");
				}
			});
			e.setMessage(e.getMessage().replaceAll("([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5]):[\\d]{1,5}", "*.*.*.*:*****"));
		}
		
		if(!e.isCancelled()){
			plg.getUser(pName).setLastMessage(e.getMessage(), System.currentTimeMillis());
		
			String format = msgManager.getMessage("CHAT_FORMAT");
			String prefix = PermissionsEx.getUser(pName).getPrefix();
			String suffix = PermissionsEx.getUser(pName).getSuffix();
			
			format = format.replace("%PREFIX%", prefix);
			format = format.replace("%SUFFIX%", suffix);
			format = format.replace("%NAME%", pName);
			format = format.replace("%MESSAGE%", e.getMessage());
			e.setFormat(format.replaceAll("&", "§"));
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerRespawn(PlayerRespawnEvent e){
		if(!e.isBedSpawn()){
			if(plg.getSpawnLocation() != null){
				e.setRespawnLocation(plg.getSpawnLocation());
			}
		}
	}
}