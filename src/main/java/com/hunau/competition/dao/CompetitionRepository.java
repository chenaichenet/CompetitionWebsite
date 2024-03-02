/**
 * FileName: CompetitionRepository
 * Author:   嘉平十七
 * Date:     2021/1/29 16:36
 * Description:
 */
package com.hunau.competition.dao;

import com.hunau.competition.domain.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition,Long>, JpaSpecificationExecutor<Competition> {

    /**
     * 关键字查询，可以查询用户名、标题、内容中的关键字
     * @param query
     * @param pageable
     * @return
     */
    @Query("select c from Competition c where c.title like ?1 or c.content like ?1 or c.user.username like ?1")
    Page<Competition> findByQuery(String query, Pageable pageable);

    /**
     * 分类查询
     * @param query
     * @param pageable
     * @return
     */
    @Query("select c from Competition c where c.type.name like ?1")
    Page<Competition> findByTypeName(String query,Pageable pageable);

    /**
     * 通过浏览数排序查询热门比赛
     * @param pageable
     * @return
     */
    @Query("select c from Competition c order by c.views desc")
    List<Competition> findTop(Pageable pageable);

    /**
     * 更新浏览次数
     * @param id
     * @return
     */
    @Transactional
    @Modifying
    @Query("update Competition c set c.views = c.views+1 where c.id = ?1")
    int updateViews(Long id);

    /**
     * 通过年份归档
     * @return
     */
    @Query("select function('date_format',c.createTime,'%Y') as year from Competition c group by function('date_format',c.createTime,'%Y') order by function('date_format',c.createTime,'%Y') desc")
    List<String> findGroupYears();

    /**
     * 归档查询
     * @param year
     * @return
     */
    @Query("select c from Competition c where function('date_format',c.createTime,'%Y') = ?1 ")
    List<Competition> findByYear(String year);

    /**
     * 查询用户发布的比赛
     * SQL语句：@Query(value = "select * from competition inner join user u on competition.user_id = u.id where u.id = ?1",nativeQuery = true)
     * @param userId
     * @return
     */
    @Query("select c from Competition c where c.user.id = ?1")
    List<Competition> findByUserId(Long userId);

    /**
     * 查询用户的参赛
     * @Query(value = "select competition_id from user_competition where user_id = ?1",nativeQuery = true)，
     * 不行，这里因为返回的是competition_ID，不是competition实体
     * @param userId
     * @return
     */
    @Query("select c from Competition c inner join c.enrollUsers u where u.id=?1")
    List<Competition> findEnrollCompetition(Long userId);

}
