package net.afyer.afybroker.bukkit.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import lombok.Setter;
import net.afyer.afybroker.client.BrokerClient;
import net.afyer.afybroker.client.aware.BrokerClientAware;
import net.afyer.afybroker.core.message.SendPlayerChatMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author Nipuru
 * @since 2022/8/5 10:08
 */
public class SendPlayerChatBukkitProcessor extends AsyncUserProcessor<SendPlayerChatMessage> implements BrokerClientAware {

    @Setter
    BrokerClient brokerClient;

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, SendPlayerChatMessage request) {
        Player target = Bukkit.getPlayer(request.getUid());

        if (target == null) {
            return;
        }

        target.sendMessage(request.getMessage());
    }

    @Override
    public String interest() {
        return SendPlayerChatMessage.class.getName();
    }
}