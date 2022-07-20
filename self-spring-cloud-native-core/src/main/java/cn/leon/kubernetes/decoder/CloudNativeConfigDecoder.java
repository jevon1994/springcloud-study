package cn.leon.kubernetes.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @author mujian
 * @Classname CloudNativeConfigDecoder
 * @Description
 * @Date 2022/1/26
 */
public class CloudNativeConfigDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        if (response.status() == HttpStatus.OK.value()) {
            String result = IOUtils.toString(response.body().asReader(StandardCharsets.UTF_8));
            ObjectMapper objectMapper = new ObjectMapper();
            Environment environment = objectMapper.readValue(result, Environment.class);
            return environment;
        }
        return null;
    }
}