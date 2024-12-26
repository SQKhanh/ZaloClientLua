local this = {}

--- Xử lý khi nhận được message
-- @param: com.khanhdz.bot.zalo.model.dto.msgType.WebChat: msg,
function this.process(msg)

    if msg:isFromMaster() then
        print('is master');

        print(string.format([[
            ??? direct on message ???
            msg: %s
        ]], msg:getData():toString(4)));

        local content = msg:getContent();

        if content == 'shutdown' then
            print('shut down ne')
            Zalo.shutdown(0)
        end

    else
        print('is not master');
    end

end

return this
