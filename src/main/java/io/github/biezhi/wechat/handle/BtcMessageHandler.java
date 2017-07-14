package io.github.biezhi.wechat.handle;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.github.biezhi.wechat.model.Environment;
import io.github.biezhi.wechat.model.GroupMessage;
import io.github.biezhi.wechat.model.UserMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/7/14.
 */
@Slf4j
public class BtcMessageHandler implements MessageHandle {
    private Set<String> ipwhiteList;
    public BtcMessageHandler(Environment environment) {
        ipwhiteList = new HashSet<String>();
        String ipwhite = environment.get("white.ip.List");
        for(String ip:ipwhite.split(",")){
            ipwhiteList.add(ip);
        }
    }

    @Override
    public void wxSync(JsonObject msg) {

    }

    @Override
    public void userMessage(UserMessage userMessage) {
        if (null == userMessage || userMessage.isEmpty()) {
            return;
        }
        String text = userMessage.getText();
        JsonObject raw_msg = userMessage.getRawMsg();
        String toUid = raw_msg.get("FromUserName").getAsString();
        if(text.startsWith("btc")&&ipwhiteList.contains(userMessage.getWechatApi().getUserById(toUid).get("showName"))){
            userMessage.sendText("当前比特币价格为：100",toUid);
        }
        log.info(raw_msg.toString());
        log.info(toUid);
        log.info(userMessage.toString());
    }

    @Override
    public void groupMessage(GroupMessage groupMessage) {

    }

    @Override
    public void groupMemberChange(String groupId, JsonArray memberList) {

    }

    @Override
    public void groupListChange(String groupId, JsonArray memberList) {

    }
}
