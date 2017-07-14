package io.github.biezhi.wechat.operate;

import com.alibaba.fastjson.JSONObject;

import io.github.biezhi.wechat.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/7/14.
 */
@Slf4j
public class GetPriceOperate implements Runnable {
    private static final Integer TIME_OUT = 5000;
    private static final String BTC_BTCLTC = "https://data.btcchina.com/data/ticker?market=all";
    private static final String BTC_ETH = "https://plus-api.btcchina.com/account/market/ETHCNY";
    private static final String HUOBI_BTC = "http://api.huobi.com/staticmarket/detail_btc_json.js";
    private static final String HUOBI_LTC = "http://api.huobi.com/staticmarket/ticker_ltc_json.js";
    private static final String HUOBI_ETH = "http://be.huobi.com/market/trade?symbol=ethcny";

    @Override
    public void run() {
        while (true) {
            try {
                getPrice();
                Thread.sleep(1000);
            } catch (Exception e) {
                log.error("获取价格出错", e);
            }

        }
    }

    private void getPrice() {
        String btc_btcAndltc = HttpUtils.getContentForGet(BTC_BTCLTC, TIME_OUT);
        String btc_btc_last = JSONObject.parseObject(btc_btcAndltc).getJSONObject("ticker_btccny").getString("last");
        String btc_ltc_last = JSONObject.parseObject(btc_btcAndltc).getJSONObject("ticker_ltccny").getString("last");
        String btc_eth = HttpUtils.getContentForGet(BTC_ETH, TIME_OUT);
        String btc_eth_last = JSONObject.parseObject(btc_eth).getJSONObject("Ticker").getString("Last");
        String huobi_btc = HttpUtils.getContentForGet(HUOBI_BTC, TIME_OUT);
        String huobi_btc_last = JSONObject.parseObject(huobi_btc).getString("p_new");
        String huobi_ltc = HttpUtils.getContentForGet(HUOBI_LTC, TIME_OUT);
        String huobi_ltc_last = JSONObject.parseObject(huobi_ltc).getJSONObject("ticker").getString("last");
        String huobi_eth = HttpUtils.getContentForGet(HUOBI_ETH, TIME_OUT);
        String huobi_eth_last = JSONObject.parseObject(huobi_eth).getJSONObject("tick").getJSONArray("data")
                .getJSONObject(0).getString("price");
        PriceCache.setPrice("比特币中国比特币价格", btc_btc_last);
        PriceCache.setPrice("比特币中国莱特币价格", btc_ltc_last);
        PriceCache.setPrice("比特币中国以太币价格", btc_eth_last);
        PriceCache.setPrice("火币网比特币价格", huobi_btc_last);
        PriceCache.setPrice("火币网莱特币价格", huobi_ltc_last);
        PriceCache.setPrice("火币网以太币价格", huobi_eth_last);
    }
}
