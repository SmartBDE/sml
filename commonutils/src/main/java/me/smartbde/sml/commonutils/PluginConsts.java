package me.smartbde.sml.commonutils;

public class PluginConsts {
    public enum Type {
        STREAM,
        BATCH
    }

    public enum StreamInput {
        HTTP
    }

    public enum BatchInput {
        JDBC,
        MONGO
    }

    public enum Filter {
        SQL,
        GROOVY,
        CLAZZ
    }

    public enum StreamOutput {
        HTTP
    }

    public enum BatchOutput {
        JDBC,
        MONGO
    }
}
