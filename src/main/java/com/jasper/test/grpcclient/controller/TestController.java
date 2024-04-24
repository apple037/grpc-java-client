package com.jasper.test.grpcclient.controller;

import com.jasper.test.grpcclient.model.TestListResponsePOJO;
import com.jasper.test.grpcclient.model.TestResponsePOJO;
import com.jasper.test.grpcclient.service.grpc.TestGrpcService;
import com.jasper.test.grpcclient.service.grpc.TestRustGrpcService;
import io.grpc.ConnectivityState;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
  private final TestGrpcService testGrpcService;
  private final TestRustGrpcService testRustGrpcService;

  public TestController(TestGrpcService testGrpcService, TestRustGrpcService testRustGrpcService) {
    this.testGrpcService = testGrpcService;
    this.testRustGrpcService = testRustGrpcService;
  }

  @GetMapping("/poke")
  public Object poke(int times) {
    List<TestListResponsePOJO> testListResponsePOJOS = new ArrayList<>();
    for (int i = 0; i < times; i++) {
      TestResponsePOJO javaRes = testGrpcService.testRequest();
      TestListResponsePOJO testListResponsePOJO = new TestListResponsePOJO();
      testListResponsePOJO.setCount(i+1);
      testListResponsePOJO.setType("java");
      testListResponsePOJO.setTestResponsePOJO(javaRes);
      testListResponsePOJOS.add(testListResponsePOJO);
      // rust
      TestResponsePOJO rustRes = testRustGrpcService.testRequestInRust();
      TestListResponsePOJO testListResponsePOJO2 = new TestListResponsePOJO();
      testListResponsePOJO2.setCount(i+1);
      testListResponsePOJO2.setType("rust");
      testListResponsePOJO2.setTestResponsePOJO(rustRes);
      testListResponsePOJOS.add(testListResponsePOJO2);
    }
    return testListResponsePOJOS;
  }

  @GetMapping("/poke/rust")
  public Object pokeRust(int times) {
    ConnectivityState state = testRustGrpcService.getChannelState();
    System.out.println("state: " + state);
    if (state != ConnectivityState.READY) {
      return "rust grpc not ready";
    }
    List<TestListResponsePOJO> testListResponsePOJOS = new ArrayList<>();
    for (int i = 0; i < times; i++) {
      // rust
      TestResponsePOJO rustRes = testRustGrpcService.testRequestInRust();
      TestListResponsePOJO testListResponsePOJO2 = new TestListResponsePOJO();
      testListResponsePOJO2.setCount(i+1);
      testListResponsePOJO2.setType("rust");
      testListResponsePOJO2.setTestResponsePOJO(rustRes);
      testListResponsePOJOS.add(testListResponsePOJO2);
    }
    return testListResponsePOJOS;
  }

  @GetMapping("/poke/java")
  public Object pokeJava(int times) {
    List<TestListResponsePOJO> testListResponsePOJOS = new ArrayList<>();
    for (int i = 0; i < times; i++) {
      TestResponsePOJO javaRes = testGrpcService.testRequest();
      TestListResponsePOJO testListResponsePOJO = new TestListResponsePOJO();
      testListResponsePOJO.setCount(i+1);
      testListResponsePOJO.setType("java");
      testListResponsePOJO.setTestResponsePOJO(javaRes);
      testListResponsePOJOS.add(testListResponsePOJO);
    }
    return testListResponsePOJOS;
  }

  @GetMapping("/status")
  public ConnectivityState status() {
    return testGrpcService.getChannelState();
  }

  @GetMapping("/health")
  public String health() {
    return "ok";
  }
}
