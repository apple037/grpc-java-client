package com.jasper.test.grpcclient.service.grpc;

import com.google.protobuf.Int32Value;
import com.google.protobuf.Timestamp;
import com.jasper.test.grpc.grpcserver.GRPCTestServiceGrpc;
import com.jasper.test.grpc.grpcserver.TestConnection.TestRequest;
import com.jasper.test.grpc.grpcserver.TestConnection.TestResponse;
import com.jasper.test.grpcclient.common.ProtoJsonUtils;
import com.jasper.test.grpcclient.model.TestRequestPOJO;
import com.jasper.test.grpcclient.model.TestResponsePOJO;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestGrpcService {
  private final ManagedChannel testServerGrpcChannel;

  public TestGrpcService(@Qualifier("testServerGrpcChannel")
      ManagedChannel testServerGrpcChannel) {
    this.testServerGrpcChannel = testServerGrpcChannel;
  }

  @SneakyThrows
  public TestResponsePOJO testRequest() {
    log.debug("testRequest() start");
    long sendTimeStamp = System.currentTimeMillis();
    try {
      TestRequest request = TestRequest.newBuilder()
          .setSendTimeStamp(getTimestampInTimestamp(sendTimeStamp))
          .build();

      TestResponse responseProto =
          GRPCTestServiceGrpc.newBlockingStub(testServerGrpcChannel)
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
    return testServerGrpcChannel.getState(true);
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
