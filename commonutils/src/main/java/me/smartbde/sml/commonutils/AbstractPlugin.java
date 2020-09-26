package me.smartbde.sml.commonutils;

import java.util.Map;

public abstract class AbstractPlugin implements IPlugin {
    protected Map<String, String> properties = null;

    /**
     * Set Config. Configuration的实现类包含YAMLConfiguration，DatabaseConfiguration等
     *
     * @param config
     */
    @Override
    public void setConfig(Map<String, String> config) {
        properties = config;
    }

    /**
     * Get Config.
     */
    @Override
    public Map<String, String> getConfig() {
        return properties;
    }
}
