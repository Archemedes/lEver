package co.lotc.lever.cmd;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import co.lotc.lever.BaseCommand;
import net.lordofthecraft.arche.util.WeakBlock;

public class Back extends BaseCommand{
	public static final Map<UUID, WeakBlock> BACKS = new HashMap<>();
	
	public void invoke(Player p) {
		UUID u = p.getUniqueId();
		validate(BACKS.containsKey(u), "Could not find a location to return you to!");
		
		WeakBlock wb = BACKS.get(u);
		p.teleport(wb.toLocation());
	}
}
