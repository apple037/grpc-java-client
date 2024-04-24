package com.jasper.test.grpcclient.model;

import lombok.Data;

@Data
public class TestResponsePOJO {
  private Long sendTimeStamp;
  private Long receiveTimeStamp;
  private Long processTimeCost;
  private Long grpcTimeCost;
}
