package com.honsul.stampist.agent.handler;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;

import com.honsul.stampist.core.message.StampStatusMessage;
import com.honsul.stampist.core.message.StopWorkingStamp;

@Component
public class StopWorkingHandler implements StompFrameHandler{
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private StompSession session;
  
  public void setSession(StompSession session) {
    this.session = session;
  }
  
  @Override
  public Type getPayloadType(StompHeaders headers) {
    logger.info("getPayloadType : {}" + headers);
    return StopWorkingStamp.class;
  }

  @Override
  public void handleFrame(StompHeaders headers, Object payload) {
    logger.info("handleFrame : {}, {}, {}", headers, payload, payload.getClass());
    
    StopWorkingStamp message = (StopWorkingStamp) payload;
    
    StampStatusMessage response = StampStatusMessage
      .builder()
      .token(message.getToken())
      .date("2018-07-01")
      .startWorking("08:00")
      .stopWorking("09:00").build();
    
    
    this.session.send("/app/stamp.status", response);    
  }

}
