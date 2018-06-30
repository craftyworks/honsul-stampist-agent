package com.honsul.stampist.core.message;

import com.honsul.stampist.core.Stamp;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StopWorkingStamp extends StampMessage {
  public StopWorkingStamp(String token) {
    super(token, Stamp.STOP_WORKING);
  }
}
