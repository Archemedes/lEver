package co.lotc.lever.cmd;

import co.lotc.core.Tythan;
import co.lotc.core.bukkit.util.LocationUtil;
import co.lotc.core.bukkit.util.Run;
import co.lotc.core.command.annotate.Cmd;
import co.lotc.lever.BaseCommand;
import co.lotc.lever.Lever.StaticInventory;
import lombok.var;
import net.lordofthecraft.arche.ArcheCore;
import net.lordofthecraft.arche.interfaces.Economy;
import net.lordofthecraft.arche.interfaces.Persona;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.bukkit.ChatColor.*;

public class InvSearch extends BaseCommand {
	public static Map<UUID, UUID> requests = new HashMap<>();

	public void invoke(Persona me, Persona you) {
		validate(LocationUtil.isClose(me.getPlayer(), you.getPlayer()), "That Player is too far away!");
		validate(me != you, "You can't search your own inventory");
		msg("%sSending a request to search the player's inventory.", BLUE);
		requests.put(me.getPlayerUUID(), you.getPlayerUUID());
		you.getPlayer().sendMessage(me.getPlayerName() + BLUE + " requests to search your inventory.");
		you.getPlayer().sendMessage(Tythan.get().chatBuilder()
				.appendButton(GREEN + "Allow", "/invsearch accept")
				.append(' ')
				.appendButton(RED + "Deny", "/invsearch deny").build()
				);

		Run.as(plugin).delayed(400,()->requests.remove(me.getPlayerUUID()));
	}

	@Cmd("Accept the pending inventory search request")
	public void accept(Persona me){
		validate(requests.containsValue(me.getPlayerUUID()), "You have no pending requests");
				
		final Economy econ = ArcheCore.getControls().getEconomy();
    final boolean flag = econ != null;
    final String title = flag ? (econ.currencyNamePlural() + ": " + GREEN + econ.getBalance(me)) : "Searched Possessions";
		
		var iter = requests.entrySet().iterator();
		while(iter.hasNext()) {
			var ent = iter.next();
			if(ent.getValue().equals(me.getPlayerUUID())) {
				iter.remove();
				Player you = Bukkit.getPlayer(ent.getKey());
				if(you != null) {
					msg("%sYou allow %s%s%s to search through your inventory", BLUE, WHITE, you.getName(), BLUE);
					you.sendMessage(me.getName() + BLUE + " has allowed you to search their possessions");
					
					var inv = new StaticInventory(36, title).getInventory();
					var inv2 = me.getPlayer().getInventory();
					for(int i = 0; i < 36; i++) {
						inv.setItem(i, inv2.getItem(i));
					}
					
					you.openInventory(inv);
				}
			}
		}
		
	}
	
	@Cmd("Accept the pending inventory search request")
	public void deny(Persona me){
		validate(requests.containsKey(me.getPlayerUUID()), "You have no pending requests");
		
		msg(LIGHT_PURPLE + "You resist having your possessions searched");
		
		var iter = requests.entrySet().iterator();
		while(iter.hasNext()) {
			var ent = iter.next();
			if(ent.getValue().equals(me.getPlayerUUID())) {
				iter.remove();
				Player you = Bukkit.getPlayer(ent.getKey());
				if(you != null) {
					you.sendMessage(me.getName() + RED + " resists having their possessions searched by you");
				}
			}
		}
	}
}
