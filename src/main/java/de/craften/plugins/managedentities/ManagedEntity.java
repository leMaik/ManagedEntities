package de.craften.plugins.managedentities;

import de.craften.plugins.managedentities.behavior.Behavior;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.Collection;
import java.util.UUID;

/**
 * An entity that can be managed by the {@link EntityManager}.
 */
public interface ManagedEntity<T extends Entity> {
    /**
     * Get the underlying entity.
     *
     * @return underlying entity
     */
    T getEntity();

    /**
     * Get the unique ID of this managed entity. This is not the unique ID of the actual entity and will persist if the
     * actual entity is respawned.
     *
     * @return unique ID of this managed entity
     */
    UUID getUniqueId();

    /**
     * Spawn this entity.
     */
    void spawn();

    /**
     * Remove this entity.
     */
    void remove();

    /**
     * Kill this entity. If this entity is not {@link org.bukkit.entity.Damageable}, it will simply be removed.
     */
    void kill();

    /**
     * Gets the location of this entity.
     *
     * @return location of this entity
     */
    Location getLocation();

    /**
     * Teleport this entity to the given location.
     *
     * @param location target location
     */
    void teleport(Location location);

    /**
     * Adds a behavior to this entity.
     *
     * @param behavior behavior to add
     */
    void addBehavior(Behavior behavior);

    /**
     * Removes a behavior from this entity.
     *
     * @param behavior behavior to remove
     */
    void removeBehavior(Behavior behavior);

    /**
     * Gets all behaviors of the given type of this entity.
     *
     * @param behaviorType behavior type
     * @return all behaviors of the given type
     */
    Collection<Behavior> getBehaviors(Class<? extends Behavior> behaviorType);

    /**
     * Gets the property with the given key.
     *
     * @param key key of the property
     * @return value of the property or null if the property isn't set
     */
    String getProperty(String key);

    /**
     * Sets the property with the given key to the given value.
     *
     * @param key   key of the property
     * @param value new value of the property
     */
    void setProperty(String key, String value);
}
