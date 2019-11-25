package com.ssw;

import com.google.common.collect.Lists;
import com.ssw.interceptors.AdminAuthoityInterceptor;
import com.ssw.interceptors.PortalAuthorityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.List;

/**
 * @Auther:Wss
 * @Date:2019/11/4 0004
 * @Description:com.ssw
 * @version: 1.0
 */
/*
拦截后台请求,验证用户是否登陆
 */
@SpringBootConfiguration
public class MySpringBootConfig implements WebMvcConfigurer {
    @Autowired
    AdminAuthoityInterceptor adminAuthoityInterceptor;

    @Autowired
    PortalAuthorityInterceptor portalAuthorityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> excludePathPaterns1 = Lists.newArrayList();
        excludePathPaterns1.add("/manage/login/**");
        excludePathPaterns1.add("/manage/product/hot.do");
        excludePathPaterns1.add("/manage/product/detail.do");
        //拦截后台
        registry.addInterceptor(adminAuthoityInterceptor).addPathPatterns("/manage/**").excludePathPatterns(excludePathPaterns1);
        //拦截前台
        List<String> addInterceptor = Lists.newArrayList();
        addInterceptor.add("/user/**");
        addInterceptor.add("/cart/**");
        addInterceptor.add("/order/**");
        addInterceptor.add("/shipping/**");

        //不需要拦截的
        List<String> excludePathPaterns = Lists.newArrayList();
        excludePathPaterns.add("/user/register.do");
        excludePathPaterns.add("/user/login/**");
        excludePathPaterns.add("/user/forget_get_question/**");
        excludePathPaterns.add("/user/forget_check_answer.do");
        excludePathPaterns.add("/user/forget_reset_password.do");
        excludePathPaterns.add("/order/callback.do");
        //excludePathPaterns.add("/order/list.do");
        //excludePathPaterns.add("/cart/list.do");
        registry.addInterceptor(portalAuthorityInterceptor).addPathPatterns(addInterceptor).
                excludePathPatterns(excludePathPaterns);

    }


}
