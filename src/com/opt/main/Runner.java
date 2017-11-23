package com.opt.main;


import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.opt.util.SpringHelper;
import com.opt.util.WebConfig;


public class Runner {
	
	public static void main(String[] args) throws Exception {
		int port = 8080;
		SpringHelper.getAppContext();
		Runner r = new Runner();
		r.startHttpServer(port);
	}

	public void startHttpServer(int port) throws Exception {
		final AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(WebConfig.class);

		HandlerCollection handlers = new HandlerCollection();

		final ServletHolder servletHolder = new ServletHolder(new DispatcherServlet(applicationContext));
		final ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/");
		context.addServlet(servletHolder, "/*");
		final Server server = new Server(port);

		handlers.addHandler(context);

		NCSARequestLog requestLog = new NCSARequestLog();
		requestLog.setFilename("/tmp/test.log");
		requestLog.setFilenameDateFormat("yyyy_MM_dd");
		requestLog.setRetainDays(90);
		requestLog.setAppend(true);
		requestLog.setExtended(true);
		requestLog.setLogCookies(false);
		requestLog.setLogTimeZone("GMT");
		RequestLogHandler requestLogHandler = new RequestLogHandler();
		requestLogHandler.setRequestLog(requestLog);

		handlers.addHandler(requestLogHandler);

		server.setHandler(handlers);
		server.start();
		server.join();
	}

	/*public void start(int port) throws Exception {
		final AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(WebConfig.class);

		HandlerCollection handlers = new HandlerCollection();

		final ServletHolder servletHolder = new ServletHolder(new DispatcherServlet(applicationContext));
		final ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/");
		context.addServlet(servletHolder, "/*");
		final Server server = new Server();

		handlers.addHandler(context);

		NCSARequestLog requestLog = new NCSARequestLog();
		requestLog.setFilename("/opt/xcavate/test.log");
		requestLog.setFilenameDateFormat("yyyy_MM_dd");
		requestLog.setRetainDays(90);
		requestLog.setAppend(true);
		requestLog.setExtended(true);
		requestLog.setLogCookies(false);
		requestLog.setLogTimeZone("GMT");
		RequestLogHandler requestLogHandler = new RequestLogHandler();
		requestLogHandler.setRequestLog(requestLog);

		handlers.addHandler(requestLogHandler);

		server.setHandler(handlers);

		// HTTP Configuration
		HttpConfiguration http_config = new HttpConfiguration();
		http_config.setSecureScheme("https");
		http_config.setSecurePort(port);

		// === jetty-https.xml ===
		// SSL Context Factory
		SslContextFactory sslContextFactory = new SslContextFactory();
		sslContextFactory.setKeyStorePath(System.getProperty(Constants.KEY_STORE_PATH));
		sslContextFactory.setKeyStorePassword(System.getProperty(Constants.KEY_STORE_PASSWORD));
		// sslContextFactory.setKeyManagerPassword("OBF:1u2u1wml1z7s1z7a1wnl1u2g");
		// sslContextFactory.setTrustStorePath(jetty_home +
		// "/../../../jetty-server/src/test/config/etc/keystore");
		// sslContextFactory.setTrustStorePassword("OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4");
		sslContextFactory.setExcludeCipherSuites("SSL_RSA_WITH_DES_CBC_SHA", "SSL_DHE_RSA_WITH_DES_CBC_SHA",
				"SSL_DHE_DSS_WITH_DES_CBC_SHA", "SSL_RSA_EXPORT_WITH_RC4_40_MD5", "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA",
				"SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA");

		// SSL HTTP Configuration
		HttpConfiguration https_config = new HttpConfiguration(http_config);
		https_config.addCustomizer(new SecureRequestCustomizer());

		// SSL Connector
		ServerConnector sslConnector = new ServerConnector(server,
				new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
				new HttpConnectionFactory(https_config));
		sslConnector.setPort(port);
		server.addConnector(sslConnector);
		server.start();
		
		server.join();
		
	}*/

}
