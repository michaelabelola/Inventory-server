package com.buyern.inventory;

import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {
    Logger logIntercept = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
    //    @Override
//    public void afterCompletion(HttpServletRequest request,
//                                HttpServletResponse response, Object object, Exception arg3)
//            throws Exception {
//        logIntercept.info("State: After completion");
//    }
//    @Override
//    public void postHandle(HttpServletRequest request,
//                           HttpServletResponse response, Object object, ModelAndView model)
//            throws Exception {
//        logIntercept.info("State: Post request is handled");
//    }
//    @Override
//    public boolean preHandle(HttpServletRequest request,
//                             HttpServletResponse response, Object object) throws Exception {
//        logIntercept.info("State: Before request reaches controller");
//        return true;
//    }

}
