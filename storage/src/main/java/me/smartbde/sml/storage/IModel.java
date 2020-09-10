package me.smartbde.sml.storage;

import java.io.InputStream;
import java.io.OutputStream;

public interface IModel {
    public InputStream readModel(String path);

    public void writeModel(String path, OutputStream modelStream);

    public void deleteModel(String path);
}
