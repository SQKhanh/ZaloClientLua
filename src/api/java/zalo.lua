Zalo = {
}
local class = luajava.bindClass("com.khanhdz.bot.zalo.Zalo")

function Zalo.shutdown(code)
    class:systemExit(tonumber(code));
end

function Zalo.reloadLua()
    class.Instance:reloadLua();
end
