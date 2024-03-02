/**
 * FileName: Competition
 * Author:   嘉平十七
 * Date:     2021/1/29 16:09
 * Description: 比赛实体类
 */
package com.hunau.competition.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "competition")
public class Competition implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    //比赛标题
    private String title;
    //图片地址
    private String firstPicture;
    //比赛内容
    @Lob    //指定持久属性或字段应作为大对象持久保存到数据库支持的大对象类型
    @Basic(fetch = FetchType.LAZY)  //懒加载
    private String content;
    //比赛所属分类
    @ManyToOne
//    @JsonBackReference("type-competitions")
    private Type type;
    //浏览次数
    private Integer views;
    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    //比赛发布人
    @ManyToOne
//    @JsonBackReference(value = "user-competitions")
    private User user;
    //评论
    @OneToMany(mappedBy = "competition",cascade = CascadeType.REMOVE)
//    @JsonManagedReference(value = "competition-comments")
    private List<Comment> comments = new ArrayList<>();
    //参赛用户
    @ManyToMany(mappedBy = "enrollCompetitions",cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<User> enrollUsers = new HashSet<>();
    //摘要描述
    private String description;

    public Competition() {
    }

    @Override
    /*这里不能写多对多类型的，否则会引出堆栈溢出的问题，这是因为循环嵌套引用造成的*/
    public String toString() {
        return "Competition{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", views=" + views +
                ", createTime=" + createTime +
                ", user=" + user +
                ", comments=" + comments +
                ", description='" + description + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<User> getEnrollUsers() {
        return enrollUsers;
    }

    public void setEnrollUsers(Set<User> enrollUsers) {
        this.enrollUsers = enrollUsers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}