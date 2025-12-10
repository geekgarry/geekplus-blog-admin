package com.geekplus.framework.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.boot.web.context.WebServerInitializedEvent;

import javax.annotation.PostConstruct;
import java.io.File;

/**
  * @Author geekplus
  * @Description ssl证书在线热加载
  */
@Slf4j
@Component
public class SSLCertificateReload implements ApplicationListener<WebServerInitializedEvent> {

    private TomcatWebServer tomcatWebServer;
    private Connector httpsConnector;

    @Value("${server.port:8443}") // Default to 8443 if not specified
    private int httpsPort;

    @Value("${server.ssl.host-name:localhost}") // Default to localhost if not specified
    private String sslHostName;

    @Value("${server.ssl.key-store}")
    private String keystorePath;

    @Value("${server.ssl.key-store-password}")
    private String keystorePassword;

    private long lastModified = 0L; // 记录最后修改时间

    private boolean initialized = false;

    @PostConstruct
    public void init() {
        // 仅进行基础初始化，不启动检查线程
        log.info("SSL证书热加载组件初始化完成，等待Tomcat启动...");
        // 开始检查证书更新
        Thread updaterThread = new Thread(this::updateCert);
        updaterThread.start();
    }

    private void updateCert() {
        while (true) {
            try {
                // 检查证书文件的最后修改时间
                File keyStoreFile = new File(keystorePath);
                if (keyStoreFile.exists() && keyStoreFile.lastModified() > lastModified) {
                    lastModified = keyStoreFile.lastModified();
                    reloadSslContext();
                }
                Thread.sleep(60 * 1000); // 每分钟检查一次
                //log.info("每分钟检查一次");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        if (event.getWebServer() instanceof TomcatWebServer && !initialized) {
            this.tomcatWebServer = (TomcatWebServer) event.getWebServer();
            // 找到第一个 HTTPS Connector
            for (Connector connector : tomcatWebServer.getTomcat().getService().findConnectors()) {
                if ("https".equalsIgnoreCase(connector.getScheme())) {
                    this.httpsConnector = connector;
                    log.info("找到HTTPS连接器，端口：{}", httpsPort);
                    log.info("连接器：{}", this.httpsConnector);
                    // 执行初始证书加载
//                    performInitialCertLoad();
                    // 启动定期检查线程
//                    startCertUpdateThread();
                    initialized = true;
                    break;
                }

            }

//            if (httpsConnector != null) {
//                // 执行初始证书加载
//                performInitialCertLoad();
//
//                // 启动定期检查线程
//                startCertUpdateThread();
//
//                initialized = true;
//                log.info("SSL证书热加载功能已完全启动");
//            } else {
//                log.warn("未找到HTTPS连接器，请检查配置");
//            }
        }
    }

    private void performInitialCertLoad() {
        try {
            File keyStoreFile = new File(keystorePath);
            if (keyStoreFile.exists()) {
                lastModified = keyStoreFile.lastModified();
                log.info("SSL证书文件检测成功，路径：{}", keystorePath);
            } else {
                log.error("SSL证书文件不存在，路径：{}", keystorePath);
            }
        } catch (Exception e) {
            log.error("初始证书检查失败", e);
        }
    }

    private void startCertUpdateThread() {
        Thread updaterThread = new Thread(() -> {
            updateCert();
        });
        updaterThread.setName("SSL-Cert-Updater");
        updaterThread.setDaemon(true);
        updaterThread.start();
    }

    public void reloadSslContext() {
        if (tomcatWebServer == null || httpsConnector == null) {
            System.err.println("Tomcat 未初始化完成，无法重新加载证书");
            return;
        }

        try {
            httpsConnector.pause();
            httpsConnector.stop();
            tomcatWebServer.getTomcat().getService().removeConnector(httpsConnector);

            Connector newConnector = createHttpsConnector();
            tomcatWebServer.getTomcat().getService().addConnector(newConnector);
            newConnector.start();

            this.httpsConnector = newConnector;

            log.info("SSL证书已热更新成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connector createHttpsConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("https");
        connector.setPort(httpsPort);
        connector.setSecure(true);
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        protocol.setSSLEnabled(true);
        //设置默认
        protocol.setDefaultSSLHostConfigName(sslHostName);

        SSLHostConfig sslHostConfig = new SSLHostConfig();
        sslHostConfig.setHostName(sslHostName);
        //sslHostConfig.setProtocols("TLSv1.2");

        SSLHostConfigCertificate cert = new SSLHostConfigCertificate(sslHostConfig,
                SSLHostConfigCertificate.Type.EC);
        //证书可以放在固定的证书文件夹里也可以放在项目中,如果放项目中，则将证书放在resources目录下，sslHostConfigCertificate.setCertificateKeystoreFile("cloud.xxx.com.jks");
        cert.setCertificateKeystoreFile(keystorePath);
        //下载jks格式时，里面会带有密码文件
        cert.setCertificateKeystorePassword(keystorePassword);
        cert.setCertificateKeystoreType("PKCS12");
        sslHostConfig.addCertificate(cert);

        connector.addSslHostConfig(sslHostConfig);
        return connector;
    }
}
