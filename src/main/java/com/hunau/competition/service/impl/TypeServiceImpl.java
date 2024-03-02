/**
 * FileName: TypeServiceImpl
 * Author:   嘉平十七
 * Date:     2021/1/28 19:11
 * Description:
 */
package com.hunau.competition.service.impl;

import com.hunau.competition.dao.TypeRepository;
import com.hunau.competition.domain.Type;
import com.hunau.competition.service.TypeService;
import com.hunau.competition.utils.NotFoundException;
import com.hunau.competition.utils.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private RedisUtils redisUtil;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Override
    public Type getTypeByName(String typeName) {
        return typeRepository.findByName(typeName);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"competitions.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        /*todo-chen redis缓存
        * 循环遍历存入redis，序列化存入OK，反序列化取出会StackOverflow
        * 这里涉及到hibernate中的一对多、多对多关系，添加了相关注解但是无效
        * */
/*        List<Type> hotTypeList = typeRepository.findTop(pageable);
        hotTypeList.forEach(type -> {
            if (!redisUtil.hasKey("HotType")){
                redisUtil.setList("HotType",type.getName());
                redisUtil.expire("HotType",2000);
            }
        });*/
        return typeRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.getOne(id);
        if (t == null){
            throw new NotFoundException("不存在该分类");
        }
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
         typeRepository.deleteById(id);
    }
}