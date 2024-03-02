/**
 * FileName: Information
 * Author:   嘉平十七
 * Date:     2021/3/31 18:09
 * Description: 报名信息实体类
 */
package com.hunau.competition.domain;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name = "information")
public class Information {

    @Id
    @GeneratedValue
    private Long id;

    //通过状态
    private boolean state;

    //对应的用户id
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    //对应的比赛id
    @ManyToOne(fetch = FetchType.EAGER)
    private Competition competition;
    //姓名
    private String name;
    //性别
    private String gender;
    //身份证
    private Long idCard;
    //手机号
    private Long phone;
    //邮箱
    private String email;
    //学校
    private String school;
    //学号
    private Long student_id;
    //导师
    private String teacher;
    //作品名称
    private String title;
    //简介
    private String content;
    //文件
    private String file;

    public Information() {
    }

    @Override
    public String toString() {
        return "Information{" +
                "id=" + id +
                ", state=" + state +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", idCard=" + idCard +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", school='" + school + '\'' +
                ", student_id=" + student_id +
                ", teacher='" + teacher + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", file=" + file +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getIdCard() {
        return idCard;
    }

    public void setIdCard(Long idCard) {
        this.idCard = idCard;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}