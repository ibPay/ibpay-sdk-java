package cc.ib.ibpay.entity;

import lombok.Data;

@Data
public class CheckAddressDTO {
    private String currency;
    private String address;
}
