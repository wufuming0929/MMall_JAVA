package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 项目名称：eab-ng-mmall
 * 包： com.mmall.util
 * 类名称：CookieUtil.java
 * 类描述：
 * 创建人：wufuming
 * 创建时间：2018年09月17日
 */
@Slf4j
public class CookieUtil {

    private static String COOKIE_NAME = "imooc_login_token";
    private static String DOMAIN_NAME = ".imooc-formain.com";

    public static void writeLoginToken(HttpServletResponse response,String token){

        Cookie cookie = new Cookie(COOKIE_NAME,token);

        cookie.setDomain(DOMAIN_NAME);

        cookie.setHttpOnly(true);//防止脚本攻击
        cookie.setPath("/");
        cookie.setMaxAge(365*24*60*60);// 设置一年的缓存时间，单位秒。setMaxAge会把缓存存在硬盘，如果是-1，代表永久

        log.info("write cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
        response.addCookie(cookie);

    }


    public static String readLoginToken(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(StringUtils.equals(cookie.getName(),COOKIE_NAME)){
                    log.info("read cookieValue:{}",cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void deleteLoginToken(HttpServletRequest request, HttpServletResponse response){

        Cookie[] cookies = request.getCookies();

        if(cookies!=null){
            for (Cookie cookie:cookies){
                if(StringUtils.equals(cookie.getName(),COOKIE_NAME)){
                    cookie.setDomain(DOMAIN_NAME);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);//0，代表删除cookie
                    response.addCookie(cookie);
                    return;

                }
            }

        }
    }


}