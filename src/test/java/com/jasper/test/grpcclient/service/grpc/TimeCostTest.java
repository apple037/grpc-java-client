package com.jasper.test.grpcclient.service.grpc;

import com.jasper.test.grpc.grpcserver.TestConnection.TestResponse;
import com.jasper.test.grpcclient.GrpcClientApplication;
import com.jasper.test.grpcclient.model.TestResponsePOJO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = GrpcClientApplication.class)
@Slf4j
@ActiveProfiles("local")
public class TimeCostTest {
  @Autowired
  private TestGrpcService testGrpcService;

  @Nested
  class GRPCTest {
    @Test
    public void testRequest() {
      TestResponsePOJO response = testGrpcService.testRequest();
      log.debug("Test response: {}", response);
    }
  }
}
