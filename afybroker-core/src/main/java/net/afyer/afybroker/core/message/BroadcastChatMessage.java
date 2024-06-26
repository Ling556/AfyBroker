package net.afyer.afybroker.core.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * @author Nipuru
 * @since 2022/8/10 11:23
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BroadcastChatMessage implements Serializable {
    private static final long serialVersionUID = -4901406795508836396L;

    /**
     * 消息
     */
    String message;

}
