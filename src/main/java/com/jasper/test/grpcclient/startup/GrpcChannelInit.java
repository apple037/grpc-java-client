package com.jasper.test.grpcclient.startup;

import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GrpcChannelInit implements CommandLineRunner {
  private final ManagedChannel testServerGrpcChannel;
  private final ManagedChannel rustServerGrpcChannel;

  public GrpcChannelInit(@Qualifier("testServerGrpcChannel") ManagedChannel testServerGrpcChannel,
     @Qualifier("rustServerGrpcChannel") ManagedChannel rustServerGrpcChannel) {
    this.testServerGrpcChannel = testServerGrpcChannel;
    this.rustServerGrpcChannel = rustServerGrpcChannel;
  }


  @Override
  public void run(String... args) throws Exception {
    log.debug("[GRPC][testServerGrpcChannel][State][{}]", testServerGrpcChannel.getState(true));
    log.debug("[GRPC][rustServerGrpcChannel][State][{}]", rustServerGrpcChannel.getState(true));
  }
}
