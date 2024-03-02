/**
 * FileName: Type
 * Author:   嘉平十七
 * Date:     2021/1/29 16:13
 * Description: 分类实体类
 */
package com.hunau.competition.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "type")
public class Type implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    //分类名
    @NotBlank(message = "分类名称不能为空")
    private String name;
    //比赛列表
    @OneToMany(mappedBy = "type",cascade = CascadeType.REMOVE)
//    @JsonManagedReference(value = "type-competitions")
    private List<Competition> competitions = new ArrayList<>();
    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public Type(){

    }

    public Type(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", typeName='" + name + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String typeName) {
        this.name = typeName;
    }

    public List<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<Competition> competitions) {
        this.competitions = competitions;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}