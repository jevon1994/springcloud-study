package cn.leon.kubernetes.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class CloudNativeConfigDecoder implements Decoder {
    @Autowired

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        if (response.status() == 200) {
            String result = IOUtils.toString(response.body().asReader(StandardCharsets.UTF_8));
            ObjectMapper objectMapper = new ObjectMapper();
            Environment environment = objectMapper.readValue(result, Environment.class);
            return environment;
        }
        return null;
    }
}
