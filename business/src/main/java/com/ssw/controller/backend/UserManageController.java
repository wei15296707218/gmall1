package com.ssw.controller.backend;

import com.ssw.common.RoleEnum;
import com.ssw.common.ServerResponse;
import com.ssw.service.IUserService;
import com.ssw.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/")
public class UserManageController {
    @Autowired
    IUserService userService;
    @RequestMapping(value="login")
    public ServerResponse login( String username,
                                 String password,
                                HttpSession session){
        ServerResponse serverResponse =userService.login(username,password, RoleEnum.ROLE_ADMIN.getRole());
        //判断是否登录成功
        if (serverResponse.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,serverResponse.getData());
        }

        return serverResponse;
    }

}
