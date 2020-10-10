package cc.ib.ibpay.constant;

public enum CoinType {
    BTC("BTC", "BTC"),
    ETH("ETH", "ETH"),
    USDT("USDT", "USDT_OMNI"),
    XRP("XRP", "XRP"),
    BCH("BCH", "BCH"),
    LTC("LTC", "LTC"),
    DOGE("DOGE", "DOGE"),
    USDC("USDC", "USDC_ERC20"),
    TRX("TRX", "TRX"),
    USDT_ERC20("USDT_ERC20", "USDT_ERC20"),;


    private String code;
    private String unit;

    CoinType(String code, String unit){
        this.code=code;
        this.unit=unit;
    }

    public String getCode(){
        return this.code;
    }

    public String getUnit(){
        return this.unit;
    }

    public static CoinType codeOf(String code){
        switch (code){
            case "BTC": return BTC;
            case "ETH": return ETH;
            case "USDT": return USDT;
            case "XRP": return XRP;
            case "BCH": return BCH;
            case "LTC": return LTC;
            case "DOGE":return DOGE;
            case "USDC": return USDC;
            case "TRX": return TRX;
            case "USDT_ERC20": return USDT_ERC20;
        }
        return null;
    }
}
