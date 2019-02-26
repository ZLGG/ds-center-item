package com.zlg.bs.dscenteritemservice.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.edas.acm.ConfigService;
import com.alibaba.fastjson.JSONObject;
import com.zlg.bs.dscenteritemservice.config.bean.DataSourceVo;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.zlg.bs.dscenteritemservice.mapper"},sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {
    @Bean(name = "datasource",destroyMethod = "close")
    public DruidDataSource dataSource() throws Exception{
        String config = ConfigService.getConfig("ds.zlg.item.datasourcevo", "zlg", 3000);

        DataSourceVo dataSourceVo = JSONObject.parseObject(config, DataSourceVo.class);

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dataSourceVo.getDriverClassName());
        dataSource.setUrl(dataSourceVo.getJdbcUrl());
        dataSource.setUsername(dataSourceVo.getJdbcUserName());
        dataSource.setPassword(dataSourceVo.getJdbcUserPassword());
        dataSource.setMaxActive(dataSourceVo.getMaxActive());
        dataSource.setValidationQuery(dataSourceVo.getValidationQuery());
        dataSource.setInitialSize(dataSourceVo.getInitialSize());
        dataSource.setMinIdle(dataSourceVo.getMinIdle());
        dataSource.setMaxWait(dataSourceVo.getMaxWait());
        return dataSource;
    }
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlsessionFactoryBean(@Qualifier("datasource") DruidDataSource dataSource) throws Exception{
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        config.setLogImpl(Slf4jImpl.class);
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfiguration(config);

        return sqlSessionFactoryBean.getObject();
    }

}
