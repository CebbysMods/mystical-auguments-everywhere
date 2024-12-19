package lv.cebbys.mcmods.mystical.augments.everywhere.utility;

import java.util.function.Consumer;

public interface UpdateProvider<T> {
    void subscribe(Consumer<T> consumer);
}
