package com.zlg.bs.dscenteritemservice.config;

import com.alibaba.edas.acm.ConfigService;
import com.alibaba.fastjson.JSONObject;
import com.zlg.bs.dscenteritemservice.common.AppContext;
import com.zlg.bs.dscenteritemservice.common.Constants;
import com.zlg.bs.dscenteritemservice.config.bean.EsRegistryVo;
import com.zlg.bs.dscenteritemservice.es.ESClient;
import com.zlg.bs.dscenteritemservice.es.ESTemplate;
import com.zlg.bs.dscenteritemservice.vo.IndexRegistryVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class EsConfig {
    private static final Logger logger = LoggerFactory.getLogger(EsConfig.class);

    @Bean
    public AppContext appContext() throws Exception{
        //IndexRegistryVo registryVo = searchRegistryService.getObject(Constants.INDEX_REGISTRYVO, IndexRegistryVo.class);
        String config = ConfigService.getConfig(Constants.INDEX_REGISTRYVO, "zlg", 3000L);
        IndexRegistryVo registryVo = JSONObject.parseObject(config, IndexRegistryVo.class);

        AppContext appContext = new AppContext();
        appContext.setItem(registryVo.getItem());
        appContext.setOrder(registryVo.getTrade());
        appContext.setMember(registryVo.getMember());
        appContext.setUserItem(registryVo.getMemberItem());
        appContext.setInv(registryVo.getInv());
        appContext.setUser(registryVo.getUser());
        return appContext;
    }

    @Bean
    public ESClient esClient() throws Exception{
        /*EsRegistryVo registryVo = searchRegistryService.getObject(Constants.ELASTICSEARCH_REGISTRYVO,
                EsRegistryVo.class);*/
        String config = ConfigService.getConfig(Constants.ELASTICSEARCH_REGISTRYVO, "zlg", 3000L);
        EsRegistryVo registryVo = JSONObject.parseObject(config, EsRegistryVo.class);
        logger.info("es cluster is {},es host is {} es port is {} es sniff is {} es appkey is {} es appSecret is {}", registryVo.getCluster(),
                registryVo.getHost(), registryVo.getPort(), registryVo.getSniff(),registryVo.getAppKey(),registryVo.getAppSecret());
        return new ESClient(registryVo.getCluster(), registryVo.getAppKey(), registryVo.getAppSecret(),
                registryVo.getHost(), registryVo.getPort(), registryVo.getSniff());
    }

    @Bean
    @Scope(value = "prototype")
    public ESTemplate esTemplate() throws Exception {
        logger.info("Create a ESTemplate bean");
        return new ESTemplate(esClient());
    }
}
