package de.craften.plugins.managedentities.util.nms;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;

/**
 * NMS utility methods for entities.
 */
public class NmsEntityUtil {
    /**
     * Disables the AI for the given entity.
     *
     * @param entity entity
     */
    public static void disableAi(LivingEntity entity) {
        Entity nmsEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        nmsEntity.f(tag);
    }
}
