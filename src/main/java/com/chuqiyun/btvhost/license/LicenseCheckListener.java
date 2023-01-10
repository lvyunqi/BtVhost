package com.chuqiyun.btvhost.license;

import com.chuqiyun.btvhost.entity.LicenseVerifyParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author mryunqi
 * @date 2023/1/9
 */
@Component
public class LicenseCheckListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(LicenseCheckListener.class);

    /**
     * 证书subject
     */
    private final String subject = "Chuqi Cloud";

    /**
     * 公钥别称
     */
    private final String publicAlias = "publicCert";

    /**
     * 访问公钥库的密码
     */
    private String storePass = "public_chuqiyun_2531130613";

    /**
     * 证书生成路径
     */
    private String licensePath = System.getProperty("user.dir") + "/key/auth.lic";

    /**
     * 密钥库存储路径
     */
    private String publicKeysStorePath = System.getProperty("user.dir")+"/key/publicCerts.keystore";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //root application context 没有parent
        ApplicationContext context = event.getApplicationContext().getParent();
        if(context == null){
            if(StringUtils.isNotBlank(licensePath)){
                logger.info("++++++++ 开始校验授权 ++++++++");

                LicenseVerifyParam param = new LicenseVerifyParam();
                param.setSubject(subject);
                param.setPublicAlias(publicAlias);
                param.setStorePass(storePass);
                param.setLicensePath(licensePath);
                param.setPublicKeysStorePath(publicKeysStorePath);

                LicenseVerify licenseVerify = new LicenseVerify();
                //安装证书
                licenseVerify.install(param);

                logger.info("++++++++ 授权校验结束 ++++++++");
            }
        }
    }
}
