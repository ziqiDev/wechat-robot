package io.github.biezhi.wechat.operate;

import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/7/14.
 */
public class PriceCache {
    private static final Map<String,String> price = new ConcurrentHashMap<String, String>();

    public static String getPrice (){
        return price.toString();
    }
    public static void setPrice(String key , String value){
        price.put(key,value);
    }

    public static void main(String[] args) throws Exception{
//        new Thread(new GetPriceOperate()).start();
//        while(true){
//            Thread.sleep(2000);
//            System.out.println(getPrice());
//        }
        System.out.println(new Timestamp(System.currentTimeMillis()-3*24*3600*1000));
    }
}
