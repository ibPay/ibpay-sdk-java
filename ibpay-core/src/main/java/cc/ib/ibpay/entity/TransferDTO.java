package cc.ib.ibpay.entity;

import lombok.Data;

@Data
public class TransferDTO {

    private String businessId;

    private String currency;

    private String address;

    private String amount;

    private String memo;
}
