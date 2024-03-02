/**
 * FileName: InformationRepository
 * Author:   嘉平十七
 * Date:     2021/4/5 12:45
 * Description: 处理报名表单
 */
package com.hunau.competition.dao;

import com.hunau.competition.domain.Information;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InformationRepository extends JpaRepository<Information,Long> {

    /**
     * 通过参赛信息id查询具体参赛信息
     * @param information_id
     * @return
     */
    @Query("select i from Information i where i.id = ?1")
    Information getInformationById(Long information_id);

    /**
     * 通过比赛id查询报名信息
     * select i from Information i, Competition c where c.id = ?1，未报名的比赛也会查询到信息，这是因为不是连接查询。
     * @param competition_id
     * @return
     */
    @Query("select i from Information i inner join i.competition c where c.id = ?1")
    Page<Information> getInformationByCompetition(Long competition_id, Pageable pageable);

    /**
     * 通过用户id和比赛id查询报名信息
     * 最开始是使用 select * from information i join user_competition uc on i.id = uc.information_id where uc.user_id = ?1 and uc.competition_id = ?2，查询结果不是所求。
     * 而后使用 select * from information where information.id = (select information_id from user_competition where user_id =?1 and competition_id =?2)，但是后面修改了information实体类，不适用。
     * 而后又是 select i from Information i, User u, Competition c where u.id = ?1 and c.id = ?2，不行
     * @param user_id
     * @param competition_id
     * @return
     */
    @Query("select i from Information i inner join i.user u inner join i.competition c where u.id = ?1 and c.id = ?2")
    Information getInformationByUserAndCompetition(Long user_id, Long competition_id);

    @Query("select i from Information i inner join i.competition c where c.id = ?1")
    List<Information> getInformationByCompetitionId(Long competition_id);

    @Modifying
    @Query("update Information set state = true where id = ?1")
    void checkInformation(Long information_id);
}