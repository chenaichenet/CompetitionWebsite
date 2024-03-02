/**
 * FileName: TypeRepository
 * Author:   嘉平十七
 * Date:     2021/1/29 16:35
 * Description:
 */
package com.hunau.competition.dao;

import com.hunau.competition.domain.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {

    /**
     * 通过分类名查询
     * @param typeName
     * @return
     */
    Type findByName(String typeName);

    /**
     * 查询分类列表
     * @param pageable
     * @return
     */
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
