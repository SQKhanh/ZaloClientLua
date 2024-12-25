local this = {}

--- Xử lý khi xảy ra lỗi trong quá trình kết nối WebSocket
-- @param: java.lang.Exception: exception
function this.process(exception)
    print('error' .. tostring(exception))
end

return this
