package io.github.biezhi.wechat.util;

import java.net.SocketTimeoutException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpUtils {
    private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

    public static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0";

    public static Connection getConnectionForPost(String url, Map<String, String> datas) {
        url = appendHttpString(url);
        Connection connection = Jsoup.connect(url)
                .userAgent(USER_AGENT).timeout(5000)
                .method(Connection.Method.POST);
        if (datas != null && !datas.isEmpty()) {
            connection.data(datas);
        }
        return connection;
    }

    private static String appendHttpString(String url) {
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        return url;
    }

    public static Connection getConnectionForGetNoCookies(String url, Map<String, String>... datas) {
        url = appendHttpString(url);
        Connection connection = Jsoup.connect(url).userAgent(USER_AGENT).ignoreContentType(true).timeout(5000);
        if (datas != null && datas.length > 0 && !datas[0].isEmpty()) {
            connection.data(datas[0]);
        }

        return connection;
    }

    public static Connection getConnectionForGet(String url, Map<String, String>... datas) {
        return getConnectionForGetNoCookies(url, datas);
    }

    public static String getContentForGet(String url, int timeout) {
        try {
            Document objectDoc;
            try {
                Connection connection = getConnectionForGetNoCookies(url).timeout(timeout);
                objectDoc = connection.get();
            } catch (SocketTimeoutException e) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    // ignore
                }
                Connection connection = getConnectionForGetNoCookies(url).timeout(timeout);
                objectDoc = connection.get();
            }
            return objectDoc.body().text();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        String btcAndLtc = "https://data.btcchina.com/data/ticker?market=all";
//        String ethcny = "https://plus-api.btcchina.com/account/market/ETHCNY";
//        String iocCny = "https://plus-api.btcchina.com/account/market/ICOCNY";
        String btcAndLtcres = getContentForGet(btcAndLtc,5000);
        JSONObject btcAndLtcObject = JSON.parseObject(btcAndLtcres);
        System.out.println(btcAndLtcObject);
        System.out.println(btcAndLtcObject.getJSONObject("ticker_btccny").get("buy"));
        System.out.println(btcAndLtcObject.getJSONObject("ticker_btccny").get("sell"));
        System.out.println(btcAndLtcObject.getJSONObject("ticker_btccny").get("last"));
//
//        String ethCnyRes = getContentForGet(ethcny,5000);
//        JSONObject ethCnyObject = JSON.parseObject(ethCnyRes);
//        System.out.println(ethCnyObject);
//        System.out.println(ethCnyObject.getJSONObject("Ticker").get("BidPrice"));
//        System.out.println(ethCnyObject.getJSONObject("Ticker").get("AskPrice"));
//
//        String iocCnyRes = getContentForGet(iocCny,5000);
//        JSONObject iocCnyObject = JSON.parseObject(iocCnyRes);
//        System.out.println(iocCnyObject);
//        System.out.println(iocCnyObject.getJSONObject("Ticker").get("BidPrice"));
//        System.out.println(iocCnyObject.getJSONObject("Ticker").get("AskPrice"));


/*
        String urlFromHuobi = "http://api.huobi.com/staticmarket/detail_btc_json.js";
        String iocCnyRes = getContentForGet(urlFromHuobi,5000);
        JSONObject iocCnyObject = JSON.parseObject(iocCnyRes);
        System.out.println(iocCnyObject);
        System.out.println(iocCnyObject.getString("p_new"));
*/
    }

}
