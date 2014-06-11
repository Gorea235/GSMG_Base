package gsmg.plugin.gsmg_base.gsmg_lua;

import java.util.*;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.*;

public class Lua_Event extends TwoArgFunction {
	public static Map<LuaValue, LuaValue> PlayerJoinEvent = new HashMap<LuaValue, LuaValue>();
	public static Map<LuaValue, LuaValue> PlayerLeaveEvent = new HashMap<LuaValue, LuaValue>();
	public static Map<LuaValue, LuaValue> PlayerChatEvent = new HashMap<LuaValue, LuaValue>();
	public static Map<LuaValue, LuaValue> PlayerDeathEvent = new HashMap<LuaValue, LuaValue>();
	public static Map<LuaValue, LuaValue> PlayerRespawnEvent = new HashMap<LuaValue, LuaValue>();

	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();
		LuaValue hook = tableOf();
		hook.set("PlayerJoin", new HookPlayerJoinEvent());
		hook.set("PlayerLeave", new HookPlayerLeaveEvent());
		hook.set("PlayerChat", new HookPlayerChatEvent());
		hook.set("PlayerDeath", new HookPlayerDeathEvent());
		hook.set("PlayerRespawn", new HookPlayerRespawnEvent());
		LuaValue unhook = tableOf();
		unhook.set("PlayerJoin", new UnhookPlayerJoinEvent());
		unhook.set("PlayerLeave", new UnhookPlayerLeaveEvent());
		unhook.set("PlayerChat", new UnhookPlayerChatEvent());
		unhook.set("PlayerDeath", new UnhookPlayerDeathEvent());
		unhook.set("PlayerRespawn", new UnhookPlayerRespawnEvent());
		library.set("hook", hook);
		library.set("unhook", unhook);
		return library;
	}

	public class HookPlayerJoinEvent extends TwoArgFunction {
		public LuaValue call(LuaValue name, LuaValue func) throws LuaError {
			if (func.isfunction()) {
				PlayerJoinEvent.put(name, func);
			} else {
				throw new LuaError("Variable is not a function");
			}
			return LuaValue.NIL;
		}
	}

	public class UnhookPlayerJoinEvent extends OneArgFunction {
		public LuaValue call(LuaValue name) throws LuaError {
			if (PlayerJoinEvent.containsKey(name)) {
				PlayerJoinEvent.remove(name);
			} else {
				throw new LuaError("A hook with that identifier does not exist");
			}
			return LuaValue.NIL;
		}
	}

	public class HookPlayerLeaveEvent extends TwoArgFunction {
		public LuaValue call(LuaValue name, LuaValue func) throws LuaError {
			if (func.isfunction()) {
				PlayerLeaveEvent.put(name, func);
			} else {
				throw new LuaError("Variable is not a function");
			}
			return LuaValue.NIL;
		}
	}

	public class UnhookPlayerLeaveEvent extends OneArgFunction {
		public LuaValue call(LuaValue name) throws LuaError {
			if (PlayerLeaveEvent.containsKey(name)) {
				PlayerLeaveEvent.remove(name);
			} else {
				throw new LuaError("A hook with that identifier does not exist");
			}
			return LuaValue.NIL;
		}
	}

	public class HookPlayerChatEvent extends TwoArgFunction {
		public LuaValue call(LuaValue name, LuaValue func) throws LuaError {
			if (func.isfunction()) {
				PlayerChatEvent.put(name, func);
			} else {
				throw new LuaError("Variable is not a function");
			}
			return LuaValue.NIL;
		}
	}

	public class UnhookPlayerChatEvent extends OneArgFunction {
		public LuaValue call(LuaValue name) throws LuaError {
			if (PlayerChatEvent.containsKey(name)) {
				PlayerChatEvent.remove(name);
			} else {
				throw new LuaError("A hook with that identifier does not exist");
			}
			return LuaValue.NIL;
		}
	}

	public class HookPlayerDeathEvent extends TwoArgFunction {
		public LuaValue call(LuaValue name, LuaValue func) throws LuaError {
			if (func.isfunction()) {
				PlayerDeathEvent.put(name, func);
			} else {
				throw new LuaError("Variable is not a function");
			}
			return LuaValue.NIL;
		}
	}

	public class UnhookPlayerDeathEvent extends OneArgFunction {
		public LuaValue call(LuaValue name) throws LuaError {
			if (PlayerDeathEvent.containsKey(name)) {
				PlayerDeathEvent.remove(name);
			} else {
				throw new LuaError("A hook with that identifier does not exist");
			}
			return LuaValue.NIL;
		}
	}

	public class HookPlayerRespawnEvent extends TwoArgFunction {
		public LuaValue call(LuaValue name, LuaValue func) throws LuaError {
			if (func.isfunction()) {
				PlayerRespawnEvent.put(name, func);
			} else {
				throw new LuaError("Variable is not a function");
			}
			return LuaValue.NIL;
		}
	}

	public class UnhookPlayerRespawnEvent extends OneArgFunction {
		public LuaValue call(LuaValue name) throws LuaError {
			if (PlayerRespawnEvent.containsKey(name)) {
				PlayerRespawnEvent.remove(name);
			} else {
				throw new LuaError("A hook with that identifier does not exist");
			}
			return LuaValue.NIL;
		}
	}
}
