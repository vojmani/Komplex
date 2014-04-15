package cz.vojtamaniak.komplex;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import java.util.SortedMap;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Utils {
	
	public static String buildMessage(String[] arg, int start){
		String message = "";
		
		for(int i = start; i < arg.length; i++){
			message += arg[i] + " ";
		}
		return message;
	}
	
	public static String dateFormat(long time){
		SimpleDateFormat sdf = new SimpleDateFormat("d. M. y, H:m");
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
	
	public void paginate(CommandSender sender, SortedMap<Integer, String> map, int page, int pageLength) {
		sender.sendMessage(ChatColor.YELLOW + "List: Page (" + String.valueOf(page) + " of " + (((map.size() % pageLength) == 0) ? map.size() / pageLength : (map.size() / pageLength) + 1));
		int i = 0, k = 0;
		page--;
		for (final Entry<Integer, String> e : map.entrySet()) {
			        k++;
			        if ((((page * pageLength) + i + 1) == k) && (k != ((page * pageLength) + pageLength + 1))) {
			            i++;
			            sender.sendMessage(ChatColor.YELLOW + " - " + e.getValue());
			        }
			    }
			}
}