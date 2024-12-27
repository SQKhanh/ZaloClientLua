local this = {

    isPrintLog = false

}

--- Custom sử lý tin nhắn
-- @param: com.khanhdz.bot.zalo.model.dto.msgType.WebChat: msg,
function this.processMasterMsg(msg)

    if this.isPrintLog then
        print(string.format([[
            ??? handle message master ???
            msg: %s 
        ]], msg:getData():toString(4)));
    end

    local content = msg:getContent();

    -- Kiểm tra nếu chuỗi không bắt đầu bằng 'master' thì thoát hàm
    if content:find('^master ') then
        -- Xóa 'master ' khỏi đầu chuỗi và loại bỏ khoảng trắng thừa (nếu có)
        content = content:sub(8):gsub("^%s*(.-)%s*$", "%1")
    else
        return
    end

    -- ZaloAPI.replyMessageMention('content: <' .. content .. '>', msg);

    if content == 'shutdown' then

        ZaloAPI.replyMessageMention('Em shutdown đây bái bai ♥', msg);

        Zalo.shutdown(0)
    elseif content == 'reload lua' then
        Zalo.reloadLua();

        ZaloAPI.replyMessageMention(
            'Đã load lại lua, vui lòng kiểm tra terminal để biết thêm chi tiết quá trình xử lý', msg);

    elseif content == 'group active id' then
        local listIdGroup = ZaloConfigHelper.class:getListGroupActive();

        if listIdGroup == nil then
            print('listIdGroup is nil')
        else
            ZaloAPI.replyMessage('listIdGroup: ' .. listIdGroup:toString(4), msg);
        end

    else
        print('unknow cmd');
        ZaloAPI.replyMessageMention('Không hiểu lệnh', msg);
    end
end

return this
