package com.jasper.test.grpcclient.config;

import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerChannel {

  @Value("${grpc.channel.test-server.address}")
  private String grpcAddress;

  @Value("${grpc.channel.test-server.port}")
  private int grpcPort;

  @Value("${grpc.netty-server.keep-alive-time}")
  private int keepAliveTime;

  @Value("${grpc.netty-server.keep-alive-timeout}")
  private int keepAliveTimeout;

  @Bean("testServerGrpcChannel")
  public ManagedChannel testServerGrpcChannel() {
    return NettyChannelBuilder.forAddress(grpcAddress, grpcPort)
        .keepAliveTime(keepAliveTime, TimeUnit.SECONDS)
        .keepAliveTimeout(keepAliveTimeout, TimeUnit.SECONDS)
        .keepAliveWithoutCalls(true)
        .usePlaintext()
        .build();
  }

  /**
   * 向 channel 發送確認連線的請求，並取得 channel 的狀態
   * @param channel
   * @return
   */
  public ConnectivityState getChannelState(ManagedChannel channel) {
    return channel.getState(true);
  }

}
