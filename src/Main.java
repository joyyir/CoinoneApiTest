import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // Set your API Key
        Map<String, String> apikey = new HashMap<>();
        apikey.put("access_token", "01234567-89ab-cdef-0123-456789abcdef");
        apikey.put("secret", "01234567-89ab-cdef-0123-456789abcdef");
        apikey.put("nonce", String.valueOf(0)); // if you get Exception, you should increase this value.

        CoinoneComm comm = new CoinoneComm(apikey);

        try {
            double ethBal = comm.getBalance(CoinoneComm.COIN_ETH);
            long totBal = comm.getCompleteBalance();
            int etcPrice = comm.getMarketPrice(CoinoneComm.COIN_ETC);

            System.out.println(""
                    + "I have " + ethBal + " ETH.\n"
                    + "I have " + totBal + " won value in Coinone.\n"
                    + "1 ETC price is " + etcPrice + " won.\n"
            );
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
