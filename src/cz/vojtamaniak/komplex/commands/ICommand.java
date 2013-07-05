package cz.vojtamaniak.komplex.commands;

import cz.vojtamaniak.komplex.Komplex;
import org.bukkit.command.CommandExecutor;
import cz.vojtamaniak.komplex.MessageManager;

abstract public class ICommand implements CommandExecutor {
	
	protected Komplex plg;
	protected MessageManager msgManager;
	
	public ICommand(Komplex plg){
		this.plg = plg;
		this.msgManager = plg.getMessageManager();
	}
}