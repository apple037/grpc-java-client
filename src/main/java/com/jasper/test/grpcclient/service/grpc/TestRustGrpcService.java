package com.jasper.test.grpcclient.service.grpc;

import com.google.protobuf.Timestamp;
import com.jasper.test.grpc.grpcserver.GRPCTestServiceGrpc;
import com.jasper.test.grpc.grpcserver.TestConnection.TestRequest;
import com.jasper.test.grpc.grpcserver.TestConnection.TestResponse;
import com.jasper.test.grpcclient.model.TestResponsePOJO;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestRustGrpcService {
  private final ManagedChannel rustServerGrpcChannel;

  public TestRustGrpcService(
      @Qualifier("rustServerGrpcChannel") ManagedChannel rustServerGrpcChannel) {
    this.rustServerGrpcChannel = rustServerGrpcChannel;
  }

  @SneakyThrows
  public TestResponsePOJO testRequestInRust() {
    log.debug("testRequest() start");
    long sendTimeStamp = System.currentTimeMillis();
    try {
      TestRequest request = TestRequest.newBuilder()
          .setSendTimeStamp(getTimestampInTimestamp(sendTimeStamp))
          .build();

      TestResponse responseProto =
          GRPCTestServiceGrpc.newBlockingStub(rustServerGrpcChannel)
              .testConnectionTimeCost(request);
      TestResponsePOJO responsePOJO = new TestResponsePOJO();
      responsePOJO.setSendTimeStamp(getTimestampInMilliseconds(responseProto.getSendTimeStamp()));
      responsePOJO.setReceiveTimeStamp(getTimestampInMilliseconds(responseProto.getReceiveTimeStamp()));
      responsePOJO.setProcessTimeCost(getTimestampInMilliseconds(responseProto.getProcessTimeCost()));
      log.debug("testRequest() responsePOJO: {}", responsePOJO);
      Long currentTimeStamp = System.currentTimeMillis();
      Long timeCostThroughGrpc = currentTimeStamp - responsePOJO.getReceiveTimeStamp();
      log.debug("Time cost through grpc: {}", timeCostThroughGrpc);
      responsePOJO.setGrpcTimeCost(timeCostThroughGrpc);
      return responsePOJO;
    }
    catch (Exception e) {
      log.error("testRequest() error.", e);
      throw e;
    }
  }

  public ConnectivityState getChannelState() {
    return rustServerGrpcChannel.getState(true);
  }

  private long getTimestampInMilliseconds(Timestamp timestamp) {
    return timestamp.getSeconds() * 1000 + timestamp.getNanos() / 1000000;
  }

  private Timestamp getTimestampInTimestamp(long timestampInMilliseconds) {
    long seconds = timestampInMilliseconds / 1000;
    int nanoSeconds = (int) ((timestampInMilliseconds % 1000) * 1000000);
    return Timestamp.newBuilder().setSeconds(seconds).setNanos(nanoSeconds).build();
  }
}
