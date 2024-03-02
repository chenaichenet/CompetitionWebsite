/**
 * FileName: CompetitionController
 * Author:   嘉平十七
 * Date:     2021/1/31 15:42
 * Description: 处理比赛相关请求
 */
package com.hunau.competition.controller;

import com.hunau.competition.domain.Competition;
import com.hunau.competition.domain.User;
import com.hunau.competition.service.CompetitionService;
import com.hunau.competition.service.InformationService;
import com.hunau.competition.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private TypeService typeService;

    /**
     * 设置分类
     * @param model
     */
    private void setType(Model model) {
        model.addAttribute("types", typeService.listType());
    }

    /**
     * 前台跳转到发布比赛，判断是否登录
     *
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/competition/input")
    public String input(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "login";
        }
        setType(model);
        model.addAttribute("competition", new Competition());
        return "release";
    }

    /**
     * 发布比赛
     *
     * @param competition
     * @param attributes
     * @param session
     * @return
     */
    @PostMapping("/competition/input")
    public String post(Competition competition, RedirectAttributes attributes, HttpSession session) {
        competition.setUser((User) session.getAttribute("user"));   //这里采用session中的取值
        competition.setType(typeService.getType(competition.getType().getId()));
        Competition c;

        //处理view为0
        if (competition.getId() == null) {
            c = competitionService.saveCompetition(competition);
        } else {
            c = competitionService.updateCompetition(competition.getId(), competition);
        }

        if (c == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/index";
    }

    /**
     * 比赛详情
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/competition/{id}")
    public String competition(@PathVariable Long id, Model model) {
        setType(model);
        model.addAttribute("competition", competitionService.getAndConvert(id));
        return "competition";
    }

    /**
     * 列出用户的发布的比赛
     *
     * @param userId
     * @param model
     * @return
     */
    @GetMapping("/user/userCompetitions")
    public String userCompetitions(Long userId, Model model) {
        model.addAttribute("competitions", competitionService.listUserCompetition(userId));
        return "userInfo::competitionList";
    }

    /**
     * 列出用户报名的比赛
     *
     * @param userId
     * @param model
     * @return
     */
    @GetMapping("/user/enrollCompetitions")
    public String enrollCompetitions(Long userId, Model model) {
        model.addAttribute("competitions", competitionService.listEnrollCompetition(userId));
        return "userInfo::competitionList";
    }
}