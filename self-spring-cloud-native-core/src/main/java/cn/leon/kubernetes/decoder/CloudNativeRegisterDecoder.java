package cn.leon.kubernetes.decoder;

import cn.leon.kubernetes.ex.CloudNativeRegisterException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author mujian
 * @Classname CloudNativeRegisterDecoder
 * @Description
 * @Date 2022/2/11
 */
public class CloudNativeRegisterDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws IOException {
        return Util.toString(response.body().asReader(Util.UTF_8));
    }
}
