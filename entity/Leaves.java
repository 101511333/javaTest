package com.example.springboot.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


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
 * @since 2023-07-19
 */
@Getter
@Setter
@ApiModel(value = "Leaves对象", description = "")
public class Leaves implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("员工姓名")
    private String employeeName;

    @ApiModelProperty("请假类型")
    private String type;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("请假天数")
    private String days;

    @ApiModelProperty("请假审核人")
    private String auditor;

    @ApiModelProperty("请假状态")
    private String status;

    @ApiModelProperty("请假原因")
    private String reason;


}
