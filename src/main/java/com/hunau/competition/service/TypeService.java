/**
 * FileName: TypeService
 * Author:   嘉平十七
 * Date:     2021/1/28 19:04
 * Description: 分类服务
 */
package com.hunau.competition.service;

import com.hunau.competition.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    //保存分类
    Type saveType(Type type);

    //通过id获取分类
    Type getType(Long id);

    //通过分类名称获取分类
    Type getTypeByName(String typeName);

    //列出所有分类
    List<Type> listType();

    //列出所有分类并分页处理
    Page<Type> listType(Pageable pageable);

    //列出热门分类
    List<Type> listTypeTop(Integer size);

    //更新分类
    Type updateType(Long id,Type type);

    //删除分类
    void deleteType(Long id);
}
