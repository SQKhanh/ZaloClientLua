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

    EVENT_MESSAGE_WEBCHAT_DIRECT = require("src.callback.event.message.webchat.direct.onMessage"),
    EVENT_MESSAGE_WEBCHAT_DIRECT_SELF = require("src.callback.event.message.webchat.direct.onSelfMessage"),

    EVENT_MESSAGE_WEBCHAT_GROUP = require("src.callback.event.message.webchat.group.onMessage"),
    EVENT_MESSAGE_WEBCHAT_GROUP_SELF = require("src.callback.event.message.webchat.group.onSelfMessage")
}
