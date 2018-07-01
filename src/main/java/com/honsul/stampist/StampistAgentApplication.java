package com.honsul.stampist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.honsul.stampist.agent.config.StampistAgentConfig;

@EnableScheduling
@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class})
public class StampistAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(StampistAgentApplication.class, args);
	}
	
	@Bean
	public WebSocketStompClient stompClient(StampistAgentConfig config, TaskScheduler taskScheduler) {
    List<Transport> transports = new ArrayList<>(1);
    transports.add(new WebSocketTransport( new StandardWebSocketClient()) );
    
    WebSocketClient transport = new SockJsClient(transports);
    WebSocketStompClient stompClient = new WebSocketStompClient(transport);
    
    stompClient.setTaskScheduler(taskScheduler);
    stompClient.setDefaultHeartbeat(new long[] {60000L, 60000L});
    stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    
    return stompClient;
	}
  @Bean
  public TaskScheduler taskScheduler(){
      ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
      threadPoolTaskScheduler.setPoolSize(5);  
      threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
      return threadPoolTaskScheduler;
  }
  
  @Bean
  public TaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(5);
    executor.setMaxPoolSize(10);
    executor.setQueueCapacity(25);
    return executor;
  } 	
}
