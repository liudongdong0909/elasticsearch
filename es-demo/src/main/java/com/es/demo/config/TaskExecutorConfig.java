package com.es.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

@Component
@Setter
@Getter
public class TaskExecutorConfig {

	private Integer corePoolSize = 20;

	private Integer maxPoolSize = 60;

	private Integer queueCapacity = 6000;

	private Integer keepAliveSeconds = 60;

    @Bean(name = "loadDataTaskExecutor")
	public ThreadPoolTaskExecutor loadDataTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(corePoolSize);
		taskExecutor.setMaxPoolSize(maxPoolSize);
		taskExecutor.setQueueCapacity(queueCapacity);
		taskExecutor.setThreadNamePrefix("LoadData-");
		taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return taskExecutor;
	}

    @Bean(name = "importDataTaskExecutor")
	public ThreadPoolTaskExecutor importDataTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(corePoolSize);
		taskExecutor.setMaxPoolSize(maxPoolSize);
		taskExecutor.setQueueCapacity(queueCapacity);
		taskExecutor.setThreadNamePrefix("ImportData-");
		taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return taskExecutor;
	}
}