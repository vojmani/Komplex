package cz.vojtamaniak.komplex;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Material;

public class Utils {
	
	public static String buildMessage(String[] arg, int start){
		StringBuilder sb = new StringBuilder();
		
		for(int i = start; i < arg.length; i++){
			sb.append(arg[i] + " ");
		}
		return sb.toString();
	}
	
	public static String dateFormat(long time){
		SimpleDateFormat sdf = new SimpleDateFormat("d. M. y, HH:mm");
		return sdf.format(new Date(time));
	}
	
	public static boolean isInt(String string){
		try{
			Integer.parseInt(string);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	
	public static long toMillis(int amount, String type){
		long sec = 0;
		
		switch(type.toLowerCase()){
		case "sec":
			sec = amount;
			break;
		case "second":
			sec = amount;
			break;
		case "seconds":
			sec = amount;
			break;
		case "min":
			sec = amount * 60;
			break;
		case "minute":
			sec = amount * 60;
			break;
		case "minutes":
			sec = amount * 60;
			break;
		case "hour":
			sec = amount * 60 * 60;
			break;
		case "hours":
			sec = amount * 60 * 60;
			break;
		case "day":
			sec = amount * 60 * 60 * 24;
			break;
		case "days":
			sec = amount * 60 * 60 * 24;
			break;
		case "month":
			sec = amount * 60 * 60 * 24 * 31;
			break;
		case "months":
			sec = amount * 60 * 60 * 24 * 31;
			break;
		case "year":
			sec = amount * 60 * 60 * 24 * 365;
			break;
		case "years":
			sec = amount * 60 * 60 * 24 * 365;
			break;
		default:
			return -1;
		}
		
		return (sec * 1000);
	}
	
	public static String getMessage(String key, String... replacements){
		String message = MessageManager.getMessage(key);
		
		if(replacements.length != 0){
			if((replacements.length % 2) != 0){
				return "";
			}
			
			int index = 0;
			for(String s : replacements){
				if(s.startsWith("%")){
					message = message.replaceAll(s, replacements[index + 1]);
				}
				index++;
			}
		}
		
		return message;
	}
	
	public static boolean isSign(Material mat){
		List<Material> signs = new ArrayList<Material>();
		signs.add(Material.SIGN);
		signs.add(Material.SIGN_POST);
		signs.add(Material.WALL_SIGN);
		return signs.contains(mat) ? true : false;
	}
}