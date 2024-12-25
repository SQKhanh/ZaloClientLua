local this = {}

--- Xử lý khi kết nối WebSocket bị ngắt
-- @param: int: code,
-- @param: boolean: reason,
-- @param: boolean: remote,
function this.process(code, reason, remote)
    -- In thông tin về HttpStatus và HttpStatusMessage
    print(string.format([[
        Kết nối đóng: code: %s, reason: %s, remote: %s
    ]], tostring(code), tostring(reason), tostring(remote)))
end

return this
