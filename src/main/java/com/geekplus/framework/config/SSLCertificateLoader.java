//package com.geekplus.framework.config;
//
//import org.apache.catalina.connector.Connector;
//import org.apache.coyote.http11.Http11NioProtocol;
//import org.apache.tomcat.util.net.SSLHostConfig;
//import org.apache.tomcat.util.net.SSLHostConfigCertificate;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.context.ApplicationListener;
//import org.springframework.boot.web.context.WebServerInitializedEvent;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.IOException;
//
///**
// * @Author geekplus
// * @Description SSL证书在线热加载，完全通过代码配置，不依赖Spring Boot的server.ssl.*自动配置
// */
//@Component
//public class SSLCertificateLoader implements
//        WebServerFactoryCustomizer<TomcatServletWebServerFactory>, // For initial setup
//        ApplicationListener<WebServerInitializedEvent> {            // For getting TomcatWebServer instance
//
//    private static final Logger log = LoggerFactory.getLogger(SSLCertificateLoader.class);
//
//    private TomcatWebServer tomcatWebServer;
//    private Connector httpsConnector;
//
//    @Value("${server.ssl.custom.port:8443}") // Default to 8443 if not specified
//    private int httpsPort;
//
//    @Value("${server.ssl.custom.key-store}")
//    private String keystorePath;
//
//    @Value("${server.ssl.custom.key-store-password}")
//    private String keystorePassword;
//
//    @Value("${server.ssl.custom.host-name:localhost}") // Default to localhost if not specified
//    private String sslHostName;
//
//    private long lastModified = 0; // Records the last modification time of the keystore file
//
//    /**
//     * This method customizes the TomcatServletWebServerFactory to add our HTTPS connector.
//     * It's called *before* the web server starts.
//     * This replaces Spring Boot's default SSL auto-configuration.
//     */
//    @Override
//    public void customize(TomcatServletWebServerFactory factory) {
//        log.info("Customizing TomcatServletWebServerFactory to add custom HTTPS connector on port {}", httpsPort);
//        try {
//            Connector connector = createHttpsConnector();
//            factory.setPort(8443);
//            factory.setContextPath("/");
//            factory.addAdditionalTomcatConnectors(connector);
//            this.httpsConnector = connector; // Store initial connector
//
//            // Initialize lastModified after the first successful connector creation
//            File keyStoreFile = new File(keystorePath);
//            if (keyStoreFile.exists()) {
//                lastModified = keyStoreFile.lastModified();
//                log.info("Initial keystore '{}' last modified time: {}", keystorePath, lastModified);
//            } else {
//                log.warn("Keystore file '{}' does not exist during initial setup. HTTPS might not work.", keystorePath);
//            }
//
//        } catch (Exception e) {
//            log.error("Failed to create initial HTTPS connector on port {}. Application might not start properly.", httpsPort, e);
//            // Optionally re-throw to prevent application startup with critical config error
//            throw new RuntimeException("Failed to setup initial HTTPS connector", e);
//        }
//    }
//
//    /**
//     * This listener captures the started TomcatWebServer instance.
//     * It's called *after* the web server has initialized and started.
//     */
//    @Override
//    public void onApplicationEvent(WebServerInitializedEvent event) {
//        if (event.getWebServer() instanceof TomcatWebServer) {
//            this.tomcatWebServer = (TomcatWebServer) event.getWebServer();
//            log.info("TomcatWebServer instance captured for SSL hot-reload.");
//            // We already stored the connector in customize, but we can verify it here if needed.
//        }
//    }
//
//    /**
//     * Scheduled task to check for certificate updates.
//     * Runs every minute (60,000 milliseconds).
//     */
//    @Scheduled(fixedDelay = 60000)
//    public void checkForCertUpdates() {
//        if (keystorePath == null || keystorePath.isEmpty()) {
//            log.warn("Keystore path is not configured. Skipping certificate update check.");
//            return;
//        }
//
//        try {
//            File keyStoreFile = new File(keystorePath);
//            if (!keyStoreFile.exists()) {
//                log.warn("Keystore file '{}' not found. Cannot check for updates.", keystorePath);
//                return;
//            }
//
//            long currentModified = keyStoreFile.lastModified();
//            if (currentModified > lastModified) {
//                log.info("Keystore file '{}' has been modified. Initiating SSL certificate reload.", keystorePath);
//                lastModified = currentModified; // Update last modified time BEFORE reload to avoid immediate re-trigger
//                reloadSslContext();
//            } else {
//                // log.debug("Keystore file '{}' not modified. Last check: {}", keystorePath, lastModified);
//            }
//        } catch (Exception e) {
//            log.error("Error during SSL certificate update check.", e);
//        }
//    }
//
//    /**
//     * Reloads the SSL context by replacing the existing HTTPS connector.
//     */
//    public void reloadSslContext() {
//        if (tomcatWebServer == null || httpsConnector == null) {
//            log.error("Tomcat Web Server or HTTPS Connector not initialized. Cannot reload SSL context.");
//            return;
//        }
//
//        try {
//            log.info("Attempting to reload SSL certificate...");
//
//            // 1. Pause and stop the old connector
//            httpsConnector.pause();
//            httpsConnector.stop();
//            tomcatWebServer.getTomcat().getService().removeConnector(httpsConnector);
//            log.info("Old HTTPS connector stopped and removed.");
//
//            // 2. Create a new connector with updated configuration
//            Connector newConnector = createHttpsConnector();
//
//            // 3. Add and start the new connector
//            tomcatWebServer.getTomcat().getService().addConnector(newConnector);
//            newConnector.start();
//            this.httpsConnector = newConnector; // Update reference to the new connector
//            log.info("New HTTPS connector created and started successfully on port {}. SSL certificate hot-updated.", httpsPort);
//
//        } catch (Exception e) {
//            log.error("Failed to hot-update SSL certificate. Rolling back (not implemented for simplicity, requires more complex logic).", e);
//            // In a real production scenario, you might want to try to re-add the old connector
//            // or put the server in a known bad state to alert administrators.
//        }
//    }
//
//    /**
//     * Creates a new Tomcat Connector configured for HTTPS.
//     */
//    private Connector createHttpsConnector() throws IOException {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("https");
//        connector.setPort(httpsPort);
//        connector.setSecure(true);
//        connector.setProperty("SSLEnabled", "true"); // Explicitly enable SSL
//
//        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
//        // The SSLEnabled property on the protocol handler is also good practice
//        protocol.setSSLEnabled(true);
//
//        // Set default SSL host configuration
//        // This is important for Tomcat to pick up the correct SSLHostConfig for the default virtual host
//        protocol.setDefaultSSLHostConfigName(sslHostName);
//
//        SSLHostConfig sslHostConfig = new SSLHostConfig();
//        sslHostConfig.setHostName(sslHostName);
//        // Recommended modern protocols
//        sslHostConfig.setProtocols("TLSv1.2,TLSv1.3");
//
//        SSLHostConfigCertificate cert = new SSLHostConfigCertificate(sslHostConfig, SSLHostConfigCertificate.Type.UNDEFINED);
//        // Type.UNDEFINED lets Tomcat determine the type from the keystore
//
//        File keyStoreFile = new File(keystorePath);
//        if (!keyStoreFile.exists()) {
//            throw new IOException("Keystore file not found: " + keystorePath);
//        }
//        cert.setCertificateKeystoreFile(keyStoreFile.getAbsolutePath());
//        cert.setCertificateKeystorePassword(keystorePassword);
//        cert.setCertificateKeystoreType("PKCS12"); // As per your config
//
//        sslHostConfig.addCertificate(cert);
//        connector.addSslHostConfig(sslHostConfig);
//
//        log.debug("Created HTTPS connector for port {} with keystore '{}'", httpsPort, keystorePath);
//        return connector;
//    }
//}
