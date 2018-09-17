package com.mmall.util;

import com.mmall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 项目名称：eab-ng-mmall
 * 包： com.mmall.util
 * 类名称：JsonUtil.java
 * 类描述：
 * 创建人：wufuming
 * 创建时间：2018年09月15日
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));
        //忽略空Bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        //取消默认转换timestamps形式(取消默认的毫秒形式)
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static<T> String obj2String(T obj){

        if(obj==null){
            return null;
        }
        try {
            return obj instanceof String?(String) obj:objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("Parse Object  to String error",e);
            e.printStackTrace();
            return null;
        }
    }

    public static<T> String obj2StringPretty(T obj){

        if(obj==null){
            return null;
        }
        try {
            return obj instanceof String?(String) obj:objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("Parse Object  to String error",e);
            e.printStackTrace();
            return null;
        }
    }

    public static<T> T str2Obj(String str,Class<T> clazz){

        if(StringUtils.isEmpty(str)|| clazz==null){
            return null;
        }
        try {
            return str.equals(String.class)?(T)str:objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            log.warn("Parse String to Object error",e);
            e.printStackTrace();
            return null;
        }
    }

    public static<T> T str2Obj(String str, TypeReference<T> typeReference){

        if(StringUtils.isEmpty(str)|| typeReference==null){
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class)?str:objectMapper.readValue(str,typeReference));
        } catch (IOException e) {
            log.warn("Parse String to Object error",e);
            e.printStackTrace();
            return null;
        }

    }

    public static<T> T str2Obj(String str,Class<?> collectionClass,Class<?>... elementClasses ){

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (IOException e) {
            log.warn("Parse String to Object error",e);
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] arges) {

        User u1 = new User();
        u1.setId(1);
        u1.setEmail("wufuming0929@163.com");
        u1.setCreateTime(new Date());

        String jsonStr = JsonUtil.obj2StringPretty(u1);
        log.info("jsonStr:{}", jsonStr);


        /*User u2 = new User();
        u2.setEmail("wufumingu20929@163.com");

        List<User> list = new ArrayList<>();
        list.add(u1);
        list.add(u2);

        String listStr = JsonUtil.obj2StringPretty(list);

        log.info("listStr:{}", listStr);

        List<User> jsonList = JsonUtil.str2Obj(listStr, new TypeReference<List<User>>() {});

        List<User> list1=JsonUtil.str2Obj(listStr, List.class, User.class);

        System.out.println(jsonList);*/




    }



}