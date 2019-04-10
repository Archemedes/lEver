package co.lotc.lever.cmd;

import co.lotc.lever.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import static net.md_5.bungee.api.ChatColor.AQUA;
import static net.md_5.bungee.api.ChatColor.WHITE;

public class Skull extends BaseCommand {

	void invoke(Player player, String playerName) {
		validate(player.getInventory().getItemInMainHand().getType() == Material.PLAYER_HEAD, "You need to be holding a player head");
		ItemStack item = player.getInventory().getItemInMainHand();
		SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
		skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(playerName));
		player.sendMessage(AQUA + "Skull head owner set to " + WHITE + playerName);
	}
}
