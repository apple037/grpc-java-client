package com.jasper.test.grpcclient.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.LazyStringList;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import lombok.SneakyThrows;

public class ProtoJsonUtils {
  private static Gson gson = getGson();

  /**
   * protoBean -> string
   *
   * @param sourceMessage protoBean
   * @return string
   */
  public static String protoToJson(MessageOrBuilder sourceMessage) {
    try {
      return JsonFormat.printer().print(sourceMessage);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("ProtoJsonUtils->toJson->errorMessage:proto序列化string异常");
    }
  }

  /**
   * @desc: String 转 Builder对象
   */
  private static <M extends Message.Builder> void jsonToBuilder(M targetBuilder, String json) {
    try {
      JsonFormat.parser().ignoringUnknownFields().merge(json, targetBuilder);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("ProtoJsonUtils->JsonToBuilder->errorMessage:json 转 Builder对象异常");
    }
  }

  /**
   * @desc: ProtoBean 转 JavaBean messageOrBuilder protoBean target javaBean 的 Class对象
   */
  public static <T, M extends MessageOrBuilder> T protoToBean(M messageOrBuilder, Class<T> target) {
    String protoJson = protoToJson(messageOrBuilder);
    Gson gson = getGson();
    return gson.fromJson(protoJson, target);
  }

  /**
   * @desc: ProtoBean 转 JavaBean messageOrBuilder protoBean target javaBean 的 Class对象
   */
  public static <T, M extends MessageOrBuilder> T protoToBean(M messageOrBuilder, Type target) {
    String protoJson = protoToJson(messageOrBuilder);
    Gson gson = getGson();
    return gson.fromJson(protoJson, target);
  }

  /**
   * @desc: ProtoBeanList 转 JavaBeanList
   */
  public static <T, M extends MessageOrBuilder> List<T> protoListToBeanList(
      List<M> messageOrBuilders, Class<T> target) {
    List<T> list = new ArrayList<>();
    for (M messageOrBuilder : messageOrBuilders) {
      T t = protoToBean(messageOrBuilder, target);
      list.add(t);
    }
    return list;
  }

  /**
   * @desc: ProtoBean 转 ProtoBean
   */
  public static <T extends Message, M extends Message> T protoToProto(
      M messageOrBuilder, Class<T> protoClass) {
    try {
      String protoJson = protoToJson(messageOrBuilder);

      Constructor<T> declaredConstructor = protoClass.getDeclaredConstructor((Class<?>[]) null);

      declaredConstructor.setAccessible(Boolean.TRUE);

      T instance = declaredConstructor.newInstance();

      Message.Builder builder = instance.toBuilder();

      jsonToBuilder(builder, protoJson);

      return (T) builder.build();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(
          "ProtoJsonUtils->protoToProto->errorMessage:ProtoBean 转 ProtoBean 异常");
    }
  }

  /**
   * @desc: ProtoListBean 转 ProtoListBean
   */
  public static <T extends Message, M extends Message> List<T> protoListToProtoList(
      List<M> messageOrBuilders, Class<T> protoClass) {
    List<T> list = new ArrayList<>();
    for (M messageOrBuilder : messageOrBuilders) {
      T t = protoToProto(messageOrBuilder, protoClass);
      list.add(t);
    }
    return list;
  }

  /**
   * @desc: JavaBean 转 JavaBean (单个对象)
   */
  public static <T, M> T beanToBean(M source, Class<T> target) {
    Gson gson = getGson();
    String json = gson.toJson(source);
    return gson.fromJson(json, target);
  }

  /**
   * @desc: JavaBeanList 转 JavaBeanList
   */
  public static <T> List<T> beanToBean(Collection<?> source, Class<T> target) {
    Gson gson = getGson();
    String json = gson.toJson(source);
    return gson.fromJson(json, new ParameterizedTypeImpl(target));
  }

  /**
   * @desc: javaBean 转 Json
   */
  public static String beanToJson(Object o) {
    Gson gson = getGson();
    return gson.toJson(o);
  }

  /**
   * @desc: json 转 JavaBean
   */
  public static <T> T jsonToBean(String jsonString, Class<T> clazz) {
    Gson gson = getGson();
    return gson.fromJson(jsonString, clazz);
  }

  public static <T> List<T> jsonToBeanList(String json, Class<T> clazz) {
    Type type = new ParameterizedTypeImpl(clazz);
    Gson gson = getGson();
    return gson.fromJson(json, type);
  }

