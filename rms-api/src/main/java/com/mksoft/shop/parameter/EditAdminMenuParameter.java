package com.mksoft.shop.parameter;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created by lichu on 2017/6/26.
 */
@Data
@ApiModel("编辑菜单参数")
public class EditAdminMenuParameter {

    private Integer parentPkid;

    private String title;

    private String type;

    private String url;

    private String menuClass;

    private Integer menuOrder;
}
