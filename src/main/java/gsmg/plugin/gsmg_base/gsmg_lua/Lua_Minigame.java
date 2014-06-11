package gsmg.plugin.gsmg_base.gsmg_lua;

import java.util.*;

import org.bukkit.Bukkit;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.*;

public class Lua_Minigame extends TwoArgFunction {
	public static class minigames {
		public static Map<String, HashMap<LuaValue, LuaValue>> globals = new HashMap<String, HashMap<LuaValue, LuaValue>>();
		public static Map<String, LuaValue> onCommandEvents = new HashMap<String, LuaValue>();
		public static Map<String, LuaValue> onSignClickEvents = new HashMap<String, LuaValue>();
	}

	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();
		library.set("register", new registerMinigame());
		library.set("get", new getMinigame());
		library.set("unregister", new unregisterMinigame());
		library.set("exists", new minigameExists());
		return library;
	}

	public class registerMinigame extends OneArgFunction {
		public LuaValue call(LuaValue name) throws LuaError {
			String _name = name.tojstring().toLowerCase();
			if (!minigames.globals.containsKey(_name)) {
				if (_name.contains(" ")) {
					throw new LuaError("Minigame name cannot contain a space");
				}
				minigames.globals.put(_name, new HashMap<LuaValue, LuaValue>());
				return new getMinigame().call(name);
			} else {
				throw new LuaError(String.format(
						"Minigame '%s' already exists", _name));
			}

		}
	}

	public class getMinigame extends OneArgFunction {
		public LuaValue call(LuaValue name) throws LuaError {
			String _name = name.tojstring().toLowerCase();
			if (minigames.globals.containsKey(_name)) {
				LuaValue functions = tableOf();
				functions.set("getVar", new minigame_getVar(_name));
				functions.set("setVar", new minigame_setVar(_name));
				functions.set("clearVars", new minigame_clearVars(_name));
				functions.set("broadcast", new minigame_broadcast(_name));
				functions.set("print", new minigame_print(_name));
				LuaValue eventFuncs = tableOf();
				eventFuncs.set("OnCommand", new minigame_hookOnCommandEvent(
						_name));
				eventFuncs.set("OnSignClick",
						new minigame_hookOnSignClickEvent(_name));
				functions.set("hookEvent", eventFuncs);
				return functions;
			} else {
				throw new LuaError(String.format("Minigame '%s' doesn't exist",
						_name));
			}
		}
	}

	public class unregisterMinigame extends OneArgFunction {
		public LuaValue call(LuaValue name) throws LuaError {
			String _name = name.tojstring().toLowerCase();
			if (minigames.globals.containsKey(_name)) {
				minigames.globals.remove(_name);
			} else {
				throw new LuaError(String.format("Minigame '%s' doesn't exist",
						_name));
			}
			return LuaValue.NIL;
		}
	}

	public class minigameExists extends OneArgFunction {
		public LuaValue call(LuaValue name) {
			if (minigames.globals.containsKey(name.tojstring().toLowerCase())) {
				return LuaValue.TRUE;
			} else {
				return LuaValue.FALSE;
			}
		}
	}

	public class minigame_getVar extends OneArgFunction {
		public String _minigame = null;

		public minigame_getVar(String minigame) {
			this._minigame = minigame;
		}

		public LuaValue call(LuaValue varName) {
			if (minigames.globals.get(_minigame).containsKey(varName)) {
				return minigames.globals.get(_minigame).get(varName);
			}
			return LuaValue.NIL;
		}
	}

	public class minigame_setVar extends TwoArgFunction {
		public String _minigame = null;

		public minigame_setVar(String minigame) {
			this._minigame = minigame;
		}

		public LuaValue call(LuaValue varName, LuaValue var) {
			minigames.globals.get(_minigame).put(varName, var);
			return LuaValue.NIL;
		}
	}

	public class minigame_clearVars extends ZeroArgFunction {
		public String _minigame = null;

		public minigame_clearVars(String minigame) {
			this._minigame = minigame;
		}

		public LuaValue call() {
			minigames.globals.get(_minigame).clear();
			return LuaValue.NIL;
		}
	}

	public class minigame_broadcast extends OneArgFunction {
		public String _minigame = null;

		public minigame_broadcast(String minigame) {
			this._minigame = minigame;
		}

		public LuaValue call(LuaValue msg) {
			Bukkit.broadcastMessage("[GSMG][" + _minigame + "] "
					+ msg.tojstring());
			return LuaValue.NIL;
		}
	}

	public class minigame_print extends VarArgFunction {
		public String _minigame = null;

		public minigame_print(String minigame) {
			this._minigame = minigame;
		}

		public LuaValue onInvoke(Varargs args) {
			Lua_Base.PrintOut.doPrint(args, "[GSMG][" + _minigame + "]");
			return LuaValue.NIL;
		}
	}

	public class minigame_hookOnCommandEvent extends OneArgFunction {
		public String _minigame = null;

		public minigame_hookOnCommandEvent(String minigame) {
			this._minigame = minigame;
		}

		public LuaValue call(LuaValue func) throws LuaError {
			if (func.isfunction()) {
				minigames.onCommandEvents.put(_minigame, func);
			} else {
				throw new LuaError("Variable isn't a function");
			}
			return LuaValue.NIL;
		}
	}

	public class minigame_hookOnSignClickEvent extends OneArgFunction {
		public String _minigame = null;

		public minigame_hookOnSignClickEvent(String minigame) {
			this._minigame = minigame;
		}

		public LuaValue call(LuaValue func) throws LuaError {
			if (func.isfunction()) {
				minigames.onSignClickEvents.put(_minigame, func);
			} else {
				throw new LuaError("Variable isn't a function");
			}
			return LuaValue.NIL;
		}
	}
}
