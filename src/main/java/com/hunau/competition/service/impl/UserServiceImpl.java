/**
 * FileName: UserServiceImpl
 * Author:   嘉平十七
 * Date:     2021/1/28 18:59
 * Description:
 */
package com.hunau.competition.service.impl;

import com.hunau.competition.dao.UserRepository;
import com.hunau.competition.domain.User;
import com.hunau.competition.service.UserService;
import com.hunau.competition.utils.MyBeanUtils;
import com.hunau.competition.utils.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User addUser(User newUser) {
        User user1 = userRepository.findByUsername(newUser.getUsername());
        User user2 = userRepository.findByEmail(newUser.getEmail());
        if (user1 == null && user2 == null){
            return userRepository.save(newUser);    //注册
        }else {
            throw new NotFoundException("用户名或邮箱已经注册");
        }
    }

    @Override
    public User checkUserByUsername(String username, String password) {
        return userRepository.findByUsernameAndPassword(username,password);
    }

    @Override
    public User checkUserByEmail(String email, String password) {
        return userRepository.findByEmailAndPassword(email,password);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.getOne(userId);
    }

    @Override
    public Page<User> findByQuery(String query, Pageable pageable) {
        return userRepository.findByQuery(query,pageable);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String updateUser(User user) {
        //方法1：使用自定义SQL语句
/*        userRepository.updateUser(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAvatar(),
                user.getDescription());*/

        //方法2：使用save()方法需要处理输入为null的问题，使用MyBeanUtil工具类来处理
        User userCopy = userRepository.getOne(user.getId());
        BeanUtils.copyProperties(user,userCopy, MyBeanUtils.getNullPropertyNames(user));
        userRepository.save(userCopy);
        return "更新成功";
    }

    @Override
    public String enrollCompetition(Long userId, Long competitionId) {
        userRepository.enrollCompetition(userId, competitionId);
        return "报名成功";
    }

    @Override
    public String deleteEnroll(Long userId, Long competitionId) {
        userRepository.deleteEnroll(userId,competitionId);
        return "取消报名";
    }

    @Override
    public List<User> listEnrollUser(Long competitionId) {
        return userRepository.findEnrollUser(competitionId);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        System.out.println("server中删除用户");
        userRepository.deleteById(id);
    }

}