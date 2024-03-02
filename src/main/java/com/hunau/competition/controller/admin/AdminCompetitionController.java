/**
 * FileName: AdminCompetitionController
 * Author:   嘉平十七
 * Date:     2021/3/11 11:06
 * Description: 后台比赛管理
 */
package com.hunau.competition.controller.admin;

import com.hunau.competition.domain.Competition;
import com.hunau.competition.domain.CompetitionQuery;
import com.hunau.competition.domain.User;
import com.hunau.competition.service.CompetitionService;
import com.hunau.competition.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminCompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private TypeService typeService;
    
    /**
     * 设置分类
     * @param model
     */
    private void setType(Model model){
        model.addAttribute("types",typeService.listType());
    }

    /**
     * 后台获取所有比赛并分页显示
     * @param pageable
     * @param competition
     * @param model
     * @return
     */
    @GetMapping("/competitions")
    public String competitions(@PageableDefault(size = 5,sort = {"createTime"},direction = Sort.Direction.DESC) Pageable pageable, CompetitionQuery competition, Model model){
        setType(model);
        model.addAttribute("competitions",competitionService.listCompetition(pageable,competition));
        return "admin/competitions";
    }

    /**
     * 后台比赛页面的搜索功能
     * @param pageable
     * @param competition
     * @param model
     * @return
     */
    @PostMapping("/competitions/search")
    public String search(@PageableDefault(size = 5,sort = {"createTime"},direction = Sort.Direction.DESC) Pageable pageable, CompetitionQuery competition, Model model){
        model.addAttribute("competitions",competitionService.listCompetition(pageable,competition));
        return "admin/competitions::competitionList";//返回片段
    }

    /**
     * 后台查看比赛
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/competition/{id}")
    public String see(@PathVariable Long id, Model model){
        setType(model);
        model.addAttribute("competition", competitionService.getAndConvert(id));
        return "admin/competition";
    }

    /**
     * 后台编辑比赛
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/competition/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        setType(model);
        Competition competition = competitionService.getCompetition(id);
        model.addAttribute("competition",competition);    //拿到tagIds
        return "admin/edit";
    }

    /**
     * 后台删除比赛
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/competition/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        competitionService.deleteCompetition(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/competitions";
    }

    /**
     * 后台跳转到发布比赛
     * @param model
     * @return
     */
    @GetMapping("/competition/input")
    public String input(Model model){
        model.addAttribute("competition",new Competition());
        setType(model);
        return "admin/edit";
    }

    /**
     * 后台发布比赛
     * @param competition
     * @param attributes
     * @param session
     * @return
     */
    @PostMapping("/competition/input")
    public String post(Competition competition, RedirectAttributes attributes, HttpSession session){
        competition.setUser((User) session.getAttribute("adminUser"));
        competition.setType(typeService.getType(competition.getType().getId()));
        Competition c;

        //处理view为0
        if (competition.getId() == null){
            c = competitionService.saveCompetition(competition);
        }else {
            c = competitionService.updateCompetition(competition.getId(),competition);
        }

        if (c == null){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/competitions";
    }

}