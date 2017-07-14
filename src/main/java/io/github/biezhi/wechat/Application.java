package io.github.biezhi.wechat;

import io.github.biezhi.wechat.handle.BtcMessageHandler;
import io.github.biezhi.wechat.operate.GetPriceOperate;
import io.github.biezhi.wechat.robot.MoliRobot;
import io.github.biezhi.wechat.model.Environment;
import io.github.biezhi.wechat.robot.TulingRobot;
import io.github.biezhi.wechat.ui.StartUI;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * wechat启动程序
 */
public class Application {

    public static void main(String[] args) throws Exception {
        Executor executor = Executors.newSingleThreadExecutor();
        System.setProperty("https.protocols", "TLSv1");
        System.setProperty("jsse.enableSNIExtension", "false");
        executor.execute(new GetPriceOperate());
        Environment environment = Environment.of("classpath:config.properties");

        StartUI startUI = new StartUI(environment);

//        startUI.setMsgHandle(new TulingRobot(environment));
        startUI.setMsgHandle(new BtcMessageHandler(environment));
        startUI.start();
    }

}