package com.itmayiedu.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class BaseEntity {

    //主键
    private Long id;
    //创建时间
    private Timestamp created;
    /**
     * 修改时间
     */
    private Timestamp updated;


}
