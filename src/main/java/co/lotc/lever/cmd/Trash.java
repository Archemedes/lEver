package co.lotc.lever.cmd;

import static org.bukkit.ChatColor.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import lombok.Getter;
import net.lordofthecraft.arche.command.CommandTemplate;

public class Trash extends CommandTemplate {

	public static class TrashCan implements InventoryHolder{
		@Getter private final Inventory inventory = Bukkit.createInventory(this, 36, RED +""+ BOLD + "Items are destroyed!!");
	}
	
	public void invoke(Player p) {
		p.openInventory(new TrashCan().getInventory());
	}
}
