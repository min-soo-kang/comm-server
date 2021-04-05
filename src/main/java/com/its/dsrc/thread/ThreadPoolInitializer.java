package com.its.dsrc.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ThreadPoolInitializer extends AsyncConfigurerSupport {

    @Bean(name="centerCommExecutor")
    public Executor getCenterCommExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);          // 인스턴스 되면서 기본적으로 띄울 스레드 개수.
                                                            // 아무작업이 없어도 corePoolSize 만큼 스레드가 생성
        threadPoolTaskExecutor.setMaxPoolSize(10);          // 풀 최대개수, Queue Capacity 까지 꽉차는 경우 maxPoolSize 만큼 넓혀감
        threadPoolTaskExecutor.setQueueCapacity(100);       // 스레드 대기큐, Queue Capacity 가 꽉차면 스레드가 추가로 생성됨. Async 처리시 Queue Size
                                                            // (설정하지 않으면 Integer.MAX 이기 때문에 성능에 문제가 발생함)
        threadPoolTaskExecutor.setThreadNamePrefix("centerComm-");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
