package com.meituan.controller;
import com.meituan.model.${pojoName};
import com.meituan.com.meituan.service.${pojoName}Service;
import com.meituan.util.Message;
import com.meituan.util.ViewUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
@Controller
@RequestMapping("${simpleName}")
public class ${pojoName}Controller {
private ${pojoName}Service com.meituan.service = ${pojoName}Service.getInstance();
@RequestMapping("list")
public ModelAndView list(){
return ViewUtil.successModelAndView("${simpleName}/list");
}
@RequestMapping("itemsList")
@ResponseBody
public ModelMap itemsList(){
List<${pojoName}> list = com.meituan.service.findItemList();
    return ViewUtil.successModelObj(list);
    }

    @RequestMapping(value="save",method= RequestMethod.POST)
    @ResponseBody
    public Message save(${pojoName} item){
    return com.meituan.service.saveItem(item);
    }

    @RequestMapping(value="update",method=RequestMethod.POST)
    @ResponseBody
    public Message update(${pojoName} item){
    return com.meituan.service.updateItem(item);
    }
    }
