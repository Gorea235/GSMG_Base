function PrintTable ( tbl , level )
  local level = level or 0
  local setSpaces = function (num, msg)
    pre = ""
    num = num - 1
    for i = 0, num do
      pre = pre.."  "
    end
    return pre..msg
  end
  print(setSpaces(level, tostring(tbl)))
  level = level + 1
  for i, k in pairs(tbl) do
    if type(k) == "table" then
      PrintTable(k, level)
    else
      print(setSpaces(level, tostring(i).." = "..tostring(k)))
    end
  end
  level = level - 1
end

function wait ( secs )
  local t0 = os.clock()
  while os.clock() - t0 <= secs do end
end

table.tostring = function ( tbl )
  local str = "{"
  local comma = ""
  for k, v in pairs(tbl) do
    local key = nil
    if type(k) == "string" then
      key = k
    elseif type(k) == "number" or type(k) == "boolean" or type(k) == "function" then
      key = tostring(k)
    end
    local value = nil
    if type(v) == "string" then
      value = v
    elseif type(v) == "number" or type(v) == "boolean" then
      value = tostring(v)
    elseif type(v) == "table" then
      value = table.tostring(v)
    end
    if key ~= nil and value ~= nil then
      str = str..comma.." [\""..key.."\"] = \""..value.."\""
    end
    comma = ","
  end
  str = str.." }"
  return str
end

table.fromstring = function ( str )
  str = "return "..str
  return assert(load(str))()
end

function loadlibrary ( file )
  lib = io.open( file )
  if lib ~= nil then
    chunk = lib:read("*a")
    return assert(load(chunk))()
  else
    error("File '"..file.."' does not exists!")
  end
end