local this = {}

--- Xử lý khi kết nối WebSocket được mở
-- @param: org.java_websocket.handshake.ServerHandshake: serverHandshake
function this.process(serverHandshake)
    -- In thông tin về HttpStatus và HttpStatusMessage
    print(string.format([[
        onOpen
        HttpStatus: %s
        HttpStatusMessage: %s
    ]], serverHandshake:getHttpStatus(), serverHandshake:getHttpStatusMessage()))
end

function this.test()
    print("ok em");
end

return this
