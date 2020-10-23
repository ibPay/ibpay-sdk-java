package cc.ib.ibpay.service;

import cc.ib.ibpay.entity.Address;
import cc.ib.ibpay.http.ResponseMessage;
import cc.ib.ibpay.http.client.IbPayClient;
import cc.ib.ibpay.http.client.NotifyReqVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

import static cc.ib.ibpay.utils.HttpUtil.getNonceString;

@Service
public class IbPayService {
    @Autowired
    private IbPayClient ibPayClient;
/*    @Value("${server.host}")
    private String host;*/
    @Value("#{'${ibpay.supported-coins}'.split(',')}")
    private List<String> supportedCoins;

    public boolean isSupportedCoin(String coinName){
        return  supportedCoins!=null && supportedCoins.contains(coinName);
    }

    /**
     * 创建币种地址
     * @param userId
     * @param currency
     * @return
     */
    public Address createCoinAddress(String userId, String currency){

        try {
            String businessId= "";
            if (userId!=null&&!userId.equals("")){
                businessId=userId;
            }
            ResponseMessage<Address> resp =  ibPayClient.createCoinAddress(businessId, currency);
            return  resp.getData();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public ResponseMessage<String> transfer( String userId , BigDecimal amount, String currency,String address,String memo){
        try {
            String businessId= "";
            //此uniqueID 需要持久化, 提币回调时需要用到此uniqueID(唯一ID)
            String uniqueID = getNonceString(32);
            if (userId==null||userId.isEmpty()){
                businessId=uniqueID;
            }else {
                businessId=userId+"&"+uniqueID;
            }
            ResponseMessage<String> resp =  ibPayClient.transfer(  businessId , amount, currency,address, memo);
            return resp;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseMessage.error("提交转币失败");
    }

    public ResponseMessage<String> checkAddress( String currency,String address,String memo){
        try {

            ResponseMessage<String> resp =  ibPayClient.checkAddress(  currency,address,memo);
            return resp;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseMessage.error("checkAddress失败");
    }
    public ResponseMessage<String> coinList( Boolean showBalance){
        try {
            if (showBalance==null){
                showBalance=false;
            }
            ResponseMessage<String> resp =  ibPayClient.coinList( showBalance);
            return resp;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseMessage.error("coinList失败");
    }

    public boolean checkSign(NotifyReqVO notifyReqVO){

        String userApiKey=ibPayClient.getMerchantKey();

        //判断时间戳不能偏差太大, 默认180秒
        if (Math.abs(Long.parseLong(notifyReqVO.getTimestamp()) - System.currentTimeMillis() / 1000) > 180) {
            System.out.println("时间相差太大,验签不通过");
            return false;
        }

        //计算的sign为
        String text = (notifyReqVO.getAmount()==null?"":notifyReqVO.getAmount()) + (notifyReqVO.getBlockHigh()==null?"":notifyReqVO.getBlockHigh() )+ (notifyReqVO.getBusinessId()==null?"":notifyReqVO.getBusinessId() )+ (notifyReqVO.getConfirmHeight()==null?"":notifyReqVO.getConfirmHeight()) + (notifyReqVO.getCreateTime()==null?"":notifyReqVO.getCreateTime()) +(notifyReqVO.getCurrency()==null?"":notifyReqVO.getCurrency()) + (notifyReqVO.getFromAddress()==null?"":notifyReqVO.getFromAddress()) + (notifyReqVO.getMaxConfirmHeight()==null?"":notifyReqVO.getMaxConfirmHeight()) + (notifyReqVO.getMsg()==null?"":notifyReqVO.getMsg())  +(notifyReqVO.getNonce()==null?"":notifyReqVO.getNonce()) +(notifyReqVO.getStatus()==null?"":notifyReqVO.getStatus())+(notifyReqVO.getTimestamp()==null?"":notifyReqVO.getTimestamp()) +(notifyReqVO.getToAddress()==null?"":notifyReqVO.getToAddress()) +(notifyReqVO.getTradeId()==null?"":notifyReqVO.getTradeId()) +(notifyReqVO.getTradeType()==null?"":notifyReqVO.getTradeType()) +(notifyReqVO.getTxid()==null?"":notifyReqVO.getTxid()) +(notifyReqVO.getTxTime()==null?"":notifyReqVO.getTxTime()) + userApiKey;
        System.out.println(">>>计算出的text:{}<<<"+ text);
        String sign = DigestUtils.md5Hex(text).toLowerCase();


        System.out.println(">>>计算出的签名:{}<<<"+sign);
        if (!sign.equalsIgnoreCase(notifyReqVO.getSign())) {
            System.out.println("签名不同,验签不通过");
           return false;
        }
        return true;
    }


}
