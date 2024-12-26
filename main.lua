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

    LISTENER_ON_BEFORE_OPEN = require("src.callback.listener.onBeforeOpen"),
    LISTENER_ON_OPEN = require("src.callback.listener.onOpen"),
    LISTENER_ON_ERROR = require("src.callback.listener.onError"),
    LISTENER_ON_CLOSE = require("src.callback.listener.onClose"),

    EVENT_MESSAGE_DIRECT_WEBCHAT = require("src.callback.event.message.direct.webchat.onMessage"),
    EVENT_MESSAGE_DIRECT_WEBCHAT_SELF = require("src.callback.event.message.direct.webchat.onSelfMessage"),

    EVENT_MESSAGE_GROUP_WEBCHAT = require("src.callback.event.message.group.webchat.onMessage"),
    EVENT_MESSAGE_GROUP_WEBCHAT_SELF = require("src.callback.event.message.group.webchat.onSelfMessage")
}
