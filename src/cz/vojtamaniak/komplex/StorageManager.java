package cz.vojtamaniak.komplex;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class StorageManager {
	
	private Komplex plg;
	private MySQL mysql;
	private FileConfiguration conf;
	private boolean useMysql;
	private Logger log;
	private MessageManager msgManager;
	private File mailsFile;
	private FileConfiguration mails;
	private List<String> mail;
	
	/**
	 * @param plg - Instance of Plugin's main class
	 *  */
	public StorageManager(Komplex plg){
		this.conf = plg.getConfigManager().getConfig();
		this.useMysql = false;
		this.log = plg.getLogger();
		this.plg = plg;
		this.msgManager = plg.getMessageManager();
	}
	
	public void init(){
		switch(conf.getString("storage.type").toLowerCase()){
		case "mysql":
			useMysql = true;
			this.mysql = new MySQL(plg);
			break;
		case "file":
			useMysql = false;
			this.mailsFile = new File(plg.getDataFolder(), "mails.yml");
			this.mails = YamlConfiguration.loadConfiguration(mailsFile);
			if(!mailsFile.exists()){
				try {
					mails.save(mailsFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				log.info("Creating new mails.yml file...");
			}
			break;
		default:
				log.warning("Invalid storage type in config.yml! Trying to use File storage...");
				useMysql = false;
				this.mailsFile = new File(plg.getDataFolder(), "mails.yml");
				this.mails = YamlConfiguration.loadConfiguration(mailsFile);
				if(!mailsFile.exists()){
					try {
						mails.save(mailsFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					log.info("Creating new mails.yml file...");
				}
			break;
		}
	}
	
	/**
	 * @param receiver - receiver's name
	 * @return
	 * */
	public List<String> getMails(String receiver) {
		if(useMysql){
			try {
			mail = new ArrayList<String>();
			ResultSet result = mysql.query("SELECT sender, text FROM k_mail WHERE receiver = '" + receiver +"' AND deleted = 0");
			while(result.next()){
				mail.add(msgManager.getMessage("MAIL_FORMAT").replaceAll("%SENDER%", result.getString("sender")).replaceAll("%MESSAGE%", result.getString("text"))+ "");
			}
			return mail;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			mail = mails.getStringList("mails."+ receiver);
			if(mail.isEmpty()){
				return null;
			}
		}
		return mail;
	}
	
	public void sendMail(String sender, String receiver, String text){
		if(useMysql){
			mysql.query("INSERT INTO k_mail (sender, receiver, text, time, deleted) VALUES ('"+ sender +"', '"+ receiver +"', '"+ text +"', '"+ System.currentTimeMillis() +"', 0)");
		}else{
			mails.set("mails."+ receiver, mails.getStringList("mails."+ receiver).add(msgManager.getMessage("MAIL_FORMAT").replaceAll("%SENDER%", sender).replaceAll("%MESSAGE%", text)));
			try {
				mails.save(mailsFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void clearMails(String receiver){
		if(useMysql){
			mysql.query("UPDATE k_mail SET deleted = 1 WHERE receiver = '"+ receiver +"'");
		}else{
			mails.set("mails."+ receiver, null);
		}
	}
}
