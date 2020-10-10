package cc.ib.ibpay.http.client;

import cc.ib.ibpay.http.ResponseMessage;

import java.util.Map;

public interface Client<T> {

    ResponseMessage<T> post(String url, Map<String, String> list);

    ResponseMessage<T> get(String url);

}
