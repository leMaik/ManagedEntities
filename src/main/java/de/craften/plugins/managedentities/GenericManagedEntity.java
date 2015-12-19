package de.craften.plugins.managedentities;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * A generic managed entity that can be used to wrap most entities but doesn't add additional functionality.
 */
class GenericManagedEntity<T extends Entity> extends ManagedEntityBase<T> {
    private final Class<T> type;

    GenericManagedEntity(Location location, Class<T> type) {
        super(location);
        this.type = type;
    }

    @Override
    protected T spawnEntity(Location location) {
        return location.getWorld().spawn(location, type);
    }
}
