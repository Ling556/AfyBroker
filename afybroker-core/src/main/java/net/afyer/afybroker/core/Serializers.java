package net.afyer.afybroker.core;


import com.alipay.remoting.serialization.SerializerManager;

/**
 * 序列化器
 *
 * @author Nipuru
 * @since 2025/09/26 10:33
 */
public interface Serializers {
    /** Hessian2 */
    int Hessian2 = SerializerManager.Hessian2;

    /** Kryo */
    int Kryo = 1;
}
