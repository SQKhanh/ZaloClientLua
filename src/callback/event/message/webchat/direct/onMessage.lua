local this = {
    command = require('src.callback.event.message.webchat.command.command')
}

--- Xử lý khi nhận được message
-- @param: com.khanhdz.bot.zalo.model.dto.msgType.WebChat: msg,
function this.process(msg)

    if msg:isFromMaster() then
        this.command.processMasterMsg(msg);
    else
        print('is not master');
    end

end

return this
