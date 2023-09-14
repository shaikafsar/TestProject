package com.gt.config;

public final class ApiConfigFactory { // no one can extend

    private ApiConfigFactory() { // no one can create object
    }

    public static ApiConfig getConfig(){
        return org.aeonbits.owner.ConfigFactory.create(ApiConfig.class);
    }
}
