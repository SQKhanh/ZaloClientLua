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
        elseif content == 'reload lua' then
            Zalo.reloadLua();
            print('reload lua done')
        elseif content == 'group active ?' then
            local listIdGroup = ZaloConfigHelper.class:getListGroupActive();

            if listIdGroup == nil then
                print('listIdGroup is nil')
            else
                local json = ZaloAPI.replyMessage('listIdGroup: ' .. listIdGroup:toString(4), msg);

                if json == nil then
                    print("Gửi tin nhắn không thành công")
                else
                    print(string.format([[
                        Gửi tin nhắn thành công
                        json: %s
                    ]], json:toString(4)))
                end
            end

        else

        end
        
    else
        print('is not master');
    end

end

return this
