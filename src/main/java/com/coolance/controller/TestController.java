package com.coolance.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @ClassName TestController
 * @Description webflux开发-1
 * @Author Coolance
 * @Version
 * @Date 2019/10/12 9:49
 */
@RestController
@Slf4j
public class TestController {

    @GetMapping("/mvc")
    public String getMvc() {
        log.info("mvc method start");
        String result = createStr();
        log.info("mvc method end");
        return result;
    }

    @GetMapping("/mono")
    public Mono<String> getMono() {
        log.info("mono method start");
        Mono<String> result = Mono.fromSupplier(TestController::createStr);
        log.info("mono method end");
        return result;
    }

    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getFlux() {
        Flux<String> result = Flux.fromStream(IntStream.range(1, 5).mapToObj(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "flux date ---" + i;
        }));
        return result;
    }

    private static String createStr() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "something";
    }
}
