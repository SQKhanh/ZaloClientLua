Zalo = {
    class = luajava.bindClass("com.khanhdz.bot.zalo.Zalo")
}

function Zalo.shutdown(code)
    Zalo.class:systemExit(tonumber(code));
end
