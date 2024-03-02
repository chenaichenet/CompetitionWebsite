/**
 * FileName: InformationServiceImpl
 * Author:   嘉平十七
 * Date:     2021/4/5 12:40
 * Description:
 */
package com.hunau.competition.service.impl;

import com.hunau.competition.dao.InformationRepository;
import com.hunau.competition.domain.Information;
import com.hunau.competition.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InformationServiceImpl implements InformationService {

    @Autowired
    private InformationRepository informationRepository;

    @Override
    public List<Information> getInformation() {
        return null;
    }

    @Override
    public Information addInformation(Information information) {
        Information informationState = informationRepository.getInformationByUserAndCompetition(information.getUser().getId(), information.getCompetition().getId());
        System.err.println("service层"+information);
        System.err.println("service层State"+informationState);
        if (informationState != null){
            return null;
        }else{
            return informationRepository.save(information);
        }
    }

    /**
     * 通过参赛信息id查询参赛信息
     * @param information_id
     * @return
     */
    @Override
    public Information getInformationById(Long information_id) {
        return informationRepository.getInformationById(information_id);
    }

    /**
     * 通过比赛id查询参赛信息并分页
     * @param pageable
     * @param competition_id
     * @return
     */
    @Override
    public Page<Information> getInformationByCompetitionId(Long competition_id,Pageable pageable) {
        return informationRepository.getInformationByCompetition(competition_id,pageable);
    }

    @Override
    public List<Information> getInformationListByCompetitionId(Long competition_id) {
        return informationRepository.getInformationByCompetitionId(competition_id);
    }

    /**
     * 审核参赛信息
     * @param information_id
     */
    @Override
    @Transactional
    public void checkInformation(Long information_id) {
        informationRepository.checkInformation(information_id);
    }


}