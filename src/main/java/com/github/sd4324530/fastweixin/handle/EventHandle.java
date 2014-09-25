package com.github.sd4324530.fastweixin.handle;

import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.req.BaseEvent;

/**
 * 微信事件处理器接口
 * @author peiyu
 * @since 1.1
 */
public interface EventHandle {
    /**
     * 处理微信事件
     * @param event 微信事件
     * @return 回复用户的消息
     */
    BaseMsg handle(BaseEvent event);
}
