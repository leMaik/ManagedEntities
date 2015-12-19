package de.craften.plugins.managedentities.behavior;

import de.craften.plugins.managedentities.ManagedEntityBase;
import de.craften.plugins.managedentities.util.EntityUtil;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

/**
 * Behavior that makes the entity name always visible (using an armor stand). This behavior can only be used for one
 * entity at a time.
 */
public class VisibleNameBehavior implements Behavior {
    private ArmorStand nametag;

    @Override
    public void tick(ManagedEntityBase entity) {
        Location nameTagLocation = entity.getEntity().getLocation().clone()
                .add(0, EntityUtil.getEntityHeight(entity.getEntity()) + EntityUtil.NAME_TAG_HEIGHT + 0.1, 0);

        if (nametag == null) {
            nametag = (ArmorStand) entity.getEntity().getWorld().spawnEntity(nameTagLocation, EntityType.ARMOR_STAND);
            nametag.setCustomNameVisible(true);
            nametag.setVisible(false);
            nametag.setMarker(true);
            nametag.setCanPickupItems(false);
            nametag.setGravity(false);
            nametag.setCustomName(entity.getEntity().getCustomName());
        }

        if (!nametag.getCustomName().equals(entity.getEntity().getCustomName())) {
            nametag.setCustomName(entity.getEntity().getCustomName());
        }

        nametag.teleport(nameTagLocation);
    }
}
