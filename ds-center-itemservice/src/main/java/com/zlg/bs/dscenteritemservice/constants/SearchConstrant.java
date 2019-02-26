package com.zlg.bs.dscenteritemservice.constants;

/**
 * @(#) SearchConstrant 1.0 2018/2/3
 * <p> ES 查询常量
 * Copyright (c) 2017, YUNXI. All rights reserved.
 * YUNXI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
public class SearchConstrant {

    public  static  final String GROUP_NAME_ITEM = "group_by_item_id" ; // 分组名称

    public  static  final String SUM_NAME_ITEM ="sum_item_num" ; // 统计和名称

    public  static  final String TYPE_NAME_ORDER_ITEM = "tr_order_item";

    public static final String TYPE_NAME_COUPON_MEMBER = "pr_coupon_member";

    public static final String TYPE_NAME_MEMBER_INFO = "mc_member_info";

    public static final String TYPE_NAME_MEMBER_ACCOUNT = "mc_member_account";

    public  static  final  String DR_NAME = "dr";

    public  static  final Integer DR_VALUE = 0;

    public static  final  String TYPE_NAME_CATALOG = "bd_catalog";

    public static final String TYPE_NAME_R_CATALOG_ITEM ="bd_r_catalog_item";

    public static final String FIELD_FRONT_CATALOG_ID = "front_catalog_id";

    public static final String QUERY = "query";

    public static final String POST ="post";


    public  static  final  String FILTER_DR = "filter_dr";

    public  static  final Integer PAGE_SIZE_DEFAULT = 10 ;

    public  static  final Integer PAGE_SIZE_MAX = 10000 ;

    public  static  final Integer PAGE_SIZE_MIN = 1 ;

    public static final String CACHE_GRAY_KEY = "NUSKIN_GRAY_RELEASE";

    public static final Integer CACHE_GRAY_KEY_EXPRIE = 60 * 60 ;
}
