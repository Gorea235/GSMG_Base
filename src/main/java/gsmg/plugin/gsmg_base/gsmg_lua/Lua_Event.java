package gsmg.plugin.gsmg_base.gsmg_lua;

import java.util.*;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.*;

public class Lua_Event extends TwoArgFunction {
	public enum EventEnums {
		PlayerJoinEvent, PlayerLeaveEvent, PlayerChatEvent, PlayerDeathEvent, PlayerRespawnEvent, ShutdownEvent
	}

	public static Map<EventEnums, Map<LuaValue, LuaValue>> events = eventMapBuilder();

	public static Map<EventEnums, Map<LuaValue, LuaValue>> eventMapBuilder() {
		Map<EventEnums, Map<LuaValue, LuaValue>> eventMap = new HashMap<EventEnums, Map<LuaValue, LuaValue>>();
		eventMap.put(EventEnums.PlayerJoinEvent,
				new HashMap<LuaValue, LuaValue>());
		eventMap.put(EventEnums.PlayerLeaveEvent,
				new HashMap<LuaValue, LuaValue>());
		eventMap.put(EventEnums.PlayerChatEvent,
				new HashMap<LuaValue, LuaValue>());
		eventMap.put(EventEnums.PlayerDeathEvent,
				new HashMap<LuaValue, LuaValue>());
		eventMap.put(EventEnums.PlayerRespawnEvent,
				new HashMap<LuaValue, LuaValue>());
		eventMap.put(EventEnums.ShutdownEvent,
				new HashMap<LuaValue, LuaValue>());
		return eventMap;
	}

	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();
		LuaValue hook = tableOf();
		hook.set("PlayerJoin", new HookEvent(EventEnums.PlayerJoinEvent));
		hook.set("PlayerLeave", new HookEvent(EventEnums.PlayerLeaveEvent));
		hook.set("PlayerChat", new HookEvent(EventEnums.PlayerChatEvent));
		hook.set("PlayerDeath", new HookEvent(EventEnums.PlayerDeathEvent));
		hook.set("PlayerRespawn", new HookEvent(EventEnums.PlayerRespawnEvent));
		hook.set("Shutdown", new HookEvent(EventEnums.ShutdownEvent));
		LuaValue unhook = tableOf();
		unhook.set("PlayerJoin", new UnhookEvent(EventEnums.PlayerJoinEvent));
		unhook.set("PlayerLeave", new UnhookEvent(EventEnums.PlayerLeaveEvent));
		unhook.set("PlayerChat", new UnhookEvent(EventEnums.PlayerChatEvent));
		unhook.set("PlayerDeath", new UnhookEvent(EventEnums.PlayerDeathEvent));
		unhook.set("PlayerRespawn", new UnhookEvent(
				EventEnums.PlayerRespawnEvent));
		unhook.set("Shutdown", new UnhookEvent(EventEnums.ShutdownEvent));
		library.set("hook", hook);
		library.set("unhook", unhook);
		return library;
	}

	public class HookEvent extends TwoArgFunction {
		private EventEnums _event;

		public HookEvent(EventEnums e) {
			this._event = e;
		}

		public LuaValue call(LuaValue name, LuaValue func) throws LuaError {
			if (func.isfunction()) {
				events.get(this._event).put(name, func);
			} else {
				throw new LuaError("Variable is not a function");
			}
			return LuaValue.NIL;
		}
	}

	public class UnhookEvent extends OneArgFunction {
		private EventEnums _event;

		public UnhookEvent(EventEnums e) {
			this._event = e;
		}

		public LuaValue call(LuaValue name) throws LuaError {
			if (events.get(this._event).containsKey(name)) {
				events.get(this._event).remove(name);
			} else {
				throw new LuaError("A hook with that identifier does not exist");
			}
			return LuaValue.NIL;
		}
	}
}
