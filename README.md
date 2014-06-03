GSMG_Base
=========

The base plugin for our mini-game plugins. The other plugins will be written in Lua, this plugin will have the lobbying system and will load the other plugins. It will provide special functions to make the mini-game programming easier.

Current LuaJ Version: LuaJ-JSE 2.0.3

TODO List:

- [ ] Commands
  - [ ] Write up command list
  - [ ] Implement Empty Command Functions
  - [ ] Work out how each command will work
  - [ ] Code the commands
- [ ] Lua Addon Loading
  - [ ] Implement LuaJ to a stage where Lua files can be loaded and run with custom classes
  - [ ] Work out what custom class need to be built for the Lua
  - [ ] Code the classes
  - [ ] Code the first addon
- [ ] Build Lobbying System
  - [ ] Work out how lobbying will work
  - [ ] Code initial lobbying system
  - [ ] Test it, see if changes need to be made
  - [ ] Create classes for the Lua custom classes to use

Command List:

    [ ] help - give information about the plugin and command to the player who typed the command
    [ ] close - closes off a minigame from use
    [ ] start - opens a minigame for use
    [ ] reload - will reload the plugin selected
