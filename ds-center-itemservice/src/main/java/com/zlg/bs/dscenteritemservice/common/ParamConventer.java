package com.zlg.bs.dscenteritemservice.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
@Slf4j
public class ParamConventer {

    public ParamConventer() {
    }

    public static Date convertToDate(String dateTime, DatePattern pattern) {
        if (pattern == null) {
            log.info("日期错误");
            return null;
        } else {
            Date datetime = null;
            if (StringUtils.isNotBlank(dateTime)) {
                try {
                    datetime = DateUtils.parseDate(dateTime, new String[]{pattern.getPattern()});
                } catch (Exception var4) {
                    log.info("无效的日期格式");
                    return null;
                }
            }

            return datetime;
        }
    }
    public static Long convertToLong(Object param) {
        return (Long)numberConverter(param, Long.class);
    }
    private static Object numberConverter(Object object, Class type) {
        if (object != null && !StringUtils.isBlank(object.toString())) {
            if (Number.class.isAssignableFrom(type)) {
                String param = object.toString();
                if (!NumberUtils.isNumber(param)) {
                    return object;
                } else {
                    return Double.class.isAssignableFrom(type) ? Double.parseDouble(param) : Long.parseLong(param);
                }
            } else {
                return object;
            }
        } else {
            return null;
        }
    }

    public static Integer convertToInteger(Object param)   {
        Long value = (Long)numberConverter(param, Integer.class);
        if (value == null) {
            return null;
        } else if (value <= 2147483647L && value >= -2147483648L) {
            return value.intValue();
        } else {
            log.info("数值超出范围");
            return null;
        }
    }
    public static Double convertToDouble(Object param) {
        return (Double)numberConverter(param, Double.class);
    }

    public static String convertToString(Object param) {
        return param == null ? null : param.toString().trim();
    }
}
