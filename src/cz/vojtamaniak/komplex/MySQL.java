package cz.vojtamaniak.komplex;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;

public class MySQL {
	
	private Connection con;
	private Statement st;
	
	private FileConfiguration conf;
	private Logger log;
	
	private String host;
	private String user;
	private String password;
	private String database;
	
	private int port;
	
	public MySQL(Komplex plg){
		this.conf = plg.getConfigManager().getConfig();
		this.log = plg.getLogger();
		
		this.host = conf.getString("storage.mysql-host");
		this.user = conf.getString("storage.mysql-user");
		this.password = conf.getString("storage.mysql-pass");
		this.database = conf.getString("storage.mysql-data");
		this.port = 3306;
		
		open();
	}
	
	public ResultSet query(String query){
		try {
			if(con.isClosed()){
				open();
			}
			if(query.toLowerCase().startsWith("select")){
				return st.executeQuery(query);
			}
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void open(){
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
			st = con.createStatement();
			int v1 = st.executeUpdate("CREATE TABLE IF NOT EXISTS k_mail (id INT AUTO_INCREMENT, sender VARCHAR(32), receiver VARCHAR(32), text TEXT, time BIGINT(20), deleted TINYINT(1), PRIMARY KEY (id))");
			if(v1 != 0){
				log.info("[MySQL] Creating table k_mail.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}