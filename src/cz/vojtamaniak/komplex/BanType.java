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
	TEMPBAN(10),
	TEMPIPBAN(11),
	TEMPJAIL(12),
	TEMPMUTE(13), 
	UNMUTE(9);
	
	int id;
	private BanType(int i){
		id = i;
	}
	
	public int getId(){
		return id;
	}
	
	public static BanType fromId(int id){
		for(BanType bt : BanType.values()){
			if(bt.getId() == id){
				return bt;
			}
		}
		return null;
	}
	
	public String toCode(){
		return toCode(id);
	}
	
	public static String toCode(int i){
		switch(i){
		case 0:
			return "B";
		case 1:
			return "IP";
		case 2:
			return "W";
		case 3:
			return "K";
		case 5:
			return "UB";
		case 6:
			return "J";
		case 7:
			return "M";
		case 9:
			return "UM";
		case 10:
			return "TB";
		case 11:
			return "TIP";
		case 12:
			return "TJ";
		case 13:
			return "TM";
		default:
			return "?";
		}
	}
}
