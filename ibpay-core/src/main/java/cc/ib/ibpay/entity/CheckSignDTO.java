package cc.ib.ibpay.entity;



public class CheckSignDTO {
    private String timestamp;

    private String nonce;

    private String sign;

    private String merchantId;

    private String businessId;

    private String currency;

    private String address;

    private String amount;

    private String memo;

    private String showBalance;

    public CheckSignDTO(String timestamp, String nonce, String sign, String merchantId, String businessId, String currency, String address, String amount, String memo, String showBalance) {
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.sign = sign;
        this.merchantId = merchantId;
        this.businessId = businessId;
        this.currency = currency;
        this.address = address;
        this.amount = amount;
        this.memo = memo;
        this.showBalance = showBalance;
    }

    public CheckSignDTO() {
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getShowBalance() {
        return showBalance;
    }

    public void setShowBalance(String showBalance) {
        this.showBalance = showBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckSignDTO that = (CheckSignDTO) o;

        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (nonce != null ? !nonce.equals(that.nonce) : that.nonce != null) return false;
        if (sign != null ? !sign.equals(that.sign) : that.sign != null) return false;
        if (merchantId != null ? !merchantId.equals(that.merchantId) : that.merchantId != null) return false;
        if (businessId != null ? !businessId.equals(that.businessId) : that.businessId != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (memo != null ? !memo.equals(that.memo) : that.memo != null) return false;
        return showBalance != null ? showBalance.equals(that.showBalance) : that.showBalance == null;
    }

    @Override
    public int hashCode() {
        int result = timestamp != null ? timestamp.hashCode() : 0;
        result = 31 * result + (nonce != null ? nonce.hashCode() : 0);
        result = 31 * result + (sign != null ? sign.hashCode() : 0);
        result = 31 * result + (merchantId != null ? merchantId.hashCode() : 0);
        result = 31 * result + (businessId != null ? businessId.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (memo != null ? memo.hashCode() : 0);
        result = 31 * result + (showBalance != null ? showBalance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CheckSignDTO{" +
                "timestamp='" + timestamp + '\'' +
                ", nonce='" + nonce + '\'' +
                ", sign='" + sign + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", businessId='" + businessId + '\'' +
                ", currency='" + currency + '\'' +
                ", address='" + address + '\'' +
                ", amount='" + amount + '\'' +
                ", memo='" + memo + '\'' +
                ", showBalance='" + showBalance + '\'' +
                '}';
    }
}
