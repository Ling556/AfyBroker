package net.afyer.afybroker.core.util;

import com.alipay.remoting.exception.CodecException;
import com.alipay.remoting.serialization.Serializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Pool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Kryo 序列化器实现
 *
 * @author Nipuru
 * @since 2025/01/12 10:36
 */
public class KryoSerializer implements Serializer {

    private final Pool<Kryo> kryoPool;
    private final ThreadLocal<ByteArrayOutputStream> localOutputByteArray = ThreadLocal.withInitial(ByteArrayOutputStream::new);

    public KryoSerializer(ClassLoader classLoader) {
        // 创建 Kryo 池，提高并发性能
        // 线程安全(true), 软引用(false), 最大容量(8)
        this.kryoPool = new Pool<Kryo>(true, false, 8) {
            @Override
            protected Kryo create() {
                Kryo kryo = new Kryo();
                kryo.setRegistrationRequired(false); // 允许未注册的类
                kryo.setReferences(true); // 启用引用跟踪，避免循环引用问题
                kryo.setAutoReset(true); // 自动重置，提高性能
                kryo.setClassLoader(classLoader);
                return kryo;
            }
        };
    }

    @Override
    public byte[] serialize(Object obj) throws CodecException {
        if (obj == null) {
            return new byte[0];
        }


        ByteArrayOutputStream byteArray = localOutputByteArray.get();
        byteArray.reset();
        Output output = new Output(byteArray);
        Kryo kryo = kryoPool.obtain();
        try {
            kryo.writeClassAndObject(output, obj);
            output.close();
        } catch (Exception e) {
            throw new CodecException("IOException occurred when Kryo serializer encode!", e);
        } finally {
            kryoPool.free(kryo);
        }
        return byteArray.toByteArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] data, String classOfT) throws CodecException {
        if (data == null || data.length == 0) {
            return null;
        }
        
        Kryo kryo = kryoPool.obtain();
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             Input input = new Input(bais)) {
            
            Object result = kryo.readClassAndObject(input);
            return (T) result;
            
        } catch (Exception e) {
            throw new CodecException("IOException occurred when Kryo serializer decode!", e);
        } finally {
            kryoPool.free(kryo);
        }
    }
}
