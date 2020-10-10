package cc.ib.ibpay.controller;


import cc.ib.ibpay.http.client.NotifyReqVO;
import cc.ib.ibpay.service.IbPayService;

import cc.ib.ibpay.http.client.IbPayClient;

import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class CallbackController {
    @Autowired
    IbPayClient ibPayClient;
    @Autowired
    IbPayService ibPayService;

    private Logger logger = LoggerFactory.getLogger(CallbackController.class);

    /**
     * 处理回调，包括充币,提币
     * Handle callbacks, including deposit and withdrawal
     * @param notifyReqVO
     * @return
     * @throws Exception
     */
    @PostMapping("/ibpay/notify")
    public String tradeCallback(@RequestBody NotifyReqVO notifyReqVO) throws Exception {

        logger.info(notifyReqVO.toString());

        boolean checkSignResult = ibPayService.checkSign(notifyReqVO);
        if (!checkSignResult){
          throw new HttpException("验签不通过");
        }

        //TODO 业务处理
        if(notifyReqVO.getTradeType().equals("DEPOSIT")){
            logger.info("=====收到充币通知======");
            logger.info("地址address:{} 收到充值 ,amount:{},币种名currency:{},txid:{}, 确认块高confirmHeight:{}",notifyReqVO.getToAddress(), notifyReqVO.getAmount(),notifyReqVO.getCurrency(),notifyReqVO.getTxid(),notifyReqVO.getConfirmHeight());

        }
        else if(notifyReqVO.getTradeType().equals("WITHDRAW")){
            logger.info("=====收到提币处理通知=====");
            logger.info("提币唯一ID(提币时填入的唯一ID) :{} , 提币地址address:{} , amount:{}, 币种名currency:{}, txid:{}, 确认块高confirmHeight:{}",notifyReqVO.getBusinessId(), notifyReqVO.getToAddress(), notifyReqVO.getAmount(),notifyReqVO.getCurrency(),notifyReqVO.getTxid(),notifyReqVO.getConfirmHeight());


           /* if(trade.getStatus() == 1){
                logger.info("审核通过，转账中");
                //TODO: 提币交易已发出，理提币订单状态，扣除提币资金
            }
            else if(trade.getStatus() == 2){
                logger.info("审核不通过");
                //TODO: 处理提币订单状态，订单号为 businessId
            }
            else if(trade.getStatus() == 3){
                logger.info("提币已到账");
                //TODO: 提币已到账，可以向提币用户发出通知
            }*/
        }
        return "success";
    }
}
