/**
 * FileName: InformationController
 * Author:   嘉平十七
 * Date:     2021/4/21 16:46
 * Description: 处理报名信息
 */
package com.hunau.competition.controller.admin;

import com.hunau.competition.domain.Information;
import com.hunau.competition.service.CompetitionService;
import com.hunau.competition.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminInformationController {

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private InformationService informationService;

    /**
     * 通过比赛id查询所有参赛用户信息
     * @param competition_id
     * @param model
     * @return
     */
    @RequestMapping("/information/getInformationList")
    public String getInformationList(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.ASC) Pageable pageable,
                                     @RequestParam Long competition_id, Model model){
        model.addAttribute("competition",competitionService.getCompetition(competition_id));
        model.addAttribute("informationList",informationService.getInformationByCompetitionId(competition_id,pageable));
        return "admin/informations";
    }

    /**
     * 通过参赛信息id查询具体参赛信息
     * @param information_id
     * @param competition_id
     * @param model
     * @return
     */
    @RequestMapping("/information/getInformation")
    public String getInformation(@RequestParam Long information_id, @RequestParam Long competition_id, Model model){
        Information informationById = informationService.getInformationById(information_id);
        model.addAttribute("information",informationById);
        model.addAttribute("competition_id",competition_id);
        return "admin/information";
    }

    /**
     * 具体参赛信息审核
     * @param information_id
     * @param competition_id
     * @param model
     * @return
     */
    @RequestMapping("/information/checkInformation")
    public String checkInformation(@RequestParam Long information_id,@RequestParam Long competition_id, Model model){
        informationService.checkInformation(information_id);
        model.addAttribute("message",true);
        System.out.println("成功");
        System.out.println(competition_id);
        return "redirect:/admin/information/getInformationList?competition_id="+competition_id;
    }

    @RequestMapping("/information/outInformations")
    public String outInformations(Long competition_id){
        //todo-chen 获取List<Information>后调用PDF工具类输出
        informationService.getInformationListByCompetitionId(competition_id);
        return null;
    }

    @RequestMapping("/information/outInformation")
    public String outInformation(Long information_id){
        return null;
    }


}