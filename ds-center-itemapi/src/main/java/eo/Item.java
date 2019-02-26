package eo;

import lombok.Data;

@Data
public class Item {
    private Long id;
    private String code;
    private String name;
    private String alias;
    private Integer status;
    private Integer auditStatus;
    private String title;
    private String statement;
    private String publishDate;
    private Integer salesCount;
    private Double salePrice;
    private Double sellPrice;
    private Double marketPrice;
    private Double freightInsuranceFee;
    private Integer isSellerFair;
    private Integer isReturnNoReason;
    private Integer allowReturnDay;
    private String createTime;
    private String updateTime;
    private Integer dr;

}
