package cz.vojtamaniak.komplex;

public class Utils {
	
	public static String buildMessage(String[] arg, int start){
		String message = "";
		
		for(int i = start; i < arg.length; i++){
			message += arg[i] + " ";
		}
		return message;
	}
}