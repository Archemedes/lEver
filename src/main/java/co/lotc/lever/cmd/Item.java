package co.lotc.lever.cmd;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import co.lotc.core.command.annotate.Arg;
import co.lotc.core.command.annotate.Default;
import co.lotc.core.command.annotate.Range;
import co.lotc.lever.BaseCommand;

public class Item extends BaseCommand {

	public void invoke(Player p, ItemStack is, @Arg("amount") @Default("1") @Range(min=1, max=64) int amount) {
		is.setAmount(amount);
		p.getInventory().addItem(is);
	}

}
