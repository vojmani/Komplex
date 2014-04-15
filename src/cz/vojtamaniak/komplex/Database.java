package cz.vojtamaniak.komplex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class Database {
	
	private Connection con;
	
	private String host;
	private String user;
	private String password;
	private String database;
	
	private FileConfiguration conf;
	
	public Database(Komplex plg){
		this.conf = plg.getConfigManager().getConfig();
		this.host = conf.getString("storage.mysql-host");
		this.user = conf.getString("storage.mysql-user");
		this.password = conf.getString("storage.mysql-pass");
		this.database = conf.getString("storage.mysql-data");
	}
	
	public Connection getConnection(){
		try{
			if(con != null && !con.isClosed()){
				return con;
			}
			con = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+database, user, password);
			return con;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void load(){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS k_mail (id INT(11) NOT NULL AUTO_INCREMENT, sender VARCHAR(32) NOT NULL,receiver VARCHAR(32) NOT NULL,message TEXT NOT NULL, time BIGINT(20) NOT NULL, deleted TINYINT(1) NOT NULL DEFAULT '0', PRIMARY KEY (`id`) USING BTREE) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;");
			ps.executeUpdate();
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS k_ignore (id INT(11) NOT NULL AUTO_INCREMENT, player VARCHAR(32), ignoredPlayer VARCHAR(32), time BIGINT(20), PRIMARY KEY (`id`) USING BTREE) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;");
			ps.executeUpdate();
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS k_warps (id INT(11) NOT NULL AUTO_INCREMENT, name VARCHAR(32), world VARCHAR(32), x INT(11) NOT NULL, y INT(11) NOT NULL, z INT(11) NOT NULL, yaw FLOAT(30) NOT NULL, pitch FLOAT(30) NOT NULL, PRIMARY KEY (`id`) USING BTREE) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT DYNAMIC;");
			ps.executeUpdate();
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS k_homes (id INT(11) NOT NULL AUTO_INCREMENT, name VARCHAR(32), world VARCHAR(32), x INT(11) NOT NULL, y INT(11) NOT NULL, z INT(11) NOT NULL, owner VARCHAR(32), PRIMARY KEY (`id`) USING BTREE) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT DYNAMIC;");
			ps.executeUpdate();
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS k_tickets (id INT(11) NOT NULL AUTO_INCREMENT, creator VARCHAR(32) NOT NULL, create_time BIGINT(20) NOT NULL, refresh_time BIGINT(20), admin VARCHAR(32), text TEXT, admin_reply TEXT, user_reply TEXT, status VARCHAR(11) NOT NULL, world VARCHAR(32), x INT(10), y INT(10), z INT(10), yaw FLOAT(10), pitch FLOAT(10), PRIMARY KEY (`id`) USING BTREE) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;");
			ps.executeUpdate();
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS k_notices (id INT(11) NOT NULL AUTO_INCREMENT, player VARCHAR(32) NOT NULL, time BIGINT(20), text TEXT, PRIMARY KEY (`id`) USING BTREE) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;");
			ps.executeUpdate();
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS k_notices_read (id INT(11) NOT NULL AUTO_INCREMENT, notice_id INT(11) NOT NULL, player VARCHAR(32) NOT NULL, PRIMARY KEY (`id`) USING BTREE) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;");
			ps.executeUpdate();
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS k_spawn (id INT(11) NOT NULL AUTO_INCREMENT, world VARCHAR(32) NOT NULL, x INT(11) NOT NULL, y INT(11) NOT NULL, z INT(11) NOT NULL, yaw FLOAT(11) NOT NULL, pitch FLOAT(11) NOT NULL, PRIMARY KEY (`id`) USING BTREE) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;");
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void sendMail(String sender, String receiver, String message){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("INSERT INTO k_mail (sender, receiver, message, time) VALUES (?,?,?,?)");
			ps.setString(1, sender);
			ps.setString(2, receiver);
			ps.setString(3, message);
			ps.setLong(4, System.currentTimeMillis());
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void clearMails(String player){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("UPDATE k_mail SET deleted = 1 WHERE receiver = ?");
			ps.setString(1, player);
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public HashMap<String, String> getMails(String player){
		con = getConnection();
		HashMap<String, String> mails = new HashMap<String, String>();
		try{
			PreparedStatement ps = con.prepareStatement("SELECT sender,message FROM k_mail WHERE receiver = ? AND deleted = 0");
			ps.setString(1, player);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				mails.put(rs.getString("sender"), rs.getString("message"));
			}
			close(ps, rs);
			return mails;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return mails;
	}
	
	public int getCountOfMails(String player){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM k_mail WHERE receiver = ? AND deleted = 0");
			ps.setString(1, player);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			while(rs.next()){
				count = rs.getInt("COUNT(*)");
			}
			close(ps,rs);
			return count;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<String> getIgnoredPlayers(String player){
		List<String> ignoredPlayers = new ArrayList<String>();
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("SELECT ignoredPlayer FROM k_ignore WHERE player = ?");
			ps.setString(1, player);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				ignoredPlayers.add(rs.getString("ignoredPlayer"));
			}
			close(ps, rs);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return ignoredPlayers;
	}
	
	public void addIgnored(String player, String ignoredPlayer){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("INSERT INTO k_ignore (player, ignoredPlayer, time) VALUES (?,?,?)");
			ps.setString(1, player);
			ps.setString(2, ignoredPlayer);
			ps.setLong(3, System.currentTimeMillis());
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void removeIgnored(String player, String ignoredPlayer){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("DELETE FROM k_ignore WHERE player = ? AND ignoredPlayer = ?");
			ps.setString(1, player);
			ps.setString(2, ignoredPlayer);
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void addWarp(String name, String world, int x, int y, int z, float yaw, float pitch){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("INSERT INTO k_warps (name, world, x, y, z, yaw, pitch) VALUES (?,?,?,?,?,?,?)");
			ps.setString(1, name);
			ps.setString(2, world);
			ps.setInt(3, x);
			ps.setInt(4, y);
			ps.setInt(5, z);
			ps.setFloat(6, yaw);
			ps.setFloat(7, pitch);
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public List<String> getWarpList(){
		con = getConnection();
		List<String> warps = new ArrayList<String>();
		try{
			PreparedStatement ps = con.prepareStatement("SELECT name FROM k_warps");
			ResultSet rs =  ps.executeQuery();
			while(rs.next()){
				warps.add(rs.getString("name"));
			}
			close(ps, rs);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return warps;
	}
	
	public Location getWarpLocation(String name){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("SELECT * FROM k_warps WHERE name = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			String world = "";
			int x = 0;
			int y = 0;
			int z = 0;
			float yaw = 0F;
			float pitch = 0F;
			while(rs.next()){
				world = rs.getString("world");
				x = rs.getInt("x");
				y = rs.getInt("y");
				z = rs.getInt("z");
				yaw = rs.getFloat("yaw");
				pitch = rs.getFloat("pitch");
			}
			close(ps, rs);
			if(world == ""){
				return null;
			}
			return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteWarp(String name){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("DELETE FROM k_warps WHERE name = ?");
			ps.setString(1, name);
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public int getCountOfHomes(String player){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM k_homes WHERE owner = ?");
			ps.setString(1, player);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			while(rs.next()){
				count = rs.getInt("COUNT(*)");
			}
			close(ps, rs);
			return count;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	public void addHome(String owner, String name, String world, int x, int y, int z){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("INSERT INTO k_homes (name, world, x, y, z, owner) VALUES (?,?,?,?,?,?)");
			ps.setString(1, name);
			ps.setString(2, world);
			ps.setInt(3, x);
			ps.setInt(4, y);
			ps.setInt(5, z);
			ps.setString(6, owner);
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void deleteAllHomes(String owner){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("DELETE FROM k_homes WHERE owner = ?");
			ps.setString(1, owner);
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public Location getHomeLocation(String owner, String name){
		con = getConnection();
		Location loc = null;
		try{
			PreparedStatement ps = con.prepareStatement("SELECT * FROM k_homes WHERE name = ? AND owner = ?");
			ps.setString(1, name);
			ps.setString(2, owner);
			ResultSet rs = ps.executeQuery();
			String world = "";
			int x = 0;
			int y = 0;
			int z = 0;
			while(rs.next()){
				world = rs.getString("world");
				x = rs.getInt("x");
				y = rs.getInt("y");
				z = rs.getInt("z");
			}
			close(ps, rs);
			if(world == ""){
				return null;
			}
			loc = new Location(Bukkit.getWorld(world), x, y, z);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return loc;
	}
	
	public void deleteHome(String owner, String name){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("DELETE FROM k_homes WHERE name = ? AND owner = ?");
			ps.setString(1, name);
			ps.setString(2, owner);
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public List<String> getHomeList(String owner){
		con = getConnection();
		List<String> homes = new ArrayList<String>();
		try{
			PreparedStatement ps = con.prepareStatement("SELECT name FROM k_homes WHERE owner = ?");
			ps.setString(1, owner);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				homes.add(rs.getString("name"));
			}
			close(ps, rs);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return homes;
	}
	
	public void addTicket(String creator, String text, String world, double x, double y, double z, float yaw, float pitch){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("INSERT INTO k_tickets(creator, create_time, refresh_time, text, status, world, x, y, z, yaw, pitch) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, creator);
			ps.setLong(2, System.currentTimeMillis());
			ps.setLong(3, System.currentTimeMillis());
			ps.setString(4, text);
			ps.setString(5, "OPEN");
			ps.setString(6, world);
			ps.setInt(7, (int)x);
			ps.setInt(8, (int)y);
			ps.setInt(9, (int)z);
			ps.setFloat(10, yaw);
			ps.setFloat(11, pitch);
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public HashMap<String, Object> getTicket(int id){
		con = getConnection();
		HashMap<String, Object> ticket = new HashMap<String, Object>();
		try{
			PreparedStatement ps = con.prepareStatement("SELECT * FROM k_tickets WHERE id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				ticket.put("creator", rs.getString("creator"));
				ticket.put("admin", rs.getString("admin"));
				ticket.put("timeCreated", rs.getLong("create_time"));
				ticket.put("refreshTime", rs.getLong("refresh_time"));
				ticket.put("text", rs.getString("text"));
				ticket.put("adminReply", rs.getString("admin_reply"));
				ticket.put("userReply", rs.getString("user_reply"));
				ticket.put("status", rs.getString("status"));
				if(rs.getString("world") != null){
					ticket.put("location", new Location(Bukkit.getWorld(rs.getString("world")), (double)rs.getInt("x"), (double)rs.getInt("y"), (double)rs.getInt("z"), rs.getFloat("yaw"), rs.getFloat("pitch")));
				}else{
					ticket.put("location", null);
				}
			}
			
			close(ps, rs);
			if(ticket.isEmpty()){
				return null;
			}
			
			return ticket;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return ticket;
	}
	
	public void setTicketAdmin(int id, String admin){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("UPDATE k_tickets SET admin = ? WHERE id = ?");
			ps.setString(1, admin);
			ps.setInt(2, id);
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public List<String> getTickets(boolean all){
		con = getConnection();
		List<String> tickets = new ArrayList<String>();
		try{
			if(all){
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `k_tickets`;");
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					String message = "";
					
					if(rs.getString("status") == "CLOSED"){
						message = "§2#" + rs.getInt("id") + "§f " + rs.getString("creator") + ": §8" + rs.getString("text");
					}else{
						if(rs.getString("admin_reply") != null){
							message = "§2#" + rs.getInt("id") + "§f " + rs.getString("creator") + ": §e" + rs.getString("text");
						}else{
							message = "§2#" + rs.getInt("id") + "§f " + rs.getString("creator") + ": " + rs.getString("text");
						}
					}
					
					tickets.add(message);
				}
				close(ps, rs);
			}else{
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `k_tickets` WHERE `status` != 'CLOSED'");
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					String message = "";
					
					if(rs.getString("admin_reply") != null){
						message = "§2#" + rs.getInt("id") + "§f " + rs.getString("creator") + ": §e" + rs.getString("text");
					}else{
						message = "§2#" + rs.getInt("id") + "§f " + rs.getString("creator") + ": " + rs.getString("text");
					}
					
					tickets.add(message);
				}
				close(ps, rs);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return tickets;
	}

	public void closeTicket(int id, boolean close){
		con = getConnection();
		try{
			String status = "OPEN";
			if(close)
				status = "CLOSED";
			PreparedStatement ps = con.prepareStatement("UPDATE `k_tickets` SET `status` = ? WHERE id = ?");
			ps.setString(1, status);
			ps.setInt(2, id);
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void refreshTicket(int id){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("UPDATE `k_tickets` SET `refresh_time` = ? WHERE `id` = ?");
			ps.setLong(1, System.currentTimeMillis());
			ps.setInt(2, id);
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void addReplyTicket(int id, boolean admin, String reply){
		con = getConnection();
		try{
			PreparedStatement ps = null;
			if(admin){
				ps = con.prepareStatement("UPDATE `k_tickets` SET `admin_reply` = ? WHERE `id` = ?");
				ps.setString(1, reply);
				ps.setInt(2, id);
				ps.executeUpdate();
			}else{
				ps = con.prepareStatement("UPDATE `k_tickets` SET `user_reply` = ? WHERE `id` = ?");
				ps.setString(1, reply);
				ps.setInt(2, id);
				ps.executeUpdate();
			}
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void deleteTicket(int id){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("DELETE FROM `k_tickets` WHERE `id` = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void setSpawn(String world, double x, double y, double z, float yaw, float pitch){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("DELETE FROM k_spawn");
			ps.executeUpdate();
			ps = con.prepareStatement("INSERT INTO k_spawn (world, x, y, z, yaw, pitch) VALUES (?,?,?,?,?,?)");
			ps.setString(1, world);
			ps.setInt(2, (int)x);
			ps.setInt(3, (int)y);
			ps.setInt(4, (int)z);
			ps.setFloat(5, yaw);
			ps.setFloat(6, pitch);
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public Location getSpawnLocation(){
		Location loc = null;
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("SELECT * FROM k_spawn;");
			String world = "";
			int x = 0;
			int y = 0;
			int z = 0;
			float yaw = 0F;
			float pitch = 0F;
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				world = rs.getString("world");
				x = rs.getInt("x");
				y = rs.getInt("y");
				z = rs.getInt("z");
				yaw = rs.getFloat("yaw");
				pitch = rs.getFloat("pitch");
			}
			close(ps, rs);
			
			if(world == "")
				return null;
			
			loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return loc;
	}
	
	public void addNotice(String player, String text){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("INSERT INTO k_notices (`player`, `time`, `text`) VALUES (?,?,?)");
			ps.setString(1, player);
			ps.setLong(2, System.currentTimeMillis());
			ps.setString(3, text);
			ps.executeUpdate();
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public int getCountOfNotices(String player){
		con = getConnection();
		try{
			PreparedStatement ps = con.prepareStatement("SELECT COUNT(DISTINCT `k_notices`.`id`) - COUNT(DISTINCT `k_notices_read`.`id`) FROM `k_notices`, `k_notices_read` WHERE `k_notices_read`.`player` = ? AND `k_notices`.`player` IN ('-GLOBAL-', ?)");
			ps.setString(1, player);
			ps.setString(2, player);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			while(rs.next())
				count = rs.getInt(1);
			close(ps, rs);
			return count;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	public HashMap<Long, String> getNotices(String player, boolean read){
		con = getConnection();
		HashMap<Long, String> notices = new HashMap<Long, String>();
		try{
			if(read){
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `k_notices` WHERE `player` = ? OR `player` = '-GLOBAL-' ORDER BY `time` DESC");
				ps.setString(1, player);
				ResultSet rs = ps.executeQuery();
				while(rs.next())
					notices.put(rs.getLong("time"), rs.getString("text"));
				close(ps,rs);
			}else{
				PreparedStatement ps = con.prepareStatement("SELECT * FROM `k_notices` AS `n` WHERE NOT EXISTS(SELECT * FROM `k_notices_read` AS `r` WHERE (`n`.`player` = ? || `n`.`player` = '-GLOBAL-') && `n`.`id` = `r`.`notice_id` && `r`.`player` = ?) AND `n`.`player` IN (?, '-GLOBAL-') ORDER BY `time` DESC");
				ps.setString(1, player);
				ps.setString(2, player);
				ps.setString(3, player);
				ResultSet rs = ps.executeQuery();
				while(rs.next())
					notices.put(rs.getLong("time"), rs.getString("text"));
				close(ps, rs);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return notices;
	}
	
	public List<Integer> getUnreadNoticesIds(String player){
		con = getConnection();
		List<Integer> ids = new ArrayList<Integer>();
		try{
			PreparedStatement ps = con.prepareStatement("SELECT `n`.`id` FROM `k_notices` AS `n` WHERE NOT EXISTS(SELECT * FROM `k_notices_read` AS `r` WHERE `n`.`id` = `r`.`notice_id` && `r`.`player` = ?) && `n`.`player` IN ('-GLOBAL-', ?)");
			ps.setString(1, player);
			ps.setString(2, player);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
				ids.add(rs.getInt("id"));
			close(ps, rs);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return ids;
	}
	
	public void setReadNotices(String player){
		con = getConnection();
		try{
			PreparedStatement ps = null;
			for(Integer id : getUnreadNoticesIds(player)){
				ps = con.prepareStatement("INSERT INTO `k_notices_read` (notice_id, player) VALUES (?,?)");
				ps.setInt(1, id);
				ps.setString(2, player);
				ps.executeUpdate();
			}
			close(ps, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void close(PreparedStatement ps, ResultSet rs){
		try {
			if(ps != null)
				ps.close();
			if(rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
