package com.honsul.stampist.core.message;

import com.honsul.stampist.core.Stamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StampMessage {
  private String token;
  
  private Stamp subscription;

  public String getDestination() {
    return this.subscription.getDestination();
  }
}
