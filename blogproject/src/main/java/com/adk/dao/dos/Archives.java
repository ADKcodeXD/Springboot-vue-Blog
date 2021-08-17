package com.adk.dao.dos;

import lombok.Data;

/**
 * 这个类中存放的类都是从数据库中查询到的数据
 * 但却不需要持久化
 * 也就是临时的数据对象
 */
@Data
public class Archives {
    private Integer year;
    private Integer month;
    private Long count;
}
