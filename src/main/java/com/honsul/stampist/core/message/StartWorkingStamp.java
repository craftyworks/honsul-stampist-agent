package com.honsul.stampist.core.message;

import com.honsul.stampist.core.Stamp;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StartWorkingStamp extends StampMessage {
  
  public StartWorkingStamp(String token) {
    super(token, Stamp.START_WORKING);
  }
}
