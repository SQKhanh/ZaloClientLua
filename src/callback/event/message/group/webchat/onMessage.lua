local this = {}

--- Xử lý khi nhận được message
-- @param: com.khanhdz.bot.zalo.model.dto.msgType.WebChat: msg,
function this.process(msg)

    if msg:isFromMaster() then
        print('is master');

        print(string.format([[
            ??? group on message ???
            msg: %s 
        ]], msg:getData():toString(4)));

        if ZaloConfigHelper.class:isGroupActive(msg:getThreadId()) then
            print('is group active');
        else
            print('is group not active');

            local content = msg:getContent();

            if content == 'get group id' then
                local json = ZaloAPI.replyMessage(string.format([[Thưa đại ca đây là idGroup: %s]],
                    msg:getThreadId()), msg);

                if json == nil then
                    print("Gửi tin nhắn không thành công")
                else
                    print(string.format([[
                        Gửi tin nhắn thành công
                        json: %s
                    ]], json:toString(4)))
                end
            else
                local json = ZaloAPI.replyMessage('Em nhận được tin nhắn rồi ạ ?', msg);

                if json == nil then
                    print("Gửi tin nhắn không thành công")
                else
                    print(string.format([[
                        Gửi tin nhắn thành công
                        json: %s
                    ]], json:toString(4)))
                end
            end

        end

    else
        print('is not master');
    end

end

return this
