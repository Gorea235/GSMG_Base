package gsmg.plugin.gsmg_base.gsmg_lua;

import java.util.*;

import org.bukkit.Bukkit;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.*;

public class Lua_Minigame extends TwoArgFunction {
	public Map<String, HashMap<LuaValue, LuaValue>> minigames = new HashMap<String, HashMap<LuaValue, LuaValue>>();
	
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
			if (!minigames.containsKey(name)) {
				minigames.put(name.tojstring(),
						new HashMap<LuaValue, LuaValue>());
				return new getMinigame().call(name);
			} else {
				throw new LuaError(String.format(
						"Minigame '%s' already exists", name.tojstring()));
			}

		}
	}

	public class getMinigame extends OneArgFunction {
		public LuaValue call(LuaValue name) throws LuaError {
			if (minigames.containsKey(name.tojstring())) {
				LuaValue functions = tableOf();
				functions.set("getVar", new minigame_getVar(name.tojstring()));
				functions.set("setVar", new minigame_setVar(name.tojstring()));
				functions.set("clearVars",
						new minigame_clearVars(name.tojstring()));
				functions.set("broadcast",
						new minigame_broadcast(name.tojstring()));
				functions.set("print", new minigame_print(name.tojstring()));
				return functions;
			} else {
				throw new LuaError(String.format("Minigame '%s' doesn't exist",
						name.tojstring()));
			}
		}
	}

	public class unregisterMinigame extends OneArgFunction {
		public LuaValue call(LuaValue name) throws LuaError {
			if (minigames.containsKey(name.tojstring())) {
				minigames.remove(name);
			} else {
				throw new LuaError(String.format("Minigame '%s' doesn't exist",
						name.tojstring()));
			}
			return LuaValue.NIL;
		}
	}

	public class minigameExists extends OneArgFunction {
		public LuaValue call(LuaValue name) {
			if (minigames.containsKey(name.tojstring())) {
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
			if (minigames.get(_minigame).containsKey(varName)) {
				return minigames.get(_minigame).get(varName);
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
			minigames.get(_minigame).put(varName, var);
			return LuaValue.NIL;
		}
	}

	public class minigame_clearVars extends ZeroArgFunction {
		public String _minigame = null;

		public minigame_clearVars(String minigame) {
			this._minigame = minigame;
		}

		public LuaValue call() {
			minigames.get(_minigame).clear();
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
}
