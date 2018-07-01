package com.honsul.stampist.agent.websocket;

import java.lang.reflect.Type;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.honsul.stampist.agent.config.StampistAgentConfig;
import com.honsul.stampist.agent.handler.StartWorkingHandler;
import com.honsul.stampist.agent.handler.StopWorkingHandler;
import com.honsul.stampist.core.Stamp;

@Component
public class StampistWebSocketClient implements CommandLineRunner, StompSessionHandler {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  private StampistAgentConfig config;
  
  @Autowired
  private WebSocketStompClient stompClient;
  
  @Autowired
  private TaskScheduler taskScheduler;
  
  @Autowired
  private StartWorkingHandler startWorkingHandler;
  
  @Autowired
  private StopWorkingHandler stopWorkingHandler;
  
  @Override
  public void run(String... args) throws Exception {
    connect();
  }

  private void connect() {
    logger.info("connecting {} with token : {}", config.getServerUrl(), config.getToken());
    StompHeaders connectHeaders = new StompHeaders();
    connectHeaders.add("token", config.getToken());
    
    stompClient.connect(config.getServerUrl(), new WebSocketHttpHeaders(), connectHeaders, this);
  }
  
  @Override
  public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
    logger.info("connected : {}, {}" + session, connectedHeaders);
    startWorkingHandler.setSession(session);
    stopWorkingHandler.setSession(session);
    
    session.subscribe("/user" + Stamp.START_WORKING.getDestination(), startWorkingHandler);
    session.subscribe("/user" + Stamp.STOP_WORKING.getDestination(), stopWorkingHandler);
  }
  
  @Override
  public Type getPayloadType(StompHeaders headers) {
    logger.info("!getPayloadType : {}" + headers);
    return String.class;
  }

  @Override
  public void handleFrame(StompHeaders headers, @Nullable Object payload) {
    logger.info("!handleFrame : {}, {}", headers, payload);
  }

  @Override
  public void handleException(StompSession session, @Nullable StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
    logger.error("handleException", exception);
  }

  @Override
  public void handleTransportError(StompSession session, Throwable exception) {
    logger.error("handleTransportError", exception);
    taskScheduler.schedule(() -> {connect();}, new Date(System.currentTimeMillis() + 10000));
  }
  
  @Scheduled(fixedDelay=30000)
  public void checkSession() {
    //System.out.println(stompClient.isRunning());
  }

}
