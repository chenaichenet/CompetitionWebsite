/**
 * FileName: Comment
 * Author:   嘉平十七
 * Date:     2021/1/29 16:18
 * Description: 评论实体类
 */
package com.hunau.competition.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comment")
public class Comment implements Serializable {

    //ID
    @Id
    @GeneratedValue
    private Long id;
    //用户
    @ManyToOne
//    @JsonBackReference(value = "user-comments")
    private User user;
    //评论内容
    private String content;
    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    //评论所属比赛
    @ManyToOne
//    @JsonBackReference(value = "competition-comments")
    private Competition competition;
    //回复评论
    @OneToMany(mappedBy = "parentComment")
//    @JsonBackReference(value = "comment-replyComments")
    private List<Comment> replyComments = new ArrayList<>();
    //父评论
    @ManyToOne
//    @JsonBackReference(value = "comment-parentComment")
    private Comment parentComment;
    //是否作者回复
    private boolean adminComment;

    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", competition=" + competition +
                ", replyComments=" + replyComments +
                ", parentComment=" + parentComment +
                ", adminComment=" + adminComment +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }
}