package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {
    private static final String COOKIE_NAME = "mmall.login.token";
    private static final String COOKIE_DOMAIN = ".aaa.com";

    public static void writeLoginToken(HttpServletResponse response,String token){
        Cookie cookie = new Cookie(COOKIE_NAME,token);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setMaxAge(60*60*24*365);
        cookie.setPath("/cookie");
        log.info("write cookieName：{},cookieValue:{}",cookie.getName(),cookie.getValue());
        response.addCookie(cookie);
    }

    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cks = request.getCookies();
        if(cks!=null){
            for(Cookie ck:cks){
                log.info("read cookieName：{},cookieValue:{}",ck.getName(),ck.getValue());
                if(StringUtils.equals(COOKIE_NAME, ck.getName())){
                    log.info("return cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cks = request.getCookies();
        if(cks!=null){
            for(Cookie ck:cks){
                if(StringUtils.equals(COOKIE_NAME, ck.getName())){
                    log.info("delete cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/cookie");
                    ck.setMaxAge(0);
                    response.addCookie(ck);
                }
            }
        }
    }
}
