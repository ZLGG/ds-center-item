package eo;

import lombok.Data;

@Data
public class CatalogItem {
    private Long id;
    private Long frontCatalogId;
    private Long itemId;
    private Integer sort;
    private String createTime;
    private String updateTime;
    private Integer dr;
}
