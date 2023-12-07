package com.example.springboot.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Department;
import com.example.springboot.entity.User;
import com.example.springboot.service.IDepartmentService;
import com.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: 何浩然
 * @date: 2023 - 10 - 28 15:11
 **/
@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IDepartmentService departmentService;

    //折线图
    @GetMapping("/getUserEcharts")
    public Result getUserEcharts(){
        List<User> users = userService.list();
        //获取到不重复的日期
        Set<String> dateSet = users
                .stream()
                .map(user -> TimeToString(user.getCreateTime()).substring(0, 9))
                .collect(Collectors.toSet());

        dateSet.stream().limit(5).sorted(Comparator.naturalOrder());

        List<Dict> lineList = new ArrayList<>();
        for (String date : dateSet) {
            //根据日期获取到用户数量
            int userCount = (int) users
                    .stream()
                    .filter(user -> TimeToString(user.getCreateTime()).substring(0, 9).equals(date))
                    .count();

            //创建一个Dict对象（类似于Hashmap）
            Dict dict = new Dict();
            dict.set("date",date).set("userCount",userCount);
            lineList.add(dict);
        }

        return Result.success(lineList);
    }

    //饼状图
    @GetMapping("/getDepartmentData")
    public Result getDepartmentData(){
        List<Department> departments = departmentService.list();
        List<Dict> pieList = new ArrayList<>();
        for (Department department : departments) {
            String flag = department.getFlag();
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("department",flag);
            List<User> usersList = userService.list(queryWrapper);
            int userCount = usersList.size();
            Dict dict = new Dict();
            dict.set("userCount",userCount).set("departmentName",department.getDepartmentName());
            pieList.add(dict);
        }
        return Result.success(pieList);
    }

    //日期转换工具
    private static String TimeToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }


}
