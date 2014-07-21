name = "Template" -- name of minigame, allows for better continuity  
location = MiniGamePath..name -- the directory containting this file, try to match cases 
                                -- (as far as I can tell, directories in Lua are case-insensitive)

require( IncludePath.."main" ) -- adds the Lua-written library to this file, all functions are now directly accessable
minigame = require ( class.minigame ) -- requires the minigame library
event = require ( class.event ) -- requires the event library
chatcolours = require ( class.chatcolours ) -- requires the chatcolours library. 'colour' can be spelt 'color' where ever you want
                                              -- e.g. 'chatcolors = require ( class.chatcolors )' would work too (I'm English, that is why I prefer 'colour')
eventLocation = location..separator.."events"..separator -- the directory containing the event-handling lua scripts
commandEvent = loadlibrary( eventLocation.."command.lua" ) -- stores the return from the file (by default a function)
signEvent = loadlibrary( eventLocation.."sign.lua" ) -- stores the return from the file (by default a function)
playerJoinEvent = loadlibrary( eventLocation.."playerJoin.lua" ) -- stores the return from the file (by default a function)
playerLeaveEvent = loadlibrary( eventLocation.."playerLeave.lua" ) -- stores the return from the file (by default a function)
playerChatEvent = loadlibrary( eventLocation.."playerChat.lua" ) -- stores the return from the file (by default a function)
playerDeathEvent = loadlibrary( eventLocation.."playerDeath.lua" ) -- stores the return from the file (by default a function)
playerRespawnEvent = loadlibrary( eventLocation.."playerRespawn.lua" ) -- stores the return from the file (by default a function)
shutdownEvent = loadlibrary( eventLocation.."shutdown.lua" ) -- stores the return from the file (by default a function)

this = minigame.register( name ) -- registers the minigame name 
                                   --(only exists until the server or lua restarts, and can only be done once with each name)
-- Minigame specific events, only one handler per minigame, and they link directly to the minigame, so no UID (Unique Identifier) needed
this.hookEvent.OnCommand( commandEvent ) -- hooks the minigame command to the handler function
this.hookEvent.OnSignClick( signEvent ) -- hooks the sign click to the handler function
-- General events, as many handlers as needed, can be removed if needed. Each one needs a UID, which cannot be the same as any other UID 
  -- (I recommend doing 'MinigameName_' before to make sure it doesn't interfere with any other handler)
event.hook.PlayerJoin( "Template_PlayerJoinEvent", playerJoinEvent ) -- hooks the player join event to the handler function
event.hook.PlayerLeave( "Template_PlayerLeaveEvent", playerLeaveEvent ) -- hooks the player leave event to the handler function
event.hook.PlayerChat( "Template_PlayerChatEvent", playerChatEvent ) -- hooks the player chat event to the handler function
event.hook.PlayerDeath( "Template_PlayerDeathEvent", playerDeathEvent ) -- hooks the player death event to the handler function
event.hook.PlayerRespawn( "Template_RespawnEvent", playerRespawnEvent ) -- hooks the player respawn event to the handler function
event.hook.Shutdown( "Template_ShutdownEvent", shutdownEvent ) -- hooks the shutdown event to the handler function