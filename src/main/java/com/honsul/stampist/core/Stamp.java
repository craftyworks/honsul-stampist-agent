package com.honsul.stampist.core;

public enum Stamp {
  STOP_WORKING("/stamp/stop.working"),
  START_WORKING("/stamp/start.working");
  
  private String destination;
  
  private Stamp(String destination) {
    this.destination = destination;
  }
  
  public String getDestination() {
    return this.destination;
  }

}
