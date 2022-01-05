package com.emeka.httpserver.config;

import com.emeka.httpserver.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager myConfig;
    private static Configuration myCurrentConfiguration;

    public ConfigurationManager() {
    }

    /**
     * This creates a new instance of the class if none is existing
     */
    public static ConfigurationManager getInstance(){
        if(myConfig == null) myConfig = new ConfigurationManager();
        return myConfig;
    }

    /**
     * Used to load configuration file by the file provided
     * @param filePath
     */
    public void loadConfigurationFile(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        try {
            while ((i = fileReader.read()) != -1) {
                sb.append((char) i);
            }
        }catch (IOException e){
            throw new HttpConfigurationException(e);
        }
        JsonNode config = null;
        try {
            config = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the configuration file",e);
        }
        try {
            myCurrentConfiguration = Json.fromNode(config,Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the configuration file, internal",e);
        }

    }

    /**
     * Returns the current loaded configuration
     */
    public Configuration getCurrentConfiguration(){
        if(myCurrentConfiguration == null){
            throw new HttpConfigurationException("No current Configuration set");
        }
        return myCurrentConfiguration;
    }
}
