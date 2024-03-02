/**
 * FileName: IndexController
 * Author:   嘉平十七
 * Date:     2021/1/22 11:09
 * Description: 前台首页的所有请求的处理类
 */
package com.hunau.competition.controller;

import com.hunau.competition.service.CompetitionService;
import com.hunau.competition.service.TypeService;
import com.hunau.competition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private UserService userService;

    @Autowired
    private TypeService typeService;

    /**
     * 首页定位
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping({"/","/index"})
    public String index(@PageableDefault(size = 5,sort = {"createTime"},direction = Sort.Direction.DESC)Pageable pageable, Model model){
        model.addAttribute("types",typeService.listType(pageable));
        model.addAttribute("competitions",competitionService.listCompetition(pageable));//查询所有比赛并分页
        model.addAttribute("hotTypes",typeService.listTypeTop(5));  //前6个分类
        model.addAttribute("hotCompetitions",competitionService.listCompetitionTop(5)); //前6个比赛
        return "index";
    }

    /**
     * 后台首页定位
     * @return
     */
    @GetMapping("/admin/index")
    public String index_admin(){
        return "admin/index";
    }

    /**
     * 归档页面
     * @param model
     * @return
     */
    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("archiveMap",competitionService.archiveCompetition());
        model.addAttribute("competitionCount",competitionService.countCompetition());
        return "archives";
    }

    /**
     * 关于页面
     * @param model
     * @return
     */
    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("types",typeService.listType());
        return "about";
    }

    /**
     * 关键字查询
     * @param pageable
     * @param query
     * @param model
     * @return
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 5,sort = {"createTime"},direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String query, Model model){
        model.addAttribute("types",typeService.listType(pageable));
        model.addAttribute("users",userService.findByQuery("%"+query+"%",pageable));
        model.addAttribute("competitions",competitionService.listCompetitionByQuery("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    /**
     * 分类查询
     * @param pageable
     * @param typename
     * @param model
     * @return
     */
    @GetMapping("/search/{typename}")
    public String searchByTypename(@PageableDefault(size = 5,sort = {"createTime"},direction = Sort.Direction.DESC) Pageable pageable, @PathVariable String typename, Model model){
        model.addAttribute("types",typeService.listType(pageable));
        model.addAttribute("competitions",competitionService.listCompetitionByTypeName("%"+typename+"%",pageable));
        return "search";
    }
}