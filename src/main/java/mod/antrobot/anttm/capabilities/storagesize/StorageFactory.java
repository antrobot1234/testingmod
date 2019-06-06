package mod.antrobot.anttm.capabilities.storagesize;

import java.util.concurrent.Callable;

public class StorageFactory implements Callable<IStorageSize> {
    @Override
    public IStorageSize call() throws Exception {
        return new IStorageSize() {
            int width;
            int height;
            @Override
            public int getWidth() {
                return width;
            }

            @Override
            public int getHeight() {
                return height;
            }

            @Override
            public void setHeight(int input) {
                height = input;
            }

            @Override
            public void setWidth(int input) {
                width = input;
            }
        };
    }
}
