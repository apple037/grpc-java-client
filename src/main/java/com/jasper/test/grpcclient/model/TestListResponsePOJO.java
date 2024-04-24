package com.jasper.test.grpcclient.model;

import lombok.Data;

@Data
public class TestListResponsePOJO {
  private int count;
  private String type;
  private TestResponsePOJO testResponsePOJO;
}
