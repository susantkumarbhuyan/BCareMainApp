package com.bcareapp.config;

import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
	private final int POOL_SIZE = 10;

	private ScheduledTaskRegistrar scheduledTaskRegistrar;

	@Override
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

		threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
		threadPoolTaskScheduler.setThreadNamePrefix("POZScheduler-");
		threadPoolTaskScheduler.initialize();

		scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
		this.scheduledTaskRegistrar = scheduledTaskRegistrar;
	}

	@PreDestroy
	public void destroy() {
		this.scheduledTaskRegistrar.destroy();
	}
}
