package com.mksoft.shop.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by chawenming on 2017/5/4.
 */
@ApiModel(value = "微信用户信息对象",description="微信用户信息对象")
public class WxUserDataObject {
    @ApiModelProperty(position = 1, value = "unionid")
    //系统主键
    public String unionid;

    @ApiModelProperty(position = 2, required = true, value = "昵称")
    //舞队名
    public String nickname;

    @ApiModelProperty(position = 1, value = "性别")
    //所在舞队主键
    public int sex;

    @ApiModelProperty(position = 1, value = "省份")
    //成员职责
    public String province;

    @ApiModelProperty(position = 1, value = "城市")
    //成员职责
    public String city;

    @ApiModelProperty(position = 1, value = "国家")
    //成员职责
    public String country;

    @ApiModelProperty(position = 1, value = "用户头像")
    //成员职责
    public String headimgurl;

    @ApiModelProperty(position = 1, value = "用户手机")
    //成员职责
    public String phone;

    @ApiModelProperty(position = 1, value = "用户短信验证码")
    //成员职责
    public String smsValidationCode;

    public Double latitude;
    public Double longitude;
}
