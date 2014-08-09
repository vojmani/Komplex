package cz.vojtamaniak.komplex;

import org.bukkit.entity.Player;

public class TpaInfo {
	
	private Player requester;
	private Player target;
	private TpaType type;
	private long time;
	
	public TpaInfo(Player requester, Player target, TpaType type, long time){
		this.requester = requester;
		this.target = target;
		this.type = type;
		this.time = time;
	}

	public Player getRequester() {
		return requester;
	}

	public void setRequester(Player requester) {
		this.requester = requester;
	}

	public Player getTarget() {
		return target;
	}

	public void setTarget(Player target) {
		this.target = target;
	}

	public TpaType getType() {
		return type;
	}

	public void setType(TpaType type) {
		this.type = type;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
