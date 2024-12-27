ZaloAPI = {
    class = luajava.bindClass("com.khanhdz.bot.zalo.ZaloAPI")
}

-- Hàm gửi tin nhắn
-- @param String content: Nội dung tin nhắn
-- @param String toId: ID của người nhận hoặc nhóm nhận
-- @param int type: Loại tin nhắn (0: cá nhân, 1: nhóm)
-- @return Jsonobject: Kết quả từ server Zalo, có thể là nil nếu gửi không thành công hoặc bị lỗi
function ZaloAPI.sendMessageWebChat(content, toId, type)
    return ZaloAPI.class.Instance:sendMessageWebChat(content, toId, type);
end

-- Hàm gửi reply tin nhắn
-- @param String reply: Nội dung tin nhắn
-- @param WebChat message: tin nhắn gốc
-- @return Jsonobject: Kết quả từ server Zalo, có thể là nil nếu gửi không thành công hoặc bị lỗi
function ZaloAPI.replyMessage(reply, msg)
    return ZaloAPI.class.Instance:replyMessage(reply, msg);
end

-- Hàm gửi reply tin nhắn kèm tag @
-- @param String reply: Nội dung tin nhắn
-- @param WebChat message: tin nhắn gốc
-- @return Jsonobject: Kết quả từ server Zalo, có thể là nil nếu gửi không thành công hoặc bị lỗi
function ZaloAPI.replyMessageMention(reply, msg)
    return ZaloAPI.class.Instance:replyMessageMention(reply, msg);
end
