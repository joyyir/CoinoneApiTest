# CoinoneApiTest
Get data using Coinone API, written in JAVA, IntelliJ Project.

## How to use?
1. Create API Key in Coinone. https://coinone.co.kr/developer/app/
2. Set your API Key in Main.java
3. Run Main.java

## Usage example

```
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
```