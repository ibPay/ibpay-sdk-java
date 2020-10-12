package cc.ib.ibpay.http.client;

import lombok.Data;

@Data
public class NotifyReqVO {
    private String timestamp;
    private String nonce;
    private String sign;
    private String amount;
    private String blockHigh;
    private String businessId;
    private String confirmHeight;
    private String createTime;
    private String currency;
    private String msg;
    private String fromAddress;
    private String maxConfirmHeight;
    private String status;
    private String toAddress;
    private String tradeId;
    private String tradeType;
    private String txid;
    private String txTime;
}
