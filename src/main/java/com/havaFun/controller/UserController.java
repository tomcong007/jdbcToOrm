package com.havaFun.controller;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.havaFun.meta.Message;
import com.havaFun.model.TUser;
import com.havaFun.service.TUserService;
import com.havaFun.transaction.SessionParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;
@Controller
public class UserController {
    private TUserService service = TUserService.getInstance();
    @RequestMapping("query")
    @ResponseBody
    public String query(){
        TUser tUser = service.findOne(1);
        System.out.println(JSONObject.toJSONString(tUser, SerializerFeature.PrettyFormat));
        TUser item = new TUser();
        item.setPhone("13419600000");
        List<TUser> list  = service.findItemList(item);
        System.out.println(JSONObject.toJSONString(list, SerializerFeature.PrettyFormat));
        item.greater("id",100);
        item.setState(1);
        item.setSex(0);
        tUser = service.findItem(item);
        item = new TUser();
        System.out.println(JSONObject.toJSONString(tUser, SerializerFeature.PrettyFormat));
        item.setPhone("13500000000");
        list  = service.findItemList(item);
        System.out.println(JSONObject.toJSONString(list, SerializerFeature.PrettyFormat));
        //创建一个事务,插入10条用户信息，主键自增长
        List<SessionParams> sessionParams = new ArrayList<SessionParams>();
        for(int i=0;i<10;i++){
            TUser user = new TUser();
            user.setPhone(String.valueOf(13419600000l+i));
            user.setSex(i%2);
            user.setState(1);
            user.setImgUrl("");
            user.setContent(String.format("content%d",i));
            user.setName(String.format("name%d",i));
            user.setPassword("123456");
            user.setPid(0);
            user.setUsername(String.format("username%d",i));
            //其他字段允许为空
            SessionParams params = service.getSaveItemSession(user);
        }
        Message message = service.saveSessionParam(sessionParams);
        if(message.getCode()==0){
            System.out.println("批量插入成功");
        }else {
            System.out.println("批量插入失败");
        }
        return "query over";
    }

}
