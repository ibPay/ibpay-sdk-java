package cc.ib.ibpay.utils;


import cc.ib.ibpay.entity.CheckSignDTO;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 签名工具类
 */
public class SignUtil {

    public static String sign(CheckSignDTO checkSignDTO,String apiKey) throws Exception {
        String text = (checkSignDTO.getAddress()==null?"":checkSignDTO.getAddress()) + (checkSignDTO.getAmount()==null?"":checkSignDTO.getAmount() )+ (checkSignDTO.getBusinessId()==null?"":checkSignDTO.getBusinessId() )+ (checkSignDTO.getCurrency()==null?"":checkSignDTO.getCurrency()) + (checkSignDTO.getMerchantId()==null?"":checkSignDTO.getMerchantId()) +(checkSignDTO.getMemo()==null?"":checkSignDTO.getMemo()) +(checkSignDTO.getNonce()==null?"":checkSignDTO.getNonce()) +(checkSignDTO.getShowBalance()==null?"":checkSignDTO.getShowBalance()) +(checkSignDTO.getTimestamp()==null?"":checkSignDTO.getTimestamp()) + apiKey;
        return DigestUtils.md5Hex(text).toLowerCase();
    }


}
