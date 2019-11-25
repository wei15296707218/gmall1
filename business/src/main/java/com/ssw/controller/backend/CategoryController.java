package com.ssw.controller.backend;

import com.ssw.common.ResponseCode;
import com.ssw.common.RoleEnum;
import com.ssw.common.ServerResponse;
import com.ssw.pojo.Category;
import com.ssw.pojo.UserInfo;
import com.ssw.service.ICategoryService;
import com.ssw.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/category/")
public class CategoryController {
    @Autowired
    ICategoryService categoryService;
    //添加类别
    @RequestMapping("/add_category.do")
    public ServerResponse addCategory(Category category, HttpSession session){
            UserInfo user=(UserInfo) session.getAttribute(Const.CURRENT_USER);
            if (user==null){
                return ServerResponse.createServerResponseByError(ResponseCode.NOT_LOGIN,"未登录");
            }
            int role=user.getRole();
            if (role== RoleEnum.ROLE_USER.getRole()){
                return ServerResponse.createServerResponseByError(ResponseCode.NOT_LOGIN,"权限不足");
            }
        return categoryService.addCategory(category);
    }
    //修改类别  categoryId,categoryName,categoryUrl
    @RequestMapping("/set_category.do")
    public ServerResponse updateCategory(Category category,HttpSession session){
        UserInfo user=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=user.getRole();
        if (role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_LOGIN,"权限不足");
        }
        return categoryService.updateCategory(category);
    }

    //查看类别  categoryId,categoryName,categoryUrl
    @RequestMapping("/{categoryId}")
    public ServerResponse getCategoryById(@PathVariable("categoryId") Integer categoryId,HttpSession session){

        return categoryService.getCategoryById(categoryId);
    }
    //递归查看类别  categoryId,categoryName,categoryUrl
    @RequestMapping("/deep/{categoryId}")
    public ServerResponse deepCategory(@PathVariable("categoryId") Integer categoryId,HttpSession session){
        UserInfo user=(UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }
        int role=user.getRole();
        if (role== RoleEnum.ROLE_USER.getRole()){
            return ServerResponse.createServerResponseByError(ResponseCode.NOT_LOGIN,"权限不足");
        }
        return categoryService.deepCategory(categoryId);
    }
}
