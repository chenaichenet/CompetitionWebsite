/**
 * FileName: CommentController
 * Author:   嘉平十七
 * Date:     2021/1/28 10:31
 * Description: 前台比赛详情中的评论请求处理
 */
package com.hunau.competition.controller;

import com.hunau.competition.domain.Comment;
import com.hunau.competition.domain.User;
import com.hunau.competition.service.CommentService;
import com.hunau.competition.service.CompetitionService;
import com.hunau.competition.utils.BadWordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private CommentService commentService;

    /**
     * 通过比赛Id获取对应的评论列表
     * @param competitionId 比赛ID
     * @param model
     * @return
     */
    @GetMapping("/comments/{competitionId}")
    public String comments(@PathVariable Long competitionId, Model model){
        model.addAttribute("comments",commentService.listCommentByCompetitionId(competitionId));
        return "competition :: commentList";
    }

    /**
     * 发布评论
     * @param comment
     * @param session
     * @return
     */
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long competitionId = comment.getCompetition().getId();
        comment.setCompetition(competitionService.getCompetition(competitionId));
        User user = (User) session.getAttribute("user"); //当前登录用户
        if (user == null){
            return "login";
        }else if (user.getId() == comment.getCompetition().getUser().getId()){
            comment.setUser(user);
            comment.setAdminComment(true);
        }else {
            comment.setUser(user);
        }
        String content = BadWordUtils.filter(comment.getContent());
        comment.setContent(content);
        commentService.saveComment(comment);
        return "redirect:/comments/"+competitionId;
    }
}