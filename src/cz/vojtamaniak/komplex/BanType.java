package cz.vojtamaniak.komplex;

public enum BanType {
	BAN(0),
	IPBAN(1),
	WARN(2),
	KICK(3),
	UNBAN(5),
	JAIL(6),
	MUTE(7),
	INFO(8),
	PERMA(9),
	TEMPBAN(10),
	TEMPIPBAN(11),
	TEMPJAIL(12);
	
	int id;
	private BanType(int i){
		id = i;
	}
	
	public int getId(){
		return id;
	}
	
	public String toCode(){
		switch(id){
		case 0:
			return "B";
		case 1:
			return "IP";
		case 2:
			return "W";
		case 3:
			return "K";
		case 5:
			return "UN";
		case 6:
			return "J";
		case 7:
			return "M";
		case 10:
			return "TB";
		case 11:
			return "TIP";
		case 12:
			return "TJ";
		default:
			return "?";
		}
	}
}
