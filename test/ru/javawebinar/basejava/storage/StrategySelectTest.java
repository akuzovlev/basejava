package ru.javawebinar.basejava.storage;

public class StrategySelectTest extends AbstractStorageTest  {
    static StrategySelect stsel = new StrategySelect(new ObjectStreamStorage(STORAGE_DIR));

    static {
        stsel.setStorageRealization(new ObjectStreamPathStorage(STORAGE_DIR.toString()));
    }

    public StrategySelectTest() {
        super(stsel);
    }



}
