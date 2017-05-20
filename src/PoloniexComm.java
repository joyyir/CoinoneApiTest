import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PoloniexComm {
    public static final String API_URL = "https://poloniex.com/";
    public static final String TICKER_URL = "public?command=returnTicker";
    public static final String TRADING_URL = "tradingApi";

    public static final String COIN_BTC = "BTC";
    public static final String COIN_ETH = "ETH";
    public static final String COIN_ETC = "ETC";
    public static final String COIN_XRP = "XRP";
    public static final String COIN_USDT = "USDT";
    public static final String COIN_STR = "STR";

    private Map<String, String> apikey;

    public PoloniexComm(Map<String, String> apikey) {
        this.apikey = apikey;
    }

    public double getMarketPrice(String unitCoin, String coin) throws Exception {
        JSONObject jsonObject = HTTPUtil.getJSONfromGet(API_URL + TICKER_URL);
        String strPrice = (String) ((JSONObject) jsonObject.get(unitCoin + "_" + coin)).get("last");
        return Double.valueOf(strPrice);
    }

    public double getBalance(String coin) throws Exception {
        String key = apikey.get("key");
        String secret = apikey.get("secret");
        int nonce =  Integer.valueOf(apikey.get("nonce")) + 1;
        apikey.put("nonce", String.valueOf(nonce));

        String params = "nonce=" + nonce + "&command=returnBalances";

        Map<String, String> map = new HashMap<>();
        map.put("Accept", "application/json");
        map.put("Key", key);
        map.put("Sign", Encryptor.getHmacSha512(secret, params));

        JSONObject json = HTTPUtil.getJSONfromPost(API_URL + TRADING_URL, map, params);

        return Double.valueOf((String) json.get(coin));
    }

    public double getCompleteBalance() throws Exception {
        String key = apikey.get("key");
        String secret = apikey.get("secret");
        int nonce =  Integer.valueOf(apikey.get("nonce")) + 1;
        apikey.put("nonce", String.valueOf(nonce));

        Double completeBal = 0.0;

        String params = "nonce=" + nonce + "&command=returnCompleteBalances";

        Map<String, String> map = new HashMap<>();
        map.put("Accept", "application/json");
        map.put("Key", key);
        map.put("Sign", Encryptor.getHmacSha512(secret, params));

        JSONObject json = HTTPUtil.getJSONfromPost(API_URL + TRADING_URL, map, params);

        for (int i = 0; i < json.names().length(); i++) {
            String strCoin = json.names().getString(i);
            double btcValue = Double.valueOf((String) ((JSONObject) json.get(strCoin)).get("btcValue"));
            if (btcValue > 0.0)
                completeBal += btcValue;
        }

        return completeBal;
    }
}