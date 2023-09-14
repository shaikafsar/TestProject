package com.gt.config;

import org.aeonbits.owner.Config;

@Config.Sources(value="file:${user.dir}/src/test/resources/config/config.properties")
public interface ApiConfig extends Config {

    String apiBaseUrl();

    String ListUserEndPoint();

}
