package com.gt.config;

public final class ConfigFactory { // no one can extend

    private ConfigFactory() { // no one can create object
    }

    public static FrameworkConfig getConfig(){
        return org.aeonbits.owner.ConfigFactory.create(FrameworkConfig.class);
    }
}
