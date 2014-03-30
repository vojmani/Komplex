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
			ps.execute();
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS k_ignore (id INT(11) NOT NULL AUTO_INCREMENT, player VARCHAR(32), ignoredPlayer VARCHAR(32), time BIGINT(20), PRIMARY KEY (`id`) USING BTREE) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;");
			ps.execute();
			ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS k_warps (id INT(11) NOT NULL AUTO_INCREMENT, name VARCHAR(32), world VARCHAR(32), x INT(11) NOT NULL, y INT(11) NOT NULL, z INT(11) NOT NULL, yaw FLOAT(30) NOT NULL, pitch FLOAT(30) NOT NULL, PRIMARY KEY (`id`) USING BTREE) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ROW_FORMAT DYNAMIC;");
			ps.execute();
			ps.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		init();
	}
	
	public void init(){
		
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
