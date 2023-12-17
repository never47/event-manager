package com.example.scorerecordingmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogManager {
    private static Logger logger = LoggerFactory.getLogger(LogManager.class);

    public static Logger getLogger(){
        return logger;
    }
}
