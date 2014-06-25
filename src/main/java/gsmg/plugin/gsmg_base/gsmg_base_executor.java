package gsmg.plugin.gsmg_base;

import gsmg.plugin.gsmg_base.gsmg_lua.Lua_Minigame;
import gsmg.plugin.gsmg_base.gsmg_lua.Lua_Player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.luaj.vm2.LuaValue;

public class gsmg_base_executor implements CommandExecutor {
	private Lua_Player _Lua_Player = new Lua_Player();

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		boolean isPlayer = false;
		if (sender instanceof Player) {
			isPlayer = true;
		}
		if (args.length > 0) {
			String arg1 = args[0].toLowerCase();
			if (arg1.equalsIgnoreCase("help")) {
				sender.sendMessage("----GSMG Command List----");
				sender.sendMessage("/GSMG close (Minigame)");
				sender.sendMessage("/GSMG (Minigame) (Command)");
				sender.sendMessage("/GSMG start (Minigame)");
				sender.sendMessage("/GSMG reload");
				sender.sendMessage("/GSMG reloadlua");
				sender.sendMessage("/GSMG lobby (command) [name]");
				sender.sendMessage("/GSMG world (name) [type]");
			} else if (arg1.equalsIgnoreCase("close")) {

			} else if (arg1.equalsIgnoreCase("start")) {

			} else if (arg1.equalsIgnoreCase("lobby")) {
				if (isPlayer) {
					if (args.length == 3) {
						if (args[1].equalsIgnoreCase("create")) {
							gsmg_base_lobby.Create((Player) sender,
									args[2].toLowerCase());
						} else if (args[1].equalsIgnoreCase("remove")) {
							gsmg_base_lobby.Remove((Player) sender,
									args[2].toLowerCase());
						} else if (args[1].equalsIgnoreCase("teleport")
								|| args[1].equalsIgnoreCase("tp")) {
							gsmg_base_lobby.TeleportPlayerToLobby(
									(Player) sender, args[2].toLowerCase());
						} else if (args[1].equalsIgnoreCase("relocate")) {
							gsmg_base_lobby.Relocate((Player) sender,
									args[2].toLowerCase());
						} else {
							sender.sendMessage(ChatColor.RED
									+ "/GSMG lobby (create/remove/teleport) (name)");
						}
					} else if (args.length == 2) {
						if (args[1].equalsIgnoreCase("list")) {
							gsmg_base_lobby.ListLobbys((Player) sender);
						} else {
							sender.sendMessage(ChatColor.RED
									+ "/GSMG lobby (create/remove/teleport/list) [name]");
						}
					} else {
						sender.sendMessage(ChatColor.RED
								+ "/GSMG lobby (create/remove/teleport/list) [name]");
					}
				} else {
					sender.sendMessage("This command is only available to players.");
				}

			} else if (arg1.equalsIgnoreCase("reloadlua")) {
				gsmg_base_main.Log("Reloading Lua files...");
				gsmg_base_lua.main();
				gsmg_base_main.Log("Reloading complete!");
				if (isPlayer) {
					sender.sendMessage("Reloaded Lua files...");
				}
			} else if (arg1.equalsIgnoreCase("world")) {
				if (args.length >= 3) {
					if (args[1].equalsIgnoreCase("create")
							|| args[1].equalsIgnoreCase("load")) {
						if (args.length >= 4) {
							World.Environment env = World.Environment.NORMAL;
							Long seed = null;
							if (args[3].equalsIgnoreCase("nether")) {
								env = World.Environment.NETHER;
							} else if (args[3].equalsIgnoreCase("the_end")) {
								env = World.Environment.THE_END;
							} else if (!args[3].equalsIgnoreCase("normal")) {
								sender.sendMessage(ChatColor.RED
										+ "That world type does not exist! Available types: normal, nether, the_end");
							}
							if (args.length == 5)
								seed = Long.parseLong(args[4]);
							gsmg_base_world.create(args[2], env, seed);
						} else {
							gsmg_base_world.create(args[2],
									World.Environment.NORMAL, null);
						}
						sender.sendMessage(ChatColor.DARK_GREEN
								+ String.format(
										"World '%s' was created/loaded",
										args[2]));
					} else if (args[1].equalsIgnoreCase("unload")) {
						if (gsmg_base_world.unload(args[2])) {
							sender.sendMessage(ChatColor.DARK_GREEN
									+ String.format("World '%s' was unloaded",
											args[2]));
						} else {
							sender.sendMessage(ChatColor.RED
									+ "That world doesn't exist!");
						}
					} else if (args[1].equalsIgnoreCase("delete")) {
						if (gsmg_base_world.delete(args[2])) {
							sender.sendMessage(ChatColor.DARK_GREEN
									+ String.format(
											"World '%s' was successfully deleted",
											args[2]));
						} else {
							sender.sendMessage(ChatColor.RED
									+ String.format(
											"World '%s' couldn't be deleted",
											args[2]));
						}
					} else if (args[1].equalsIgnoreCase("tp")
							|| args[1].equalsIgnoreCase("teleport")) {
						if (isPlayer) {
							World world = Bukkit.getWorld(args[2]);
							if (world != null) {
								gsmg_base_world.teleportPlayerTo(
										(Player) sender, world);
							} else {
								sender.sendMessage(ChatColor.RED
										+ String.format(
												"World '%s' doesn't exist!",
												args[2]));
							}
						} else {
							if (args.length >= 4) {
								World world = Bukkit.getWorld(args[3]);
								Player player = Bukkit.getPlayer(args[2]);
								if (world != null && player != null) {
									gsmg_base_world.teleportPlayerTo(
											(Player) sender, world);
								} else {
									sender.sendMessage(ChatColor.RED
											+ "That world or player doesn't exist!");
								}
							} else {
								sender.sendMessage(ChatColor.RED
										+ "/GSMG world teleport (player) (world)");
							}
						}
					} else {
						sender.sendMessage(ChatColor.RED
								+ "/GSMG world (create/unload/delete/teleport/list) (name) [type]");
					}
				} else {
					if (args.length == 2) {
						if (args[1].equalsIgnoreCase("list")) {
							sender.sendMessage(ChatColor.GOLD + "World list:");
							for (World w : Bukkit.getWorlds()) {
								ChatColor env = ChatColor.AQUA;
								if (w.getEnvironment() == World.Environment.NORMAL)
									env = ChatColor.GREEN;
								else if (w.getEnvironment() == World.Environment.NETHER)
									env = ChatColor.RED;
								else if (w.getEnvironment() == World.Environment.THE_END)
									env = ChatColor.GRAY;
								sender.sendMessage(String.format(
										"%s%s%s, environment: %s%s",
										ChatColor.GREEN, w.getName(),
										ChatColor.WHITE, env, w
												.getEnvironment().toString()));
							}
						} else {
							sender.sendMessage(ChatColor.RED
									+ "/GSMG world (create/unload/delete/teleport/list) (name) [type]");
						}
					} else {
						sender.sendMessage(ChatColor.RED
								+ "/GSMG world (create/unload/delete/teleport/list) (name) [type]");
					}
				}
			} else {
				if (arg1.equalsIgnoreCase("minigame")
						|| arg1.equalsIgnoreCase("mg")) {
					if (args.length > 2) {
						LuaValue hookedFunc = Lua_Minigame.minigames.onCommandEvents
								.get(args[1].toLowerCase());
						if (hookedFunc != null) {
							LuaValue varargs = LuaValue.tableOf();
							for (int i = 2; i < args.length; i++) {
								varargs.set(i - 1, LuaValue.valueOf(args[i]));
							}
							hookedFunc.call(_Lua_Player.externalGetPlayer(sender.getName()), varargs);
						}
					} else if (args.length == 1) {
						sender.sendMessage(ChatColor.RED
								+ "Command: /GSMG minigame (name) [args]");
					} else {
						sender.sendMessage(String.format(ChatColor.RED
								+ "Command: /GSMG minigame %s [args]",
								args[1].toLowerCase()));
					}
				} else {
					sender.sendMessage(ChatColor.RED
							+ "Command: /GSMG (command) [option]");
					sender.sendMessage(ChatColor.RED
							+ "Try '/GSMG help' for more information");
				}
			}
		}
		return true;
	}
}
