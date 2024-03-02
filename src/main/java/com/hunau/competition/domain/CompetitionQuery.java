/**
 * FileName: CompetitionQuery
 * Author:   嘉平十七
 * Date:     2021/1/29 16:29
 * Description: 比赛查询
 */
package com.hunau.competition.domain;

public class CompetitionQuery {

    private String title;

    private Long typeId;

    public CompetitionQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}