package com.example.springboot.entity;

import java.io.Serializable;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2023-07-13
 */
@Getter
@Setter
@ApiModel(value = "Department对象", description = "")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("部门id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("部门名称")
    private String departmentName;

    @ApiModelProperty("部门描述")
    private String departmentDesc;

    @ApiModelProperty("部门员工")
    private String departmentEmployee;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("部门唯一标识")
    private String flag;

}
