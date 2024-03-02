/**
 * FileName: CommentService
 * Author:   嘉平十七
 * Date:     2021/1/29 9:55
 * Description: 评论服务类
 */
package com.hunau.competition.service;

import com.hunau.competition.domain.Comment;

import java.util.List;

public interface CommentService {

    //通过比赛id查询对应的评论列表
    List<Comment> listCommentByCompetitionId(Long competitionId);

    //保存评论
    Comment saveComment(Comment comment);
}
