package gsmg.plugin.gsmg_base;

//import java.util.ArrayList;
//import java.util.List;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class gsmg_base_lobby {
	public static Map<String, Lobby> LobbyList = new HashMap<String, Lobby>();
	public static int LobbyLimit = 100;
	public static DecimalFormat df = new DecimalFormat("#.#");

	// Lobby create
	public static void Create(Player player, String name) {
		Location location = player.getLocation();
		if (!(LobbyList.size() > LobbyLimit)) {
			if (!LobbyList.containsKey(name)) {
				LobbyList.put(name, new Lobby(name, location));
				gsmg_base_main.plugin.saveConfigLobby();
				player.sendMessage(ChatColor.GREEN + "Lobby Created As: "
						+ name);
			} else {
				player.sendMessage(ChatColor.RED
						+ "A lobby with that name already exists!");
			}
		} else {
			player.sendMessage(ChatColor.RED + "Lobby Limit Met!");
		}
	}

	public static void Remove(Player player, String lobby) {
		if (LobbyList.containsKey(lobby)) {
			LobbyList.remove(lobby);
			gsmg_base_main.plugin.saveConfigLobby();
			player.sendMessage(ChatColor.GREEN
					+ String.format("Removed lobby '%s'", lobby));
		} else {
			player.sendMessage(String.format(ChatColor.RED
					+ "Could not remove lobby '%s', it doesn't exist!", lobby));
		}
	}

	public static void Relocate(Player player, String lobby) {
		if (LobbyList.containsKey(lobby)) {
			Lobby toChange = LobbyList.get(lobby);
			toChange.location = player.getLocation();
			LobbyList.put(lobby, toChange);
			gsmg_base_main.plugin.saveConfigLobby();
			player.sendMessage(ChatColor.GREEN
					+ String.format("Relocated lobby '%s'", lobby));
		} else {
			player.sendMessage(ChatColor.RED
					+ String.format(
							"Could not relocate lobby '%s', it doesn't exists!",
							lobby));
		}
	}

	public static void ListLobbys(Player player) {
		player.sendMessage(ChatColor.GREEN + "List of lobbys:");
		for (Lobby l : LobbyList.values()) {
			player.sendMessage(ChatColor.GREEN
					+ String.format(
							"Lobby: %s%s%s, Location: %sx=%s, y=%s, z=%s",
							ChatColor.BLUE, l.name, ChatColor.GREEN,
							ChatColor.BLUE, df.format(l.location.getX()),
							df.format(l.location.getY()),
							df.format(l.location.getZ())));
		}
	}

	public static class Lobby {
		public String name = null;
		public Location location = null;

		public Lobby(String n, Location l) {
			this.name = n;
			this.location = l;
		}
	}

	public static boolean TeleportPlayerToLobby(Player player, String lobby) {
		if (LobbyList.containsKey(lobby)) {
			player.teleport(LobbyList.get(lobby).location);
			player.sendMessage(ChatColor.GREEN
					+ String.format("Teleported to lobby '%s'", lobby));
			return true;
		} else {
			gsmg_base_main
					.Log(String
							.format(ChatColor.RED
									+ "Could not teleport player '%s' to lobby '%s' because it doesn't exists!",
									player.getDisplayName(), lobby));
		}
		return false;
	}

	// lobby remove

	// move to lobby
	// public static void Move() {
	// for (Player player : LobbyList) {
	// String lobbyname = lobbynames[i];
	// Location lobbyLocation = lobbylocation[i];
	// player.teleport(lobbylocation);
	// player.sendmessage(ChatColor.GREEN+ "You Have Been Moved To the Lobby");
	// }
	// }
	// side codings timer

	/*
	 * this.getServer().getscheduler() .scheduleAsyncRepatingTask(this,new
	 * runnable() {
	 * 
	 * plublic void run() { if (number != -1) { if number != 0 { //message here
	 * (to put the time in the message add ("" + number)); number--; } else {
	 * Bukkit.broadcastMessage(" ") // the message if the timer = 0 number--; }
	 * } } },0L,20L);
	 */
}
