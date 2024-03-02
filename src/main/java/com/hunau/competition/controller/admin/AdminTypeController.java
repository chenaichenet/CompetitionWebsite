/**
 * FileName: AdminTypeController
 * Author:   嘉平十七
 * Date:     2021/3/11 13:16
 * Description: 后台分类管理
 */
package com.hunau.competition.controller.admin;

import com.hunau.competition.domain.Type;
import com.hunau.competition.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/admin")
public class AdminTypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 后台分类
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("types",typeService.listType(pageable));
        return "admin/types";
    }

    /**
     * 后台新建分类
     * @param model
     * @return
     */
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type(new Date()));
        return "admin/types-input";
    }

    /**
     * 后台编辑分类
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    /**
     * 后台提交新建分类
     * @Valid注解表示接受校验，校验结果由BindResult接受
     * @param type
     * @param attributes
     * @return
     */
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        //业务方法验证
        if (type1 != null){
            result.rejectValue("name","nameError","不能重复添加分类");
        }

        //有错误返回
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);

        if (t == null){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    /*更新的方法，可以和上面共用*/
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        //业务方法验证
        if (type1 != null){
            result.rejectValue("name","nameError","不能重复添加分类");
        }
        Type t = typeService.updateType(id,type);

        if (t == null){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 后台删除分类
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}