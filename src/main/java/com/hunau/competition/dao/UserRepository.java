/**
 * FileName: UserRepository
 * Author:   嘉平十七
 * Date:     2021/1/29 16:32
 * Description:
 */
package com.hunau.competition.dao;

import com.hunau.competition.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * 通过用户名查询，用于注册检验重名
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 用户名模糊查询
     * @param query
     * @param pageable
     * @return
     */
    @Query("select u from User u where u.username like ?1")
    Page<User> findByQuery(String query, Pageable pageable);

    /**
     * 通过邮箱查询，用户注册检验
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 通过用户名和密码查询
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username,String password);

    /**
     * 通过邮箱和密码查询
     * @param email
     * @param password
     * @return
     */
    User findByEmailAndPassword(String email,String password);

    /**
     * 更新用户信息操作
     * @param id
     * @param username
     * @param email
     * @param avatar
     * @param description
     */
    @Transactional
    @Modifying //通知springData是更新或修改操作
    @Query("update User u set u.username = ?2,u.email = ?3,u.avatar = ?4,u.description = ?5 where u.id = ?1")
    void updateUser(Long id, String username, String email, Integer avatar, String description);

    /**
     * 用户报名比赛
     * @param userId
     * @param competitionId
     */
    @Transactional
    @Modifying
    @Query(value = "insert into user_competition(user_id,competition_id) value (?1,?2)",nativeQuery = true)
    void enrollCompetition(Long userId,Long competitionId);

    /**
     * 用户取消报名比赛
     * @param userId
     * @param competitionId
     */
    @Transactional
    @Modifying
    @Query(value = "delete from user_competition where user_id = ?1 and competition_id = ?2",nativeQuery = true)
    void deleteEnroll(Long userId,Long competitionId);

    /**
     * 查询参赛的用户
     * (value = "select user_id from user_competition where competition_id = ?1",nativeQuery = true)
     * 还有一种sql方法，使用#{}来从参数中获取参数
     * (update User set dept.id = :#{#user.dept.id} where id in (:#{#user.idsList})") void updateBatchUserDept(@Param("user") User user);
     * @param competitionId
     * @return
     */
    @Query("select u from User u inner join u.enrollCompetitions c where c.id = ?1")
    List<User> findEnrollUser(Long competitionId);
}