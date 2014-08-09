package cz.vojtamaniak.komplex.listeners;

import org.bukkit.block.Sign;
import org.bukkit.event.block.BlockPlaceEvent;

import cz.vojtamaniak.komplex.Komplex;
import cz.vojtamaniak.komplex.Utils;

public class BlockListener extends IListener {

	public BlockListener(Komplex plg) {
		super(plg);
	}

	public void onBlockPlace(BlockPlaceEvent e) {
		if(!e.isCancelled() && Utils.isBlockSign(e.getBlockPlaced().getType())){
			Sign sign = (Sign) e.getBlockPlaced().getState();
			if(e.getPlayer().hasPermission("komplex.sign.format")){
				int i = 0;
				for(String line : sign.getLines()){
					sign.setLine(i, line.replaceAll("&", "§"));
					i++;
				}
				sign.update();
			}
		}
	}
}
