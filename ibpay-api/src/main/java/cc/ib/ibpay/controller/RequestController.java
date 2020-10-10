package cc.ib.ibpay.controller;


import cc.ib.ibpay.entity.Address;
import cc.ib.ibpay.http.ResponseMessage;
import cc.ib.ibpay.service.IbPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class RequestController {
    @Autowired
    private IbPayService ibPayService;

    /**
     * 创建新地址 create new address
     * Content-Type : application/x-www-form-urlencoded
     * @param userId "Peter"  可选 optional
     * @param currency "DOGE"
     * @return
     * {
     *     "address": "DDhKxBo4TKpZpJ2MMpEU5hsrHVp9HMrXRM",
     *     "currency": "DOGE"
     * }
     */
    @PostMapping("/ibpay/createAddress")
    public Address createCoinAddress(String userId,String currency){
        return ibPayService.createCoinAddress(userId, currency);
    }

    /**
     * 发起转账请求  transfer
     * Content-Type : application/x-www-form-urlencoded
     * @param userId "Peter" 可选 optional
     * @param amount   0.1
     * @param currency  DOGE
     * @param address   "D9kCWB3ZFksKzCtouLLt18YvKkbfn7HRwa"
     * @param memo
     * @return
     * {
     *     "code": 200,
     *     "message": "SUCCESS",
     *     "data": null
     * }
     */
    @PostMapping("/ibpay/transfer")
    public ResponseMessage<String> transfer(String userId , BigDecimal amount, String currency,String address,String memo){
        ResponseMessage<String> resp = ibPayService.transfer(userId, amount, currency,address, memo);
        return resp;
    }

    /**
     * 校验地址  check address
     * Content-Type : application/x-www-form-urlencoded
     * @param currency "XRP"
     * @param address  "r3ReqczYCCc44pUxU9bmWauCSzJJoiy2oD"
     * @param memo  "235235"
     * @return
     * {
     *     "code": 200,
     *     "message": "SUCCESS",
     *     "data": null
     * }
     */
    @PostMapping("/ibpay/checkAddress")
    public ResponseMessage<String> checkAddress(String currency,String address,String memo){
        return ibPayService.checkAddress(currency,address,memo);
    }

    /**
     * 获取币种列表
     * @param showBalance
     * @return
     */
    @PostMapping("/ibpay/coinList")
    public ResponseMessage<String> coinList(Boolean showBalance){
        return ibPayService.coinList(showBalance);
    }

}
