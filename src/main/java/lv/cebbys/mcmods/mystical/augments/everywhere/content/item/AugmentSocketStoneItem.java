package lv.cebbys.mcmods.mystical.augments.everywhere.content.item;

import lombok.Getter;
import net.minecraft.world.item.Item;

@Getter
public class AugmentSocketStoneItem extends Item {
    private final int tier;

    public AugmentSocketStoneItem(int tier) {
        super(new Properties());
        this.tier = tier;
    }
}
