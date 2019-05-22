package co.lotc.lever.cmd;

import static org.bukkit.ChatColor.*;

import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

import co.lotc.core.bukkit.util.Run;
import co.lotc.core.command.annotate.Cmd;
import co.lotc.core.command.annotate.Default;
import co.lotc.lever.BaseCommand;
import co.lotc.lever.Lever;
import lombok.SneakyThrows;
import lombok.val;

public class Spectate extends BaseCommand {

	public void invoke(Player p, @Default("@s") Player you) {
		if(p != you) {
			p.setGameMode(GameMode.SPECTATOR);
			p.teleport(you);
			Run.as(Lever.get()).delayed(2, ()->p.setSpectatorTarget(you));
			Run.as(Lever.get()).delayed(6, ()->sendCameraPacket(p, you));
			
			msg(GREEN + "You are now spectating " + WHITE + you.getName());
			return;
		}
		
		if(p.getGameMode() == GameMode.SPECTATOR) {
			p.setGameMode(GameMode.SURVIVAL);
			msg(LIGHT_PURPLE + "You are no longer spectating!");
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 4));
		} else {
			p.setGameMode(GameMode.SPECTATOR);
			msg(GREEN + "You are now spectating!");
		}
	}
	
	@SneakyThrows
	private void sendCameraPacket(Player me, Player you) {
		val packet = new PacketContainer(PacketType.Play.Server.CAMERA);
		packet.getIntegers().write(0, you.getEntityId());
		ProtocolLibrary.getProtocolManager().sendServerPacket(me, packet);
	}
	
	@Cmd("List people in spectator mode")
	public void list() {
		String names = Bukkit.getOnlinePlayers().stream()
			.filter(p->p.getGameMode() == GameMode.SPECTATOR)
			.map(Player::getName)
			.collect(Collectors.joining(", "));
		if(StringUtils.isEmpty(names)) names = "none...";
		
		msg(AQUA + "Players in spectator mode: " + names);
	}
}
