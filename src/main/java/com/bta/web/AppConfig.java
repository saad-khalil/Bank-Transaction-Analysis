package com.bta.web;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

public class AppConfig extends ResourceConfig {
    public AppConfig() {
        packages(true, "com.bta.main");
        property(JspMvcFeature.TEMPLATE_BASE_PATH, "/resource/jsp");
        register(JspMvcFeature.class);
    }
}
