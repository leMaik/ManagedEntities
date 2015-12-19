package de.craften.plugins.managedentities.behavior;

import de.craften.plugins.managedentities.ManagedEntityBase;
import org.bukkit.entity.Entity;

/**
 * A behavior of an entity.
 */
public interface Behavior {
    /**
     * Invoked on every tick.
     *
     * @param entity entity to apply the behavior on
     */
    void tick(ManagedEntityBase entity);
}
