---
-- Module chính xử lý các chức năng mà Java sẽ trả callback
--
-- Session library provides HTTP session management capabilities for OpenResty based
-- applications, libraries and proxies.
--
-- @module MainLuaByKhanhDz
-- @author KhanhDzai - https://www.facebook.com/khanhdepzai.pro/
-- @version 1.0
MainLuaByKhanhDz = {
    callBack_onOpen = require("src.callback.onOpen"),
    callBack_onException = require("src.callback.onException"),
    callBack_onClose = require("src.callback.onClose"),
    callBack_onEvenMessage = require("src.callback.onMessage.onEvenMessage")
}
  
