ZaloAPI = {
    class = luajava.bindClass("com.khanhdz.bot.zalo.ZaloAPI")
}

-- Hàm gửi tin nhắn
-- @param String content: Nội dung tin nhắn
-- @param String toId: ID của người nhận hoặc nhóm nhận
-- @param int type: Loại tin nhắn (0: cá nhân, 1: nhóm)
-- @return Jsonobject: Kết quả từ phương thức Java, có thể là nil nếu gửi không thành công
function ZaloAPI.sendMessage(content, toId, type)
    return ZaloAPI.class.Instance:sendMessage(content, toId, type);
end
