package cc.ib.ibpay.utils;

import cc.ib.ibpay.entity.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
public class HttpUtil {


    public static Map<String ,String> wrapperParams(String key, String merchantId ,CreateAddressDTO createAddressDTO) throws Exception {
        String timestamp = System.currentTimeMillis()/1000+"" ;
        String nonce = String.valueOf(getNonceString(16));
        CheckSignDTO checkSignDTO = new CheckSignDTO();
        checkSignDTO.setTimestamp(timestamp);
        checkSignDTO.setNonce(nonce);
        checkSignDTO.setMerchantId(merchantId);
        checkSignDTO.setBusinessId(createAddressDTO.getBusinessId());
        checkSignDTO.setCurrency(createAddressDTO.getCurrency());
        String sign = SignUtil.sign(checkSignDTO,key);
        Map<String ,String> map = new HashMap<>();

        map.put("merchantId",merchantId);
        map.put("businessId",createAddressDTO.getBusinessId());
        map.put("currency",createAddressDTO.getCurrency());
        map.put("sign",sign);
        map.put("timestamp",timestamp);
        map.put("nonce",nonce);
        return map;
    }
        public static Map<String ,String> wrapperParams(String key, String merchantId , TransferDTO transferDTO) throws Exception {
        String timestamp = System.currentTimeMillis()/1000+"" ;
        String nonce = String.valueOf(getNonceString(16));
        CheckSignDTO checkSignDTO = new CheckSignDTO();
        checkSignDTO.setTimestamp(timestamp);
        checkSignDTO.setNonce(nonce);
        checkSignDTO.setMerchantId(merchantId);
        checkSignDTO.setBusinessId(transferDTO.getBusinessId());
        checkSignDTO.setCurrency(transferDTO.getCurrency());
        checkSignDTO.setAddress(transferDTO.getAddress());
        checkSignDTO.setAmount(transferDTO.getAmount());
        checkSignDTO.setMemo(transferDTO.getMemo());
        String sign = SignUtil.sign(checkSignDTO,key);
        Map<String ,String> map = new HashMap<>();

        map.put("merchantId",merchantId);
        map.put("businessId",transferDTO.getBusinessId());
        map.put("address",transferDTO.getAddress());
        map.put("amount",transferDTO.getAmount());
        map.put("currency",transferDTO.getCurrency());
        map.put("memo",transferDTO.getMemo());
        map.put("sign",sign);
        map.put("timestamp",timestamp);
        map.put("nonce",nonce);
        return map;
    }


    public static Map<String ,String> wrapperParams(String key, String merchantId , CheckAddressDTO checkAddressDTO) throws Exception {
        String timestamp = System.currentTimeMillis()/1000+"" ;
        String nonce = String.valueOf(getNonceString(16));
        CheckSignDTO checkSignDTO = new CheckSignDTO();
        checkSignDTO.setTimestamp(timestamp);
        checkSignDTO.setNonce(nonce);
        checkSignDTO.setMerchantId(merchantId);
        checkSignDTO.setCurrency(checkAddressDTO.getCurrency());
        checkSignDTO.setAddress(checkAddressDTO.getAddress());
        String sign = SignUtil.sign(checkSignDTO,key);
        Map<String ,String> map = new HashMap<>();

        map.put("merchantId",merchantId);
        map.put("address",checkAddressDTO.getAddress());
        map.put("currency",checkAddressDTO.getCurrency());
        map.put("sign",sign);
        map.put("timestamp",timestamp);
        map.put("nonce",nonce);
        return map;
    }


    public static Map<String ,String> wrapperParams(String key, String merchantId , CoinListDTO coinListDTO) throws Exception {
        String timestamp = System.currentTimeMillis()/1000+"" ;
        String nonce = String.valueOf(getNonceString(16));
        CheckSignDTO checkSignDTO = new CheckSignDTO();
        checkSignDTO.setTimestamp(timestamp);
        checkSignDTO.setNonce(nonce);
        checkSignDTO.setMerchantId(merchantId);
        checkSignDTO.setShowBalance(coinListDTO.getShowBalance());
        String sign = SignUtil.sign(checkSignDTO,key);
        Map<String ,String> map = new HashMap<>();

        map.put("merchantId",merchantId);
        map.put("showBalance",coinListDTO.getShowBalance());
        map.put("sign",sign);
        map.put("timestamp",timestamp);
        map.put("nonce",nonce);
        return map;
    }


    public static String getNonceString(int length){
        String seed = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append(seed.charAt(getRandomNumber(0,61)));
        }
        return stringBuffer.toString();
    }

    public static int getRandomNumber(int from, int to) {
        float a = from + (to - from) * (new Random().nextFloat());
        int b = (int) a;
        return ((a - b) > 0.5 ? 1 : 0) + b;
    }

    public static boolean checkSign(CheckSignDTO checkSignDTO,String apiKey) throws Exception {
        String signString = SignUtil.sign(checkSignDTO,apiKey);
        return signString.equals(checkSignDTO.getSign());
    }
}
