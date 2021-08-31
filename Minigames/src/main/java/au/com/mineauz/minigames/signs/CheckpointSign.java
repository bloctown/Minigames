package au.com.mineauz.minigames.signs;

import au.com.mineauz.minigames.MinigameMessageType;
import au.com.mineauz.minigames.objects.MinigamePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import au.com.mineauz.minigames.MinigameUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;

public class CheckpointSign implements MinigameSign {

    @Override
    public String getName() {
        return "Checkpoint";
    }

    @Override
    public String getCreatePermission() {
        return "minigame.sign.create.checkpoint";
    }

    @Override
    public String getCreatePermissionMessage() {
        return MinigameUtils.getLang("sign.checkpoint.createPermission");
    }

    @Override
    public String getUsePermission() {
        return "minigame.sign.use.checkpoint";
    }

    @Override
    public String getUsePermissionMessage() {
        return MinigameUtils.getLang("sign.checkpoint.usePermission");
    }

    @Override
    public boolean signCreate(SignChangeEvent event) {
        event.line(1, Component.text("Checkpoint", NamedTextColor.GREEN));
        if (LegacyComponentSerializer.legacyAmpersand().serialize(event.line(2)).equalsIgnoreCase("global")) {
            event.line(2, Component.text("Global", NamedTextColor.BLUE));
        }
        return true;
    }

    @Override
    public boolean signUse(Sign sign, MinigamePlayer player) {
        if ((player.isInMinigame()) || (!player.isInMinigame() && sign.line(2).contains(Component.text("Global", NamedTextColor.BLUE))
                && player.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR)) {
            if (player.isInMinigame() && player.getMinigame().isSpectator(player)) {
                return false;
            }
            if (player.getPlayer().isOnGround()) {
                Location newloc = player.getPlayer().getLocation();
                if (!sign.line(2).contains(Component.text("Global", NamedTextColor.BLUE))) {
                    player.setCheckpoint(newloc);
                } else {
                    player.getStoredPlayerCheckpoints().setGlobalCheckpoint(newloc);
                }

                player.sendInfoMessage(MinigameUtils.getLang("sign.checkpoint.set"));
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "[Minigames] " + ChatColor.WHITE + MinigameUtils.getLang("sign.checkpoint.fail"), MinigameMessageType.ERROR);
            }
        } else
            player.sendMessage(ChatColor.AQUA + "[Minigames] " + ChatColor.WHITE + MinigameUtils.getLang("sign.emptyHand"), MinigameMessageType.INFO);
        return false;
    }

    @Override
    public void signBreak(Sign sign, MinigamePlayer player) {

    }

}