  /**
   * @desc: JavaBean 转 Builder对象
   */
  public static <M extends Message.Builder, T> M beanToBuilder(
      T beanSource, Class<M> targetBuilderClass) {
    try {
      Constructor<M> declaredConstructor =
          targetBuilderClass.getDeclaredConstructor((Class<?>[]) null);

      declaredConstructor.setAccessible(Boolean.TRUE);

      M instance = declaredConstructor.newInstance();

      Gson gson = getGson();

      String json = gson.toJson(beanSource);

      jsonToBuilder(instance, json);

      return instance;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(
          "ProtoJsonUtils->beanToBuilder->errorMessage:JavaBean 转 Builder对象异常");
    }
  }

  /**
   * @desc: javaBean 转 Proto
   */
  @SneakyThrows
  public static <M extends Message, T> M beanToProto(T bean, Class<M> targetProtoClass) {

    Constructor<M> declaredConstructor = targetProtoClass.getDeclaredConstructor((Class<?>[]) null);

    declaredConstructor.setAccessible(Boolean.TRUE);

    M instance = declaredConstructor.newInstance();

    Gson gson = getGson();

    String json = gson.toJson(bean);

    Message.Builder builder = instance.toBuilder();

    jsonToBuilder(builder, json);

    return (M) builder.build();
  }

  /**
   * @desc: javaBean 列表 转 ProtoBean 列表
   */
  public static <M, T extends Message> List<T> beanListToProtoList(
      Collection<M> beanSourceList, Class<T> protoBean) {
    try {
      List<T> resultList = new ArrayList<>();
      for (Object bean : beanSourceList) {

        Constructor<T> declaredConstructor = protoBean.getDeclaredConstructor((Class<?>[]) null);

        declaredConstructor.setAccessible(Boolean.TRUE);

        T instance = declaredConstructor.newInstance();

        Class<? extends Message.Builder> aClass = instance.toBuilder().getClass();

        Message.Builder builder = beanToBuilder(bean, aClass);

        resultList.add((T) builder.build());
      }
      return resultList;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(
          "ProtoJsonUtils->beanListToProtoList->errorMessage:javaBean 列表 转 ProtoBean 列表异常");
    }
  }

  private static Gson getGson() {
    if (gson != null) {
      return gson;
    }
    gson =
        new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(Date.class, new DateAdapter())
            .registerTypeAdapter(
                LazyStringList.class,
                new TypeAdapter<LazyStringList>() {

                  @Override
                  public void write(JsonWriter jsonWriter, LazyStringList strings)
                      throws IOException {}

                  @Override
                  public LazyStringList read(JsonReader in) throws IOException {
                    LazyStringList lazyStringList = new LazyStringArrayList();

                    in.beginArray();

                    while (in.hasNext()) {
                      lazyStringList.add(in.nextString());
                    }

                    in.endArray();

                    return lazyStringList;
                  }
                })
            .create();
    return gson;
  }

  public static final class DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
    @Override
    public JsonElement serialize(
        Date src, Type typeOfSrc, JsonSerializationContext jsonSerializationContext) {
      // Create a SimpleDateFormat instance with your desired format
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      // Format the Date object to the desired format
      String formattedDate = dateFormat.format(src);
      // Return the formatted date as a JsonPrimitive
      return new JsonPrimitive(formattedDate);
    }

    @Override
    public Date deserialize(JsonElement element, Type type, JsonDeserializationContext context)
            throws JsonParseException {
      String timestamp = element.getAsJsonPrimitive().getAsString();
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      try {
        return dateFormat.parse(timestamp);
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public static final class LocalDateAdapter
      implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    @Override
    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    @Override
    public LocalDate deserialize(JsonElement element, Type type, JsonDeserializationContext context)
        throws JsonParseException {
      String timestamp = element.getAsJsonPrimitive().getAsString();
      return LocalDate.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
  }

  public static final class LocalDateTimeAdapter
      implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    @Override
    public JsonElement serialize(
        LocalDateTime date, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Override
    public LocalDateTime deserialize(
        JsonElement element, Type type, JsonDeserializationContext context)
        throws JsonParseException {
      String timestamp = element.getAsJsonPrimitive().getAsString();
      return LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
  }

  private static class ParameterizedTypeImpl implements ParameterizedType {
    Class clazz;

    public ParameterizedTypeImpl(Class clz) {
      clazz = clz;
    }

    @Override
    public Type[] getActualTypeArguments() {
      return new Type[] {clazz};
    }

    @Override
    public Type getRawType() {
      return List.class;
    }

    @Override
    public Type getOwnerType() {
      return null;
    }
  }
}
