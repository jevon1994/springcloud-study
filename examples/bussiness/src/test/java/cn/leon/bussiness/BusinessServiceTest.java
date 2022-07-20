package cn.leon.bussiness;

import cn.leon.bussiness.service.BusinessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@SpringBootTest(classes = BusinessApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BusinessServiceTest {

    @Autowired
    private BusinessService businessService;

    @Test
    public void purchase() {
        CompletableFuture.runAsync(() -> businessService.purchase("1001", "2001", 1),
                Executors.newFixedThreadPool(5));

    }

    @Test
    public void testNextSpanId(){
//        Trace build = Trace.builder()
//                .traceId(UUID.randomUUID().toString())
//                .parentSpanId("1.1")
//                .spanId("1.1")
//                .appName("test")
//                .build();
        for (int i = 0; i < 100; i++) {
//            CompletableFuture.runAsync(() -> build.nextSpanId("1.1"));
        }
    }
}