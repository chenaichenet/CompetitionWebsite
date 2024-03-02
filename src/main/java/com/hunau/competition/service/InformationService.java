/**
 * FileName: InformationService
 * Author:   嘉平十七
 * Date:     2021/4/5 11:44
 * Description:
 */
package com.hunau.competition.service;

import com.hunau.competition.domain.Information;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InformationService {

    //列出所有的参赛信息
    List<Information> getInformation();

    //新增报名信息
    Information addInformation(Information information);

    //通过信息id获取具体信息
    Information getInformationById(Long information_id);

    //通过比赛id获取报名信息
    Page<Information> getInformationByCompetitionId(Long competition_id, Pageable pageable);

    //通过用户id和比赛id获取报名信息
    List<Information> getInformationListByCompetitionId(Long competition_id);

    //审核参赛信息
    void checkInformation(Long information_id);
}
