package de.craften.plugins.managedentities.util.nms;

import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.EntityLiving;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

/**
 * NMS utility methods for entities.
 */
public class NmsEntityUtil {
    /**
     * Sets whether the AI for the given entity is enabled. This also toggles gravity.
     *
     * @param entity entity
     * @param hasAi  whether the entity should have an AI
     */
    public static void setAi(LivingEntity entity, boolean hasAi) {
        Entity nmsEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = new NBTTagCompound();
        nmsEntity.c(tag);
        tag.setInt("NoAI", hasAi ? 0 : 1);
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
        NBTTagCompound tag = new NBTTagCompound();
        nmsEntity.c(tag);
        tag.setInt("Invulnerable", invulnerable ? 1 : 0);
        nmsEntity.f(tag);
    }

    /**
     * Sets the head rotation of the given entity.
     *
     * @param entity entity
     * @param yaw    head yaw, in degree
     * @param pitch  head pitch, in degree
     */
    public static void setHeadRotation(LivingEntity entity, float yaw, float pitch) {
        EntityLiving nmsEntity = ((CraftLivingEntity) entity).getHandle();
        nmsEntity.f(clampYaw(yaw));
        //nmsEntity.g(pitch);
        //TODO test
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
