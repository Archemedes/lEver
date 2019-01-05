package co.lotc.lever.cmd;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import co.lotc.lever.BaseCommand;
import net.lordofthecraft.arche.command.annotate.Arg;
import net.lordofthecraft.arche.command.annotate.Default;
import net.lordofthecraft.arche.command.annotate.Range;

public class Item extends BaseCommand {

	public void invoke(Player p, ItemStack is, @Arg("amount") @Default("1") @Range(min=1, max=64) int amount) {
		is.setAmount(amount);
		p.getInventory().addItem(is);
	}

}
