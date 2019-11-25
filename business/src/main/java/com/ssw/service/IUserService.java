package com.ssw.service;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.UserInfo;
import org.springframework.web.bind.annotation.PathVariable;

public interface IUserService {
    //注册接口
    public ServerResponse register(UserInfo user);
    //登录接口   type:1:普通用户   0：管理员
    public ServerResponse login(String username,String password,int type);
    public ServerResponse forget_get_question(@PathVariable("username") String username);
    //提交答案
    public ServerResponse forget_check_answer(String username,String question,String answer);
    //修改密码
    public ServerResponse forget_reset_password(String username,String newpassword,String forgettoken);
    //修改信息
    public ServerResponse update_information(UserInfo user);
    //查询登录状态下用户的详细信息
    public UserInfo findUserInfoByUserid(Integer userid);
}
