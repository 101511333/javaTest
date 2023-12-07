package com.example.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Leaves;
import com.example.springboot.entity.User;
import com.example.springboot.service.ILeavesService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2023-07-19
 */
@RestController
@RequestMapping("/leaves")
public class LeavesController {

    @Resource
    private ILeavesService leavesService;


    private final String now = DateUtil.now();



    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Leaves leaves) {
//        if (leaves.getId() == null) {
//            //leaves.setTime(DateUtil.now());
//            //leaves.setUser(TokenUtils.getCurrentUser().getUsername());
//        }
        saveOrUpdate(leaves);
        return Result.success();
    }

    @PostMapping("/leavesAdd")
    public Result leavesAdd(@RequestBody Leaves leaves){
        leaves.setStatus("未审批");
        leaves.setAuditor("暂无审批人");
        saveOrUpdate(leaves);
        return Result.success();
    }

    @PostMapping("/agree")
    public Result agree(@RequestBody Leaves leaves){
        forSetAuditor(leaves);
        leaves.setStatus("已经同意请假申请");
        saveOrUpdate(leaves);
        return Result.success();
    }

    @PostMapping("/refuse")
    public Result refuse(@RequestBody Leaves leaves){
        forSetAuditor(leaves);
        leaves.setStatus("该请假申请被驳回");
        saveOrUpdate(leaves);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        leavesService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        leavesService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(leavesService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(leavesService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Leaves> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("employee_name", name);
        }

        return Result.success(leavesService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("/myPage")
    public Result findMyPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Leaves> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        User currentUser = getUser();
        if (currentUser != null) {
            queryWrapper.eq("employee_name", currentUser.getNickname());
        }
        return Result.success(leavesService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


    /**
    * 导出接口
    */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Leaves> list = leavesService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("Leaves信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

        }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<Leaves> list = reader.readAll(Leaves.class);

        leavesService.saveOrUpdateBatch(list);
        return Result.success();
    }
    //获取当前页面用户信息方法
    private User getUser() {
        return TokenUtils.getCurrentUser();
    }

    //添加或者新增方法
    private void saveOrUpdate(Leaves leaves){
        leavesService.saveOrUpdate(leaves);
    }

    //获取当前用户信息并且设置审核人的方法
    private void forSetAuditor(Leaves leaves){
        User currentUser = getUser();
        if (currentUser != null){
            leaves.setAuditor(currentUser.getNickname());
        }else{
            System.out.println("当前页面用户未登录！");
        }
    }



}

