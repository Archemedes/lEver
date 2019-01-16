package co.lotc.lever.cmd;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import co.lotc.core.bukkit.util.ItemUtil;
import co.lotc.lever.BaseCommand;

public class ChangeMaterial extends BaseCommand{
	
	public void invoke(Player p, Material mat) {
		ItemStack is = p.getInventory().getItemInMainHand();
		validate(ItemUtil.exists(is), "You must be holding an item in hand!");
		
		is.setType(mat);
		p.getInventory().setItemInMainHand(is);
	}
}
