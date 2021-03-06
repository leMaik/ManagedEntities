package de.craften.plugins.managedentities.behavior;

import de.craften.plugins.managedentities.ManagedEntity;
import de.craften.plugins.managedentities.ManagedEntityBase;
import de.craften.plugins.managedentities.util.EntityUtil;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

/**
 * Behavior that makes the entity name always visible (using an armor stand). This behavior can only be used for one
 * entity at a time.
 */
public class VisibleNameBehavior implements Behavior, PropertyChangeAware, RemoveAware {
    public static final String NAME_PROPERTY_KEY = "name";
    private ArmorStand nametag;

    @Override
    public void tick(ManagedEntityBase entity) {
        if (entity.getEntity() != null) {
            String name = entity.getProperty(NAME_PROPERTY_KEY);
            if (name != null && !name.trim().isEmpty()) {
                Location nameTagLocation = entity.getEntity().getLocation().clone()
                        .add(0, EntityUtil.getEntityHeight(entity.getEntity()) + EntityUtil.NAME_TAG_HEIGHT + 0.1, 0);

                if (nametag == null) {
                    nametag = (ArmorStand) entity.getEntity().getWorld().spawnEntity(nameTagLocation, EntityType.ARMOR_STAND);
                    nametag.setCustomNameVisible(true);
                    nametag.setVisible(false);
                    nametag.setMarker(true);
                    nametag.setCanPickupItems(false);
                    nametag.setGravity(false);
                    nametag.setCustomName(entity.getProperty(NAME_PROPERTY_KEY));
                }

                if (!name.equals(nametag.getCustomName())) {
                    nametag.setCustomName(entity.getEntity().getCustomName());
                }

                nametag.teleport(nameTagLocation);
            }
        }
    }

    @Override
    public void propertyChanged(ManagedEntity entity, String key, String value) {
        if (key.equals(NAME_PROPERTY_KEY)) {
            if (nametag != null) {
                if (value != null) {
                    nametag.setCustomName(value);
                } else {
                    nametag.remove();
                    nametag = null;
                }
            }
        }
    }

    @Override
    public void onBeforeRemove(ManagedEntity entity) {
        if (nametag != null) {
            nametag.remove();
            nametag = null;
        }
    }
}
