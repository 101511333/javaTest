package com.example.springboot.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2023-07-31
 */
@Getter
@Setter
@ApiModel(value = "Attendance对象", description = "")
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("出勤记录的编号")
    private Integer id;

    @ApiModelProperty("员工姓名")
    private String employeeName;

    @ApiModelProperty("上班时间")
    private String startTime;

    @ApiModelProperty("下班时间")
    private String endTime;

    @ApiModelProperty("工时")
    private String hoursWorked;

    @ApiModelProperty("判断是否迟到")
    private String late;

    @ApiModelProperty("判断是否早退")
    private String earlyLate;

    @ApiModelProperty("加班情况")
    private String overtime;

    @ApiModelProperty("判断是否缺勤")
    private String absentType;

    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("记录者")
    private String recorder;


}
