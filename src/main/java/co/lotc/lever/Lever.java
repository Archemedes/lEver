package co.lotc.lever;

import co.lotc.core.bukkit.command.Commands;
import co.lotc.core.bukkit.util.Run;
import co.lotc.core.bukkit.util.WeakBlock;
import co.lotc.core.command.CommandTemplate;
import co.lotc.lever.cmd.*;
import co.lotc.lever.listener.LeverListener;
import co.lotc.lever.listener.SneakToggleListener;
import co.lotc.lever.listener.WarpSignListener;
import lombok.Getter;
import lombok.var;
import net.lordofthecraft.arche.ArcheCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

import static net.md_5.bungee.api.ChatColor.GREEN;

public class Lever extends JavaPlugin {
	private static Lever instance;
	public static Lever get() { return instance; }

	@Getter private WarpManager warpManager = new WarpManager();
	
	@Override
	public void onLoad() {
		instance = this;
	}
	
	@Override
	public void onEnable(){
		sql();
		omni();
		warpManager.init();
		listeners();
		
		command("list", List::new);
		command("near", Near::new);
		command("roll", Roll::new);
		command("invsearch", InvSearch::new);
		command("warp", WarpCommand::new);
		command("item", Item::new);
		command("changematerial", ChangeMaterial::new);
		command("spectate", Spectate::new);
		command("vanish", Vanish::new);
		command("back", Back::new);
		command("viewdistance", ViewDistance::new);
		command("sneak", Sneak::new);
		command("walk", Walk::new);
		command("fly", Fly::new);
		command("mobproximity", MobProximity::new);
		command("horsestats", HorseStats::new);
		command("trash", Trash::new);
		command("showitem", ShowItem::new);
		//command("impersonate", Impersonate::new);
		command("countdown", Countdown::new);
		//command("emote", Emote::new);

		Run.as(this).repeating(61L, ()->Vanish.VANISHED.stream().map(Bukkit::getPlayer).filter(Objects::nonNull)
				.forEach(p->p.sendActionBar(GREEN + "You are vanished!") ));
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.removeScoreboardTag(Vanish.VANISH_PERSIST_TAG)) {
				p.performCommand("vanish");
			}else if(p.removeScoreboardTag(Fly.FLY_PERSIST_TAG)) {
	  		p.setAllowFlight(true);
	  		p.setFlying(true);
	  	}
		}
	}
	
	@Override
	public void onDisable(){
		for(Player p : Bukkit.getOnlinePlayers()) {
	  	UUID u = p.getUniqueId();
	  	
	  	InvSearch.requests.remove(u);

	  	if(p.getOpenInventory().getTopInventory().getHolder() instanceof StaticInventory)
	  		p.closeInventory();
	  	
	  	if(Walk.isWalking(p)) p.setWalkSpeed(0.2F);

	  	if(Vanish.VANISHED.contains(u)) {
	  		Vanish.persist(p);
	  		Vanish.deactivate(p);
	  		p.setAllowFlight(false);
	  	}
		}
	}
	
	private void listeners() {
		Bukkit.getPluginManager().registerEvents(new LeverListener(), this);
		Bukkit.getPluginManager().registerEvents(new SneakToggleListener(this), this);
		Bukkit.getPluginManager().registerEvents(new WarpSignListener(this), this);
	}

	private void omni() {
		if(Bukkit.getPluginManager().isPluginEnabled("Omniscience")) {
			OmniUtil.init();
		}
	}
	
	private void sql() {
		var sql = ArcheCore.getSQLControls();
		try(var c = sql.getConnection(); var s = c.createStatement()){
			s.execute("CREATE TABLE IF NOT EXISTS "+WarpManager.TABLE+"(name TEXT PRIMARY KEY, world TEXT, x INT, y INT, z INT, yaw REAL)");
			ResultSet rs = s.executeQuery("SELECT * FROM " + WarpManager.TABLE);
			while(rs.next()) {
				String name = rs.getString("name");
				String world = rs.getString("world");
				int x = rs.getInt("x");
				int y = rs.getInt("y");
				int z = rs.getInt("z");
				float yaw = rs.getFloat("yaw");
				
				warpManager.load(name, new Warp(name, new WeakBlock(world, x, y, z), yaw));
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void command(String name, Supplier<CommandTemplate> supplier) {
		Commands.build(getCommand(name), supplier);
	}
	
	public static class StaticInventory implements InventoryHolder{
		@Getter private final Inventory inventory;
		public StaticInventory(int size, String title) {
			inventory = Bukkit.createInventory(this, size, title);
		}
	}
}
