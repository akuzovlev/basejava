package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.List;

public class StrategySelect extends AbstractStorage {
    private AbstractStorage storageRealization;

    public StrategySelect(AbstractStorage storageRealization) {
        this.storageRealization = storageRealization;
    }

    public void setStorageRealization(AbstractStorage storageRealization) {
        this.storageRealization = storageRealization;
    }


    @Override
    protected Object getSearchKey(String uuid) {
        return storageRealization.getSearchKey(uuid);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storageRealization.doUpdate(r, searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storageRealization.isExist(searchKey);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        storageRealization.doSave(r, searchKey);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storageRealization.doGet(searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storageRealization.doDelete(searchKey);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return storageRealization.doCopyAll();
    }

    @Override
    public void clear() {
        storageRealization.clear();
    }

    @Override
    public int size() {
        return storageRealization.size();
    }
}
