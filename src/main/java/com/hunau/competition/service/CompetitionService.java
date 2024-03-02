/**
 * FileName: CompetitionService
 * Author:   嘉平十七
 * Date:     2021/1/29 9:23
 * Description: 比赛服务类
 */
package com.hunau.competition.service;

import com.hunau.competition.domain.Competition;
import com.hunau.competition.domain.CompetitionQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CompetitionService {

    //通过id获取比赛
    Competition getCompetition(Long id);

    //通过id获取比赛，在同通过工具类复制对象，用于格式转换并显示
    Competition getAndConvert(Long id);

    Page<Competition> listCompetition(Pageable pageable);

    //通过条件查询比赛
    Page<Competition> listCompetition(Pageable pageable, CompetitionQuery competitionQuery);

    //通过关键字查询
    Page<Competition> listCompetitionByQuery(String query,Pageable pageable);

    //通过分类名进行查询
    Page<Competition> listCompetitionByTypeName(String typeName,Pageable pageable);

    //通过浏览数查询
    List<Competition> listCompetitionTop(Integer size);

    //发布保存比赛
    Competition saveCompetition(Competition competition);

    //归档处理
    Map<String, List<Competition>> archiveCompetition();

    //计数
    Long countCompetition();

    //更新比赛信息
    Competition updateCompetition(Long id,Competition competition);

    //通过id删除比赛
    void deleteCompetition(Long id);

    //通过用户ID查询发布的比赛
    List<Competition> listUserCompetition(Long userId);

    //查询用户的参赛
    List<Competition> listEnrollCompetition(Long userId);

}
