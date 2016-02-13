package de.craften.plugins.managedentities.util.nms;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
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

    /**
     * Sets whether the entity is invulnerable. This only blocks players in survival or adventure mode from hurting
     * the entity.
     *
     * @param entity       entity
     * @param invulnerable whether the entity should be invulnerable
     */
    public static void setInvulnerable(LivingEntity entity, boolean invulnerable) {
        Entity nmsEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("Invulnerable", invulnerable ? 1 : 0);
        nmsEntity.f(tag);
    }

    public static void setHeadRotation(LivingEntity entity, float yaw, float pitch) {
        EntityLiving nmsEntity = ((CraftLivingEntity) entity).getHandle();
        nmsEntity.f(clampYaw(yaw));
        nmsEntity.g(pitch);
    }

    private static float clampYaw(float yaw) {
        while (yaw < -180.0F) {
            yaw += 360.0F;
        }

        while (yaw >= 180.0F) {
            yaw -= 360.0F;
        }

        return yaw;
    }
}
