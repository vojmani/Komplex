package cz.vojtamaniak.komplex.commands;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.MessageManager;

abstract public class ICommand {
	
	protected Komplex plg;
	protected MessageManager msgManager;
	
	public ICommand(Komplex plg){
		this.plg = plg;
		this.msgManager = plg.getMessageManager();
	}
}
