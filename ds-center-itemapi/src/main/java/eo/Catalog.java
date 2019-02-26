package eo;

import lombok.Data;

@Data
public class Catalog {
    private Long id;
    private Integer type;
    private Long parentId;
    private String name;
    private Long code;
    private Integer status;
    private String statement;
    private Integer sort;
    private Integer level;
    private String redirectUrl;
    private String imgUrl;
    private String createTime;
    private String updateTime;
    private Integer dr;
}
