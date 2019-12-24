package com.mmall.util;

import com.google.common.collect.Lists;
import com.mmall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
        //取消转换默认timpstamp形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);
        //忽略空bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        //忽略json有java对象中没有对应属性的错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //所有日期格式统一为 yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));
    }

    public static  <T> String ObjectToString(T object){
        if(object == null){
            return null;
        }
        try {
            return object instanceof String?(String) object:objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.warn("Object to String错误",e);
            return null;
        }
    }
    public static  <T> String ObjectToStringPretty(T object){
        if(object == null){
            return null;
        }
        try {
            return object instanceof String?(String) object:objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            log.warn("Object to String错误",e);
            return null;
        }
    }

    public static  <T> T StrToObj(String str,Class<T> clazz){
        if(str.isEmpty()||clazz == null){
            return null;
        }
        try {
            return clazz.equals(String.class)?(T)str:objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            log.warn("string转换对象错误", e);
            return null;
        }
    }

    public static <T> T StrToObj(String str, TypeReference typeReference) {
        if (str.isEmpty() || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? (T) str : objectMapper.readValue(str, typeReference));
        } catch (IOException e) {
            log.warn("string转换对象错误", e);
            return null;
        }
    }

    public static  <T> T StrToObj(String str,Class<?> collectionClass,Class<?> ... typeClass) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, typeClass);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            log.warn("string转换对象错误", e);
            return null;
        }
    }

}
