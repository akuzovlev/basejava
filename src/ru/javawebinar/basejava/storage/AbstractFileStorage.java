package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * gkislin
 * 22.07.2016
 */
public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
            File[] files = directory.listFiles();
            if (files != null) {
                Arrays.stream(files).forEach(file ->
                {if (!file.delete()) {
                    throw new StorageException(file.toString(), "File can't be deleted");
                }});
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Read error", "");
        }
        return list.length;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    @Override
    protected Resume doGet(File file) {
        return doRead(file);
    }

    protected abstract Resume doRead(File file);

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException(file.toString(), "File can't be deleted");
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> result = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Read error","");
        } else {
            Arrays.stream(files).forEach(file -> result.add(doRead(file)));
            return result;
        }
    }
}