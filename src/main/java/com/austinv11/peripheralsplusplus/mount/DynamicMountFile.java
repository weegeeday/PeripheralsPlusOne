package com.austinv11.peripheralsplusplus.mount;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DynamicMountFile  {
    private final File filePath;

    public DynamicMountFile(File filePath) {
        this.filePath = filePath;
    }
    public boolean exists(@Nonnull String path) throws IOException {
        return path.isEmpty();
    }
    public boolean isDirectory(@Nonnull String path) throws IOException {
        return false;
    }
    public void list(@Nonnull String path, @Nonnull List<String> contents) throws IOException {

    }
    public long getSize(@Nonnull String path) throws IOException {
        return path.isEmpty() ? filePath.getTotalSpace() : 0;
    }

    @Nonnull
    public InputStream openForRead(@Nonnull String path) throws IOException {
        if (path.isEmpty())
            return new FileInputStream(filePath);
        throw new IOException(path);
    }
}
