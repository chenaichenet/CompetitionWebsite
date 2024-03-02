/**
 * FileName: User
 * Author:   嘉平十七
 * Date:     2021/1/29 16:02
 * Description: 用户实体类
 */
package com.hunau.competition.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue //自增
    private Long id;
    //用户名
    private String username;
    //密码
    private String password;
    //头像
    private Integer avatar;
    //邮箱
    private String email;
    //描述
    private String description;
    //创建时间
    @Temporal(TemporalType.TIMESTAMP) //时间戳
    private Date createTime;
    //发布的比赛，fetch = FetchType.EAGER是立刻加载
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    @Cascade(value = {org.hibernate.annotations.CascadeType.REMOVE})
//    @JsonManagedReference(value = "user-competitions")
    /*默认是懒加载，_fragment中58行会报错：无法懒惰地初始化角色集合*/
    private List<Competition> competitions = new ArrayList<>();
    //报名的比赛
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
//    @JsonBackReference(value = "user-enrollCompetitions")
    @JoinTable(name = "user_competition",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "competition_id"))
    private Set<Competition> enrollCompetitions = new HashSet<>();
    //是否是管理人员
    private Integer role;

    public User() {
    }

    public User(String username, String password, Integer avatar, Integer role, String email, Date createTime) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
        this.email = email;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", admin=" + role +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<Competition> competitions) {
        this.competitions = competitions;
    }

    public Set<Competition> getEnrollCompetitions() {
        return enrollCompetitions;
    }

    public void setEnrollCompetitions(Set<Competition> enrollCompetitions) {
        this.enrollCompetitions = enrollCompetitions;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}