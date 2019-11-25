package com.ssw.interceptors;

import com.google.gson.Gson;
import com.ssw.common.ResponseCode;
import com.ssw.common.ServerResponse;
import com.ssw.pojo.UserInfo;
import com.ssw.utils.Const;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Auther:Wss
 * @Date:2019/11/4 0004
 * @Description:com.ssw.interceptors
 * @version: 1.0
 */
@Component
public class PortalAuthorityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        HttpSession session = request.getSession();
        UserInfo user=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            response.reset();//重新初始化response,防止报异常
            try {
                response.setHeader("Content-Type","application/json;charset=UTF-8");
                PrintWriter printWriter = response.getWriter();
                ServerResponse serverResponse =  ServerResponse.createServerResponseByError(ResponseCode.NOT_LOGIN,"未登录");
                Gson gson = new Gson();
                String json = gson.toJson(serverResponse);
                printWriter.write(json);
                printWriter.flush();
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }



        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
