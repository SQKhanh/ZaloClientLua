local this = {}

--- Xử lý khi nhận được message
-- @param: com.khanhdz.bot.zalo.model.dto.MessageFromListen: msg,
function this.process(msg)

    -- print(string.format([[
    --     msg: %s
    -- ]], msg:getData():toString(4)))

    local success, err = pcall(function()
        if msg:getTypeMessage() == 0 then
            -- Xử lý tin nhắn cá nhân
            if not msg:isSelf() then
                print(string.format([[
                    msg: %s
                ]], msg:getData():toString(4)))
            end
        elseif msg:getTypeMessage() == 1 then
            -- Xử lý tin nhắn trong nhóm
            if not msg:isSelf() then
                print(string.format([[
                    msg: %s
                ]], msg:getData():toString(4)))

                if msg:getData():optString("uidFrom", "-1") == '8577675992826788959' then
                    print('shut down ne')
                    Zalo.shutdown(0)
                end

            end
        else
            -- Các loại tin nhắn khác
            print('unknow message')
        end
    end)

    if not success then
        print("Lỗi xử lý tin nhắn: " .. tostring(err))
    end

end

return this
