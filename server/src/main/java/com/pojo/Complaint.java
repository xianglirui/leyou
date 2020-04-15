package com.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("投诉实体类")
public class Complaint implements Serializable {
    @ApiModelProperty("id")
    private int id;
    @ApiModelProperty("uid")
    private int uid;
    @ApiModelProperty("adid")
    private int adid;
    @ApiModelProperty("tid")
    private int tid;
    @ApiModelProperty("aid")
    private int aid;
    @ApiModelProperty("oid")
    private int oid;
    @ApiModelProperty("state")
    private int state;//处理状态 0 待处理  1处理中  2已处理
    @ApiModelProperty("additional")
    private String additional;
    @ApiModelProperty("content")
    private String content;
    @ApiModelProperty("types")
    private int types;//1 投诉游记  2 未到账  3 景区纠错  4自检测 封号
    @ApiModelProperty("starttime")
    private String starttime;//客户发起投诉时间
    @ApiModelProperty("dealtime")
    private String dealtime;//处理投诉时间，可根据状态更新
    private String feedback;
}