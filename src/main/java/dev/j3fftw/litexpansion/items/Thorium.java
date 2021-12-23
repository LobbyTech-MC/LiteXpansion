package dev.j3fftw.litexpansion.items;

import dev.j3fftw.litexpansion.Items;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactive;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class Thorium extends UnplaceableBlock implements Radioactive {

    private static final ItemStack thorium = new CustomItemStack(Material.PAPER, "&f提醒!",
        "&a&o请先扫描一个区块", "&a&o然后你才能在这个区块里开采出钍!");

    public Thorium() {
        super(Items.LITEXPANSION, Items.THORIUM, RecipeType.GEO_MINER, new ItemStack[] {
                null, null, null,
                null, thorium, null,
                null, null, null
            }
        );
    }

    @Nonnull
    @Override
    public Radioactivity getRadioactivity() {
        return Radioactivity.HIGH;
    }
}
