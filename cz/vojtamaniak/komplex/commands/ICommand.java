package cz.vojtamaniak.komplex.commands;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.MessageManager;

abstract public class ICommand {
  
	private Komplex plg;
	private MessageManager msgManager;
	
	public ICommand(Komplex plg){
		this.plg = plg;
		this.msgManager = plg.getMessageManager();
	}
}
