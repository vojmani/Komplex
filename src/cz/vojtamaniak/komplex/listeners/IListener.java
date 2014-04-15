package cz.vojtamaniak.komplex.listeners;

import org.bukkit.event.Listener;

import cz.vojtamaniak.komplex.Database;
import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.KomplexAPI;
import cz.vojtamaniak.komplex.MessageManager;

abstract public class IListener implements Listener {
	
	protected MessageManager msgManager;
	protected Komplex plg;
	protected Database database;
	protected KomplexAPI api;
	
	public IListener(Komplex plg){
		this.database = plg.getDB();
		this.msgManager = plg.getMessageManager();
		this.plg = plg;
		this.api = plg.getAPI();
	}
}