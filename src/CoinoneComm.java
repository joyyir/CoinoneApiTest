import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CoinoneComm {
    public static final String API_URL = "https://api.coinone.co.kr/";
    public static final String TICKER_URL = "ticker?currency=";

    public static final String COIN_BTC = "btc";
    public static final String COIN_ETH = "eth";
    public static final String COIN_ETC = "etc";
    public static final String COIN_KRW = "krw";

    private Map<String, String> apikey;

    public CoinoneComm(Map<String, String> apikey) {
        this.apikey = apikey;
    }

    public int getMarketPrice(String coin) throws Exception {
        JSONObject jsonObject = HTTPUtil.getJSONfromGet(API_URL+TICKER_URL+coin);
        return Integer.valueOf( (String)jsonObject.get("last") );
    }

    public double getBalance(String coin) throws Exception {
        String accessToken = apikey.get("access_token");
        String secret = apikey.get("secret");
        int nonce =  Integer.valueOf(apikey.get("nonce")) + 1;
        apikey.put("nonce", String.valueOf(nonce));

        String url = API_URL + "v2/account/balance/";

        JSONObject params = new JSONObject();
        params.put("nonce", nonce);
        params.put("access_token", accessToken);

        String payload = Base64.encodeBase64String(params.toString().getBytes());
        String signature = Encryptor.getHmacSha512(secret.toUpperCase(), payload).toLowerCase();

        Map<String, String> map = new HashMap<>();
        map.put("content-type", "application/json");
        map.put("accept", "application/json");
        map.put("X-COINONE-PAYLOAD", payload);
        map.put("X-COINONE-SIGNATURE", signature);

        JSONObject result = HTTPUtil.getJSONfromPost(url, map, payload);
        String strBalance = (String)((JSONObject) result.get(coin)).get("avail");

        return Double.valueOf(strBalance);
    }

    public long getCompleteBalance() throws Exception {
        return (long) (getMarketPrice(COIN_BTC) * getBalance(COIN_BTC))
                + (long) (getMarketPrice(COIN_ETH) * getBalance(COIN_ETH))
                + (long) (getMarketPrice(COIN_ETC) * getBalance(COIN_ETC))
                + (long) getBalance(COIN_KRW);
    }
}
