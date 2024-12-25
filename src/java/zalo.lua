Zalo = {
    zalo = luajava.bindClass("com.khanhdz.bot.zalo.Zalo")
}

function Zalo.shutdown(code)
    Zalo.zalo:systemExit(tonumber(code));
end
