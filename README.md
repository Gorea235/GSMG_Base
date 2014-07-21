GSMG_Base
=========

The base plugin for our mini-game plugins. The other plugins will be written in Lua, this plugin will have the lobbying system and will load the other plugins. It will provide special functions to make the mini-game programming easier.

Current LuaJ Version: LuaJ-JSE 3.0-beta2

[Top jar folder](/src/main/java)

[Java files](/src/main/java/gsmg/plugin/gsmg_base)

TODO List:

- [ ] Commands
  - [x] Write up command list
  - [x] Implement Empty Command Functions
  - [x] Work out how each command will work
  - [ ] Code the commands
- [ ] Lua Addon Loading
  - [x] Implement LuaJ to a stage where Lua files can be loaded and run with custom classes
  - [x] Work out what custom classes need to be built for the Lua
  - [x] Code the classes
  - [ ] Code the first addon
- [ ] Build Lobbying System
  - [x] Work out how lobbying will work
  - [x] Code initial lobbying system
  - [x] Test it, see if changes need to be made
  - [x] Create classes for the Lua custom classes to use

Command List:

- [x] help - give information about the plugin and command to the player who typed the command
- [ ] close - closes off a minigame from use
- [ ] start - opens a minigame for use
- [ ] reload - will reload the plugin selected
- [x] reloadlua - will reload the Lua files
- [x] lobby - allows for lobby creation/deletion etc.
