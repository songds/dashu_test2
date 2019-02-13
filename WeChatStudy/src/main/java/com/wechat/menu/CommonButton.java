package com.wechat.menu;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
**
* 类名: CommonButton </br>
* 包名： com.souvc.weixin.menu
* 描述: 子菜单项 :没有子菜单的菜单项，有可能是二级菜单项，也有可能是不含二级菜单的一级菜单。 </br>
* 开发人员： souvc  </br>
* 创建时间：  2015-12-1 </br>
* 发布版本：V1.0  </br>
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CommonButton extends Button {
    
    private String type;
    private String key;
    private String url;

}