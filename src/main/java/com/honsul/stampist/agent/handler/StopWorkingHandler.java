package com.honsul.stampist.agent.handler;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.stereotype.Component;

import com.honsul.stampist.core.message.StopWorkingStamp;

@Component
public class StopWorkingHandler implements StompFrameHandler{
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Override
  public Type getPayloadType(StompHeaders headers) {
    logger.info("getPayloadType : {}" + headers);
    return StopWorkingStamp.class;
  }

  @Override
  public void handleFrame(StompHeaders headers, Object payload) {
    logger.info("handleFrame : {}, {}, {}", headers, payload, payload.getClass());
  }

}
