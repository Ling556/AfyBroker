package net.afyer.afybroker.client.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.afyer.afybroker.client.BrokerClient;
import net.afyer.afybroker.client.aware.BrokerClientAware;
import net.afyer.afybroker.core.message.BrokerClientInfoMessage;
import net.afyer.afybroker.core.message.RequestBrokerClientInfoMessage;

/**
 * @author Nipuru
 * @since 2022/7/30 17:22
 */
@Slf4j
public class RequestBrokerClientInfoClientProcessor extends AsyncUserProcessor<RequestBrokerClientInfoMessage> implements BrokerClientAware {

    @Setter
    BrokerClient brokerClient;

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, RequestBrokerClientInfoMessage request) {

        log.info("Received server request, sending client info");

        BrokerClientInfoMessage response = brokerClient.getClientInfo().toMessage();

        try {
            brokerClient.getRpcClient().oneway(bizCtx.getConnection(), response);
        } catch (RemotingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String interest() {
        return RequestBrokerClientInfoMessage.class.getName();
    }

}
