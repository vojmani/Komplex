package cz.vojtamaniak.komplex.listeners;

import org.bukkit.event.Listener;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.MessageManager;

abstract public class IListener implements Listener {
	
	protected MessageManager msgManager;
	protected Komplex plg;
	
	public IListener(Komplex plg){
		this.msgManager = plg.getMessageManager();
		this.plg = plg;
	}
}