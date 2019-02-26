package com.zlg.bs.dscenteritemservice.mapper;

import eo.Catalog;
import eo.CatalogItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ItemMapper {
    //查询所有一级类目
    @Select("select * from bd_catalog where dr = 0 and parent_id = 0")
    List<Catalog> selectCatalog();

    //根据一级类目查询子类目
    @Select("select * from bd_catalog where dr = 0 and parent_id = #{parentId}")
    List<Catalog> selectChiledCatalog(@Param("parentId") int parentId);

    //根据类目查询关联商品
    @Select("select * from bd_r_catalog_item where dr = 0 and front_catalog_id = #{catalogId}")
    List<CatalogItem> selectCatalogItem(@Param("catalogId") int catalogId);




}
