package dev.j3fftw.litexpansion.items;

import dev.j3fftw.litexpansion.Items;
import dev.j3fftw.litexpansion.LiteXpansion;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CargoConfigurator extends SimpleSlimefunItem<ItemUseHandler> implements Listener {

    private static final NamespacedKey CARGO_BLOCK = new NamespacedKey(LiteXpansion.getInstance(), "cargo_block");
    private static final NamespacedKey CARGO_CONFIG = new NamespacedKey(LiteXpansion.getInstance(), "cargo_config");

    public CargoConfigurator() {
        super(Items.LITEXPANSION, Items.CARGO_CONFIGURATOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.REFINED_IRON, SlimefunItems.REINFORCED_PLATE, Items.REFINED_IRON,
            SlimefunItems.REINFORCED_PLATE, SlimefunItems.CARGO_MANAGER, SlimefunItems.REINFORCED_PLATE,
            Items.REFINED_IRON, SlimefunItems.REINFORCED_PLATE, Items.REFINED_IRON
        });

        Bukkit.getPluginManager().registerEvents(this, LiteXpansion.getInstance());
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> e.setUseBlock(Event.Result.DENY);
    }

    private boolean canUseCargoConfigurator(@Nonnull Player p, @Nonnull Block clicked) {
        return SlimefunPlugin.getProtectionManager().hasPermission(p, clicked, ProtectableAction.INTERACT_BLOCK);
    }

    @EventHandler
    public void onCargoConfiguratorItemClick(PlayerInteractEvent e) {
        if (e.getItem() == null || e.getMaterial() != Material.COMPASS) {
            return;
        }

        final ItemStack clickedItem = e.getItem();

        if (!this.isItem(clickedItem) || SlimefunItem.getByItem(Items.CARGO_CONFIGURATOR).isDisabled()) {
            return;
        }

        final ItemMeta meta = clickedItem.getItemMeta();

        final List<String> defaultLore = Items.CARGO_CONFIGURATOR.getImmutableMeta().getLore()
            .orElse(new ArrayList<>());
        final List<String> lore = meta.hasLore() ? meta.getLore() : defaultLore;

        // Clear the config and lore
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
            && e.getPlayer().isSneaking()
        ) {
            clearConfig(e.getPlayer(), clickedItem, meta, defaultLore, lore);
            e.setCancelled(true);
            return;
        }

        if ((e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.LEFT_CLICK_BLOCK)
            || e.getClickedBlock() == null) {
            return;
        }

        final SlimefunItem block = BlockStorage.check(e.getClickedBlock());
        if (block == null) {
            return;
        }

        final ItemStack clickedItemStack = block.getItem();

        final String blockId = block.getId();
        if (!blockId.equals(SlimefunItems.CARGO_INPUT_NODE.getItemId())
            && !blockId.equals(SlimefunItems.CARGO_OUTPUT_NODE.getItemId())
            && !blockId.equals(SlimefunItems.CARGO_OUTPUT_NODE_2.getItemId())
        ) {
            return;
        }

        final Player p = e.getPlayer();

        if (!canUseCargoConfigurator(p, e.getClickedBlock()) && !p.hasPermission("slimefun.cargo.bypass")) {
            SlimefunPlugin.getLocalization().sendMessage(p, "inventory.no-access", true);
            return;
        }

        e.setCancelled(true);

        runActions(e, clickedItemStack, meta, blockId, lore, defaultLore);

        meta.setLore(lore);
        clickedItem.setItemMeta(meta);
    }

    private void clearConfig(@Nonnull Player player, @Nonnull ItemStack itemStack, @Nonnull ItemMeta meta,
                             @Nonnull List<String> defaultLore, @Nonnull List<String> lore
    ) {
        PersistentDataAPI.remove(meta, CARGO_BLOCK);
        PersistentDataAPI.remove(meta, CARGO_CONFIG);
        player.sendMessage(ChatColor.RED + "已清除该货运节点的配置!");

        if (lore.size() != defaultLore.size()) {
            lore.clear();
            lore.addAll(defaultLore);
        }

        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    private void runActions(@Nonnull PlayerInteractEvent e, @Nonnull ItemStack clickedItemStack, @Nonnull ItemMeta meta,
                            @Nonnull String blockId, @Nonnull List<String> lore, @Nonnull List<String> defaultLore
    ) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            final String copiedBlock = PersistentDataAPI.getString(meta, CARGO_BLOCK);
            final String config = PersistentDataAPI.getString(meta, CARGO_CONFIG);
            if (copiedBlock == null || config == null) {
                e.getPlayer().sendMessage(ChatColor.RED + "你必须先复制一个节点的配置!");
                return;
            }

            if (!copiedBlock.equals(blockId)) {
                e.getPlayer().sendMessage(ChatColor.RED + "你不能复制配置到这个节点上!");
                return;
            }

            BlockStorage.setBlockInfo(e.getClickedBlock(), config, true);
            BlockStorage.getStorage(e.getClickedBlock().getWorld()).reloadInventory(e.getClickedBlock().getLocation());
            e.getPlayer().sendMessage(ChatColor.GREEN + "已黏贴配置!");
        } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            PersistentDataAPI.setString(meta, CARGO_BLOCK, blockId);
            PersistentDataAPI.setString(meta, CARGO_CONFIG, BlockStorage.getBlockInfoAsJson(e.getClickedBlock()));

            // Has the copied part
            if (lore.size() == defaultLore.size() + 2) {
                lore.clear();
                lore.addAll(defaultLore);
            }
            lore.addAll(Arrays.asList("", ChatColor.GRAY + "> 物品 "
                + ChatColor.RESET + clickedItemStack.getItemMeta().getDisplayName()
                + ChatColor.GRAY + " 的配置"
            ));
            e.getPlayer().sendMessage(ChatColor.GREEN + "成功复制节点配置!");
        }
    }
}
