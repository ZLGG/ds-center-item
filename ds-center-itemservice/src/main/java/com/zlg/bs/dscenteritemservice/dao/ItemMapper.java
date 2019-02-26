package com.zlg.bs.dscenteritemservice.dao;

import com.zlg.bs.dscenteritemservice.common.ParamConventer;
import com.zlg.bs.dscenteritemservice.es.RowMapper;
import eo.Item;
import org.elasticsearch.search.SearchHit;

import java.util.Map;

public class ItemMapper implements RowMapper<Item> {
    public Item mapRow(SearchHit searchHit) {
        Item item = new Item();
        Map<String, Object> source = searchHit.getSource();
        item.setId(ParamConventer.convertToLong(source.get("id")));
        item.setAlias(ParamConventer.convertToString(source.get("alias")));
        item.setAllowReturnDay(ParamConventer.convertToInteger(source.get("allow_return_day")));
        item.setAuditStatus(ParamConventer.convertToInteger(source.get("audit_status")));
        item.setCode(ParamConventer.convertToString(source.get("code")));
        item.setCreateTime(ParamConventer.convertToString(source.get("create_time")));
        item.setDr(ParamConventer.convertToInteger(source.get("dr")));
        item.setFreightInsuranceFee(ParamConventer.convertToDouble(source.get("freight_insurance_fee")));
        item.setIsReturnNoReason(ParamConventer.convertToInteger(source.get("is_return_no_reason")));
        item.setIsSellerFair(ParamConventer.convertToInteger(source.get("is_seller_fair")));
        item.setMarketPrice(ParamConventer.convertToDouble(source.get("market_price")));
        item.setName(ParamConventer.convertToString(source.get("name")));
        item.setPublishDate(ParamConventer.convertToString(source.get("publish_dae")));
        item.setSalePrice(ParamConventer.convertToDouble(source.get("sale_price")));
        item.setSalesCount(ParamConventer.convertToInteger(source.get("sales_count")));
        item.setSellPrice(ParamConventer.convertToDouble(source.get("sell_price")));
        item.setStatement(ParamConventer.convertToString(source.get("statement")));
        item.setStatus(ParamConventer.convertToInteger(source.get("status")));
        item.setTitle(ParamConventer.convertToString(source.get("title")));
        item.setUpdateTime(ParamConventer.convertToString(source.get("update_time")));
        return item;
    }
}
