package co.lotc.lever;

import co.lotc.core.bukkit.command.Commands;
import co.lotc.core.bukkit.util.WeakBlock;
import co.lotc.core.command.CommandTemplate;
import co.lotc.lever.cmd.*;
import lombok.Getter;
import lombok.var;
import net.lordofthecraft.arche.ArcheCore;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

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
		
		command("roll", Roll::new);
		command("invsearch", InvSearch::new);
		command("warp", WarpCommand::new);
		command("item", Item::new);
		command("changematerial", ChangeMaterial::new);
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
		command("warpimport", WarpImport::new);
		//command("impersonate", Impersonate::new);
	}
	
	private void listeners() {
		Bukkit.getPluginManager().registerEvents(new LeverListener(), this);
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
	
	@Override
	public void onDisable(){

	}
	
	public static class StaticInventory implements InventoryHolder{
		@Getter private final Inventory inventory;
		public StaticInventory(int size, String title) {
			inventory = Bukkit.createInventory(this, size, title);
		}
	}
}
