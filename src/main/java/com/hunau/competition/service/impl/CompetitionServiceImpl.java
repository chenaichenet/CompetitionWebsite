/**
 * FileName: CompetitionServiceImpl
 * Author:   嘉平十七
 * Date:     2021/1/29 9:31
 * Description:
 */
package com.hunau.competition.service.impl;

import com.hunau.competition.dao.CompetitionRepository;
import com.hunau.competition.domain.Competition;
import com.hunau.competition.domain.CompetitionQuery;
import com.hunau.competition.domain.Type;
import com.hunau.competition.service.CompetitionService;
import com.hunau.competition.utils.MarkdownUtils;
import com.hunau.competition.utils.MyBeanUtils;
import com.hunau.competition.utils.NotFoundException;
import com.hunau.competition.utils.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    @Resource
    private CompetitionRepository competitionRepository;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public Competition getCompetition(Long id) {
        return competitionRepository.getOne(id);
    }

    /**
     * 通过id获取竞赛，通过工具类复制对象，用于转换格式并显示
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Competition getAndConvert(Long id) {
        Competition competition = competitionRepository.getOne(id);
        if (competition == null){
            throw new NotFoundException("竞赛不存在");
        }
        Competition newCompetition = new Competition();
        BeanUtils.copyProperties(competition,newCompetition);
        String content = newCompetition.getContent();
        newCompetition.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        competitionRepository.updateViews(id);
        return newCompetition;
    }

    /**
     * 列出所有竞赛并分页
     * @param pageable
     * @return
     */
    @Override
    public Page<Competition> listCompetition(Pageable pageable) {
        return competitionRepository.findAll(pageable);
    }

    /**
     * 通过条件进行查询竞赛
     * @param pageable
     * @param competitionQuery
     * @return
     */
    @Override
    public Page<Competition> listCompetition(Pageable pageable, CompetitionQuery competitionQuery) {
        return competitionRepository.findAll(new Specification<Competition>() {
            @Override
            public Predicate toPredicate(Root<Competition> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                /*title查询*/
                if (!"".equals(competitionQuery.getTitle()) && competitionQuery.getTitle()!=null){
                    predicates.add(criteriaBuilder.like(root.get("title"),"%"+competitionQuery.getTitle()+"%"));
                }
                /*分类查询*/
                if (competitionQuery.getTypeId()!=null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),competitionQuery.getTypeId()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    /**
     * 关键字查询并分页处理
     * @param query 关键字
     * @param pageable 分页
     * @return
     */
    @Override
    public Page<Competition> listCompetitionByQuery(String query, Pageable pageable) {
        return competitionRepository.findByQuery(query,pageable);
    }

    /**
     * 通过分类名进行查询并分页
     * @param typeName 分类名
     * @param pageable 分页处理
     * @return
     */
    @Override
    public Page<Competition> listCompetitionByTypeName(String typeName, Pageable pageable) {
        return competitionRepository.findByTypeName(typeName,pageable);
    }

    /**
     * 列出几个热门竞赛
     * @param size 排名的个数
     * @return
     */
    @Override
    public List<Competition> listCompetitionTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"views");
        Pageable pageable = PageRequest.of(0,size,sort);
        /*todo-chen reids缓存
         * 循环遍历存入redis，序列化存入OK，反序列化取出会StackOverflow
         * 这里涉及到hibernate中的一对多、多对多关系，添加了相关注解但是无效
         * */
        /*List<Competition> hotCompetitions = competitionRepository.findTop(pageable);
        hotCompetitions.forEach(competition -> {
            redisUtils.setList("HotCompetitions",competition.getId().toString());
            redisUtils.expire("HotCompetitions",20);
        });*/
        return competitionRepository.findTop(pageable);
    }

    /**
     * 发布竞赛
     * @param competition
     * @return
     */
    @Transactional
    @Override
    public Competition saveCompetition(Competition competition) {
        if (competition.getId() == null){   //发布竞赛
            competition.setCreateTime(new Date());
            competition.setViews(0);
        }
        return competitionRepository.save(competition);
    }

    /**
     * 归档
     * @return
     */
    @Override
    public Map<String, List<Competition>> archiveCompetition() {
        List<String> years = competitionRepository.findGroupYears();
        Map<String,List<Competition>> map = new HashMap<>();
        for (String year : years){
            map.put(year,competitionRepository.findByYear(year));
        }
        return map;
    }

    /**
     * 统计竞赛数
     * @return
     */
    @Override
    public Long countCompetition() {
        return competitionRepository.count();
    }

    /**
     * 更新竞赛信息
     * @param id
     * @param competition
     * @return
     */
    @Transactional
    @Override
    public Competition updateCompetition(Long id, Competition competition) {
        Competition competitionCopy = competitionRepository.getOne(id);
        if (competitionCopy == null){
            throw new NotFoundException("该竞赛不存在");
        }
        BeanUtils.copyProperties(competition,competitionCopy, MyBeanUtils.getNullPropertyNames(competition));   //过滤掉为空的
        competitionCopy.setCreateTime(new Date());
        return competitionRepository.save(competitionCopy);
    }

    /**
     * 删除竞赛
     * @param id
     */
    @Transactional
    @Override
    public void deleteCompetition(Long id) {
        competitionRepository.deleteById(id);
    }

    /**
     * 列出用户发布的竞赛
     * @param userId
     * @return
     */
    @Override
    public List<Competition> listUserCompetition(Long userId) {
        return competitionRepository.findByUserId(userId);
    }

    /**
     * 列出用户参加的竞赛
     * @param userId
     * @return
     */
    @Override
    public List<Competition> listEnrollCompetition(Long userId) {
        return competitionRepository.findEnrollCompetition(userId);
    }
}