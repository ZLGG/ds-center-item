package com.zlg.bs.dscenteritemservice.dao;

import com.zlg.bs.dscenteritemservice.common.ParamConventer;
import com.zlg.bs.dscenteritemservice.es.RowMapper;
import eo.Catalog;
import org.elasticsearch.search.SearchHit;

import javax.sound.midi.Soundbank;
import javax.xml.transform.Source;
import java.util.Map;

public class CatalogMapper implements RowMapper<Catalog> {
    public Catalog mapRow(SearchHit searchHit) {
        Map<String, Object> source = searchHit.getSource();
        Catalog catalog = new Catalog();
        catalog.setId(ParamConventer.convertToLong(source.get("id")));
        catalog.setCode(ParamConventer.convertToLong(source.get("code")));
        catalog.setCreateTime(ParamConventer.convertToString(source.get("create_time")));
        catalog.setDr(ParamConventer.convertToInteger(source.get("dr")));
        catalog.setImgUrl(ParamConventer.convertToString(source.get("img_url")));
        catalog.setLevel(ParamConventer.convertToInteger(source.get("level")));
        catalog.setName(ParamConventer.convertToString(source.get("name")));
        catalog.setParentId(ParamConventer.convertToLong(source.get("parent_id")));
        catalog.setRedirectUrl(ParamConventer.convertToString(source.get("redirect_url")));
        catalog.setSort(ParamConventer.convertToInteger(source.get("sort")));
        catalog.setStatement(ParamConventer.convertToString(source.get("statement")));
        catalog.setStatus(ParamConventer.convertToInteger(source.get("status")));
        catalog.setType(ParamConventer.convertToInteger(source.get("type")));
        catalog.setUpdateTime(ParamConventer.convertToString(source.get("update_time")));
        return catalog;
    }
}
