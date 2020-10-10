package cc.ib.ibpay.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class Address {
    private String address;
    private String currency;

    public static Address parse(String json){
        return JSON.parseObject(json,Address.class);
    }
}
