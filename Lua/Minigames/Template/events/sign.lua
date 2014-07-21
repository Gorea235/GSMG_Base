function onSignClick ( ply, line1, line2 ) -- called whenever a sign is clicked with the minigame on it
  --[[
    ply: the player who right-clicked the sign. this is a table of functions relating to that player
    line1: the second line of the sign
    line2: the third line of the sing
    
    Only the second and third lines are given because for the function to fire, the first
    line must be '[GSMG]', and the forth (last) must be the minigame name.
    This function could be used to add or remove players from a minigame queue.
  --]]
  
  -- you can put anything here
  this.print( "Player "..ply.getName().." right-clicked a sign with the lines '"..line1.."' and '"..line2.."' on it!" ) -- example usage, very basic
end

return onSignClick -- returns the function