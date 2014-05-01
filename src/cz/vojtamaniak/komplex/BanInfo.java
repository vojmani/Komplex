package cz.vojtamaniak.komplex;

public class BanInfo {
	
	private String name;
	private String reason;
	private String admin;
	private BanType type;
	private long time;
	private long temptime;
	
	public BanInfo(String name, String reason, String admin, BanType type, long time, long temptime){
		this.name = name;
		this.reason = reason;
		this.admin = admin;
		this.type = type;
		this.time = time;
		this.temptime = temptime;
	}

	public String getName() {
		return name;
	}

	public String getReason() {
		return reason;
	}

	public String getAdmin() {
		return admin;
	}

	public BanType getType() {
		return type;
	}

	public long getTime() {
		return time;
	}

	public long getTemptime() {
		return temptime;
	}
}
