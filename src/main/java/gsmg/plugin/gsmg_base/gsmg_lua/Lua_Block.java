package gsmg.plugin.gsmg_base.gsmg_lua;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.*;

public class Lua_Block extends TwoArgFunction {
	public LuaValue call(LuaValue modname, LuaValue env) {
		LuaValue library = tableOf();
		library.set("getBlock", new getBlock());
		return library;
	}

	public class getBlock extends OneArgFunction {
		public LuaValue call(LuaValue location) throws LuaError {
			if (location.istable()) {
				Location loc = Lua_Location.LocationFromLuaValue(location);
				Block block = loc.getBlock();
				LuaValue functions = tableOf();
				functions.set("getTypeID", new block_getTypeID(block));
				functions.set("getType", new block_getType(block));
				functions.set("setTypeID", new block_setTypeID(block));
				functions.set("setType", new block_setType(block));
				functions.set("getLightLevel", new block_getLightLevel(block));
				functions.set("getLocation", new block_getLocation(block));
				functions.set("isPowered", new block_isPowered(block));
				functions.set("getPower", new block_getPower(block));
				return functions;
			} else {
				throw new LuaError("Incorrect arguments");
			}
		}
	}

	public class block_getTypeID extends ZeroArgFunction {
		public Block _block = null;

		public block_getTypeID(Block block) {
			this._block = block;
		}

		@SuppressWarnings("deprecation")
		public LuaValue call() {
			return LuaValue.valueOf(this._block.getTypeId());
		}
	}

	public class block_getType extends ZeroArgFunction {
		public Block _block = null;

		public block_getType(Block block) {
			this._block = block;
		}

		public LuaValue call() {
			return LuaValue.valueOf(this._block.getType().toString());
		}
	}

	public class block_setTypeID extends OneArgFunction {
		public Block _block = null;

		public block_setTypeID(Block block) {
			this._block = block;
		}

		@SuppressWarnings("deprecation")
		public LuaValue call(LuaValue id) {
			return LuaValue.valueOf(this._block.setTypeId(Integer.parseInt(id
					.tojstring())));
		}
	}

	public class block_setType extends OneArgFunction {
		public Block _block = null;

		public block_setType(Block block) {
			this._block = block;
		}

		public LuaValue call(LuaValue type) {
			Material m = Material.getMaterial(type.tojstring().toUpperCase());
			if (m != null) {
				this._block.setType(m);
				return LuaValue.TRUE;
			} else {
				throw new LuaError(String.format(
						"Material '%s' doesn't exist!", type.tojstring()));
			}
		}
	}

	public class block_getLightLevel extends ZeroArgFunction {
		public Block _block = null;

		public block_getLightLevel(Block block) {
			this._block = block;
		}

		public LuaValue call() {
			return LuaValue.valueOf(this._block.getLightLevel());
		}
	}

	public class block_getLocation extends ZeroArgFunction {
		public Block _block = null;

		public block_getLocation(Block block) {
			this._block = block;
		}

		public LuaValue call() {
			return Lua_Location.LuaValueFromLocation(this._block.getLocation(),
					false);
		}
	}

	public class block_isPowered extends ZeroArgFunction {
		public Block _block = null;

		public block_isPowered(Block block) {
			this._block = block;
		}

		public LuaValue call() {
			return LuaValue.valueOf(this._block.isBlockPowered());
		}
	}

	public class block_getPower extends ZeroArgFunction {
		public Block _block = null;

		public block_getPower(Block block) {
			this._block = block;
		}

		public LuaValue call() {
			return LuaValue.valueOf(this._block.getBlockPower());
		}
	}
}
