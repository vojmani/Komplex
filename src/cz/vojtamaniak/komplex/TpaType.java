package cz.vojtamaniak.komplex;

public enum TpaType {
	TPA(0),
	HERE(1),
	ALL(2);
	
	int id;
	
	private TpaType(int id){
		this.id = id;
	}
	
	public TpaType getById(int id){
		for(TpaType type : TpaType.values()){
			if(type.getId() == id){
				return type;
			}
		}
		return null;
	}
	
	public int getId(){
		return this.id;
	}
}
