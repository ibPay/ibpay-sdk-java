package cc.ib.ibpay.http.client;

import cc.ib.ibpay.constant.API;
import cc.ib.ibpay.constant.CoinType;
import cc.ib.ibpay.entity.*;
import cc.ib.ibpay.http.ResponseMessage;
import cc.ib.ibpay.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
public class IbPayClient implements Client<String> {
    private String gateway;
    private static String CONNECTION_EXCEPTION = "connect exception" ;
    private static String ENCODE_TYPE = "UTF-8";
    private static String CONTENT_TYPE_VALUE = "application/json";
    private static String CONTENT_TYPE_NAME = "Content-Type";
    private int connectTimeout = 1000;
    private int requestTimeout =1000;
    private Boolean redirectEnabled = true;

    private String merchantId ;
    private String merchantKey;

    public static final CookieStore cookieStore = new BasicCookieStore();

    public RequestConfig requestConfig ;

    public IbPayClient(){
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(requestTimeout)
                .setRedirectsEnabled(redirectEnabled).build();
        this.requestConfig = requestConfig ;
    }

    public IbPayClient(String gateway, String merchantId, String key){
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(requestTimeout)
                .setRedirectsEnabled(redirectEnabled).build();
        this.gateway = gateway;
        this.merchantId = merchantId;
        this.merchantKey = key ;
        this.requestConfig = requestConfig ;
    }

    public IbPayClient(String host, String merchantId, String key, RequestConfig requestConfig){
        this.gateway = host;
        this.merchantId = merchantId;
        this.merchantKey = key ;
        this.requestConfig = requestConfig ;
    }

    public ResponseMessage<Address> createCoinAddress(String businessId, String currency) throws Exception {

        CreateAddressDTO createAddressDTO = new CreateAddressDTO();
        createAddressDTO.setBusinessId(businessId);
        createAddressDTO.setCurrency(currency);
        Map<String,String> map = HttpUtil.wrapperParams(this.merchantKey,this.merchantId ,createAddressDTO);
        ResponseMessage<String> response = post(API.CREATE_ADDRESS,map);
        ResponseMessage<Address> result = new ResponseMessage<>(response.getCode(),response.getMessage());
        if(result.getCode() == ResponseMessage.SUCCESS_CODE){
            result.setData(Address.parse(response.getData()));
        }
        return result;
    }

    public ResponseMessage<String>  transfer( String businessId , BigDecimal amount, String currency,String address,String memo) throws Exception {
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setAddress(address);
        transferDTO.setAmount(amount.stripTrailingZeros().toPlainString());
        transferDTO.setCurrency(currency);
        transferDTO.setBusinessId(businessId);
        transferDTO.setMemo(memo);
        Map<String,String> map = HttpUtil.wrapperParams(this.merchantKey,this.merchantId ,transferDTO);
        System.out.println(map);
        ResponseMessage<String> response = post(API.WITHDRAW,map);
        return response;
    }


    public ResponseMessage<String>  checkAddress(String currency,String address, String memo) throws Exception {
        CheckAddressDTO checkAddressDTO = new CheckAddressDTO();
        String newAddress=address;
        if (currency.equals(CoinType.XRP.getCode())){
            if (!memo.equals("")){
                newAddress=newAddress+"|"+memo;
            }
        }
        checkAddressDTO.setAddress(newAddress);
        checkAddressDTO.setCurrency(currency);
        Map<String,String> map = HttpUtil.wrapperParams(this.merchantKey,this.merchantId ,checkAddressDTO);
        System.out.println(map);
        ResponseMessage<String> response = post(API.CHECK_ADDRESS,map);
        return response;
    }

    public ResponseMessage<String>  coinList(boolean showBalance) throws Exception {
        CoinListDTO coinListDTO = new CoinListDTO();
        coinListDTO.setShowBalance(String.valueOf(showBalance) );
        Map<String,String> map = HttpUtil.wrapperParams(this.merchantKey,this.merchantId ,coinListDTO);
        System.out.println(map);
        ResponseMessage<String> response = post(API.COINLIST,map);
        return response;
    }



    public ResponseMessage<String> post(String url, Map<String,String> map) {
        CloseableHttpClient httpCilent= HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpPost httpPost = new HttpPost(gateway + url);
        httpPost.setConfig(requestConfig);
        HttpResponse httpResponse= null ;
        try {
            StringEntity entity = new StringEntity(JSONObject.toJSONString(map), Charset.forName(ENCODE_TYPE));
            entity.setContentType(CONTENT_TYPE_VALUE);
            entity.setContentEncoding(ENCODE_TYPE);
            httpPost.addHeader(CONTENT_TYPE_NAME,CONTENT_TYPE_VALUE);
            httpPost.setEntity(entity);
            httpResponse = httpCilent.execute(httpPost);
            String strResult ;
            ResponseMessage<String> response ;
            if (httpResponse != null) {
                log.info("httpResponse:{}",httpResponse.getStatusLine().toString());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                    System.out.println(strResult);
                    log.debug("strResult:,{}",strResult);
                    JSONObject json = JSONObject.parseObject(strResult);
                    response = ResponseMessage.success(json.getInteger("code"),json.getString("message"));
                    if(json.getString("data")!=null){
                        response.setData(json.getString("data"));
                    }
                }  else {
                    strResult = "Error Response: " + httpResponse.getStatusLine().toString();
                    JSONObject json = JSONObject.parseObject(strResult);
                    response = ResponseMessage.error(json.getInteger("code"),json.getString("message"));
                }
                log.info("Response:,{}",response);
                return response;
            }
        }catch (IOException e){
            log.error("IOException:{}",e);
        }finally {
            if(httpCilent!=null){
                try {
                    httpCilent.close();
                } catch (IOException e) {
                    log.error("http client close exception:{}",e);
                }
            }
        }
        log.error(CONNECTION_EXCEPTION);
        return ResponseMessage.error(CONNECTION_EXCEPTION);
    }


    public ResponseMessage<String> get(String url) {
        CloseableHttpClient httpCilent= HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        String strResult ;
        ResponseMessage<String> response;
        try {
            HttpResponse httpResponse = httpCilent.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果
                response  = ResponseMessage.success();
                response.setData(strResult);
            } else {
                strResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果
                response  = ResponseMessage.error(strResult);
            }
            log.info("Response:,{}",response);
            return response;
        } catch (IOException e) {
            log.error("Request exception:{}",e);
        }finally {
            if(httpCilent!=null){
                try {
                    httpCilent.close();
                } catch (IOException e) {
                    log.error("http client close exception:{}",e);
                }
            }
        }
        log.error(CONNECTION_EXCEPTION);
        return ResponseMessage.error(CONNECTION_EXCEPTION);
    }

    public static void setCookieStore(List<BasicClientCookie> cookielist ) {
        for(BasicClientCookie cookie:cookielist){
            IbPayClient.cookieStore.addCookie(cookie);
        }
    }

    public static void createCookie(List<BasicClientCookie> cookielist ) {
        for(BasicClientCookie cookie:cookielist){
            IbPayClient.cookieStore.addCookie(cookie);
        }
    }

}
