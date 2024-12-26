local this = {}

--- Xử lý khi nhận được message từ bản thân
-- @param: com.khanhdz.bot.zalo.model.dto.MessageFromListen: msg,
function this.process(msg)

    print(string.format([[
        ??? direct on self message ???
        msg: %s
    ]], msg:getData():toString(4)))

end

return this
