/**
 * FileName: UserService
 * Author:   嘉平十七
 * Date:     2021/1/28 18:57
 * Description: 用户服务接口
 */
package com.hunau.competition.service;

import com.hunau.competition.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Page<User> listUsers(Pageable pageable);

    //注册
    User addUser(User newUser);

    //用户名登录
    User checkUserByUsername(String username,String password);

    //邮箱登录
    User checkUserByEmail(String email,String password);

    //通过用户名查询
    User findByUsername(String username);

    //通过ID查询
    User findById(Long userId);

    //关键字查询
    Page<User> findByQuery(String query, Pageable pageable);

    //通过邮箱查询
    User findByEmail(String email);

    //更新用户信息
    String updateUser(User user);

    //用户报名比赛
    String enrollCompetition(Long userId,Long competitionId);

    //用户取消报名的比赛
    String deleteEnroll(Long userId,Long competitionId);

    //查询参数的用户
    List<User> listEnrollUser(Long competitionId);

    void deleteUser(Long id);
}
