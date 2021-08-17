package com.adk.vo.params;

import lombok.Data;

/*
    这是用来存放pageparams参数的一个类
    vo包params用来存放post的参数体
 */
@Data
public class PageParams {
    //这个是用于分页插件的参数体，默认为第一页 每页十条数据
    private int page=1;
    private int pagesize=10;
}
