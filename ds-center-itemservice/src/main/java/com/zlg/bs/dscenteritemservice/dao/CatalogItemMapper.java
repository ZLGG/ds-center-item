package com.zlg.bs.dscenteritemservice.dao;

import com.zlg.bs.dscenteritemservice.es.RowMapper;
import eo.CatalogItem;
import org.elasticsearch.search.SearchHit;

public class CatalogItemMapper implements RowMapper<CatalogItem> {
    public CatalogItem mapRow(SearchHit searchHit) {
        return null;
    }
}
