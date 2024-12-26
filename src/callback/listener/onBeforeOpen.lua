local this = {}

--- Xử lý trước khi kết nối WebSocket được mở
function this.process()
    -- In thông tin về HttpStatus và HttpStatusMessage
    print([[
        before socket open ???
    ]])

    local json = ZaloAPI.sendMessage("Hello", "5090396119491093489", 1);

    if json == nil then
        print("Gửi tin nhắn không thành công")
    else
        print(string.format([[
            Gửi tin nhắn thành công
            json: %s
        ]], json:toString(4)))
    end

end

return this
