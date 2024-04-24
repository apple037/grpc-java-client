package com.jasper.test.grpc.grpcserver;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.61.0)",
    comments = "Source: TestConnection.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class GRPCTestServiceGrpc {

  private GRPCTestServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.jasper.test.grpc.grpcserver.GRPCTestService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.jasper.test.grpc.grpcserver.TestConnection.TestRequest,
      com.jasper.test.grpc.grpcserver.TestConnection.TestResponse> getTestConnectionTimeCostMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "testConnectionTimeCost",
      requestType = com.jasper.test.grpc.grpcserver.TestConnection.TestRequest.class,
      responseType = com.jasper.test.grpc.grpcserver.TestConnection.TestResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.jasper.test.grpc.grpcserver.TestConnection.TestRequest,
      com.jasper.test.grpc.grpcserver.TestConnection.TestResponse> getTestConnectionTimeCostMethod() {
    io.grpc.MethodDescriptor<com.jasper.test.grpc.grpcserver.TestConnection.TestRequest, com.jasper.test.grpc.grpcserver.TestConnection.TestResponse> getTestConnectionTimeCostMethod;
    if ((getTestConnectionTimeCostMethod = GRPCTestServiceGrpc.getTestConnectionTimeCostMethod) == null) {
      synchronized (GRPCTestServiceGrpc.class) {
        if ((getTestConnectionTimeCostMethod = GRPCTestServiceGrpc.getTestConnectionTimeCostMethod) == null) {
          GRPCTestServiceGrpc.getTestConnectionTimeCostMethod = getTestConnectionTimeCostMethod =
              io.grpc.MethodDescriptor.<com.jasper.test.grpc.grpcserver.TestConnection.TestRequest, com.jasper.test.grpc.grpcserver.TestConnection.TestResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "testConnectionTimeCost"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.jasper.test.grpc.grpcserver.TestConnection.TestRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.jasper.test.grpc.grpcserver.TestConnection.TestResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GRPCTestServiceMethodDescriptorSupplier("testConnectionTimeCost"))
              .build();
        }
      }
    }
    return getTestConnectionTimeCostMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GRPCTestServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GRPCTestServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GRPCTestServiceStub>() {
        @java.lang.Override
        public GRPCTestServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GRPCTestServiceStub(channel, callOptions);
        }
      };
    return GRPCTestServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GRPCTestServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GRPCTestServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GRPCTestServiceBlockingStub>() {
        @java.lang.Override
        public GRPCTestServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GRPCTestServiceBlockingStub(channel, callOptions);
        }
      };
    return GRPCTestServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GRPCTestServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GRPCTestServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GRPCTestServiceFutureStub>() {
        @java.lang.Override
        public GRPCTestServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GRPCTestServiceFutureStub(channel, callOptions);
        }
      };
    return GRPCTestServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void testConnectionTimeCost(com.jasper.test.grpc.grpcserver.TestConnection.TestRequest request,
        io.grpc.stub.StreamObserver<com.jasper.test.grpc.grpcserver.TestConnection.TestResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTestConnectionTimeCostMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service GRPCTestService.
   */
  public static abstract class GRPCTestServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return GRPCTestServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service GRPCTestService.
   */
  public static final class GRPCTestServiceStub
      extends io.grpc.stub.AbstractAsyncStub<GRPCTestServiceStub> {
    private GRPCTestServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GRPCTestServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GRPCTestServiceStub(channel, callOptions);
    }

    /**
     */
    public void testConnectionTimeCost(com.jasper.test.grpc.grpcserver.TestConnection.TestRequest request,
        io.grpc.stub.StreamObserver<com.jasper.test.grpc.grpcserver.TestConnection.TestResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTestConnectionTimeCostMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service GRPCTestService.
   */
  public static final class GRPCTestServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<GRPCTestServiceBlockingStub> {
    private GRPCTestServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GRPCTestServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GRPCTestServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.jasper.test.grpc.grpcserver.TestConnection.TestResponse testConnectionTimeCost(com.jasper.test.grpc.grpcserver.TestConnection.TestRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTestConnectionTimeCostMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service GRPCTestService.
   */
  public static final class GRPCTestServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<GRPCTestServiceFutureStub> {
    private GRPCTestServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GRPCTestServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GRPCTestServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.jasper.test.grpc.grpcserver.TestConnection.TestResponse> testConnectionTimeCost(
        com.jasper.test.grpc.grpcserver.TestConnection.TestRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTestConnectionTimeCostMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_TEST_CONNECTION_TIME_COST = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_TEST_CONNECTION_TIME_COST:
          serviceImpl.testConnectionTimeCost((com.jasper.test.grpc.grpcserver.TestConnection.TestRequest) request,
              (io.grpc.stub.StreamObserver<com.jasper.test.grpc.grpcserver.TestConnection.TestResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getTestConnectionTimeCostMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.jasper.test.grpc.grpcserver.TestConnection.TestRequest,
              com.jasper.test.grpc.grpcserver.TestConnection.TestResponse>(
                service, METHODID_TEST_CONNECTION_TIME_COST)))
        .build();
  }

  private static abstract class GRPCTestServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GRPCTestServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.jasper.test.grpc.grpcserver.TestConnection.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("GRPCTestService");
    }
  }

  private static final class GRPCTestServiceFileDescriptorSupplier
      extends GRPCTestServiceBaseDescriptorSupplier {
    GRPCTestServiceFileDescriptorSupplier() {}
  }

  private static final class GRPCTestServiceMethodDescriptorSupplier
      extends GRPCTestServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    GRPCTestServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GRPCTestServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GRPCTestServiceFileDescriptorSupplier())
              .addMethod(getTestConnectionTimeCostMethod())
              .build();
        }
      }
    }
    return result;
  }
}
