package com.ssw.dao;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public interface UserInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    int insert(UserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    UserInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    List<UserInfo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserInfo record);
    int isexistsusername(String username);
    int isexistsemail(String email);
    //根据用户名和密码查询
    UserInfo findUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    //@Param主要应用在Dao层,@RequestParam主要应用在Controller层,@PathVariable应用在Controller层
    //根据用户名获取密保问题
    public String forget_get_question(@Param("username") String username);
    //提交答案
    public int forget_check_answer(@Param("username") String username,
                                              @Param("question") String question,
                                              @Param("answer")String answer);
    //修改密码
    public int forget_reset_password(@Param("username") String username,
                                                @Param("newpassword") String newpassword);
    //修改信息
    public int updateUserByActive(@Param("user") UserInfo user);
}