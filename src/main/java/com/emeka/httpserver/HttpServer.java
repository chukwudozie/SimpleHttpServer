package com.emeka.httpserver;

import com.emeka.httpserver.config.Configuration;
import com.emeka.httpserver.config.ConfigurationManager;
import com.emeka.httpserver.core.ServerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    /**
     * Driver class for the Http Server
     */
    public static void main(String[] args) {
//        System.out.println("Initializing Server...");
        LOGGER.info("Initializing Server...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using port: "+conf.getPort());
        LOGGER.info("Using webroot: "+conf.getWebroot());

//        System.out.println("Using port: "+conf.getPort());
//        System.out.println("Using webroot: "+conf.getWebroot());

        try {
            ServerListener serverListener = new ServerListener(conf.getPort(), conf.getWebroot());
            serverListener.start();
        }catch (IOException e){
            e.printStackTrace();
            //  TODO modify catch statement
        }

    }
}
