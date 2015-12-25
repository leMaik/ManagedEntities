package de.craften.plugins.managedentities;

import de.craften.plugins.managedentities.behavior.Behavior;
import de.craften.plugins.managedentities.behavior.PropertyChangeAware;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;

import java.util.*;

/**
 * The abstract base for a managed entity.
 *
 * @param <T> type of the entity
 */
public abstract class ManagedEntityBase<T extends Entity> implements ManagedEntity<T> {
    private final UUID id = UUID.randomUUID();
    private final List<Behavior> behaviors = new ArrayList<>();
    private final Map<String, String> properties = new HashMap<>();
    private T entity;
    private Location location;

    public ManagedEntityBase(Location location) {
        this.location = location;
    }

    @Override
    public T getEntity() {
        return entity;
    }

    @Override
    public void spawn() {
        if (entity == null) {
            entity = spawnEntity(location);
        }
    }

    @Override
    public void remove() {
        if (entity != null) {
            entity.remove();
            entity = null;
        }
    }

    @Override
    public void kill() {
        if (entity instanceof Damageable) {
            ((Damageable) entity).setHealth(0);
            entity = null;
        } else {
            remove();
        }
    }

    @Override
    public UUID getUniqueId() {
        return id;
    }

    @Override
    public void teleport(Location location) {
        this.location = location;
        if (entity != null) {
            entity.teleport(location);
        }
    }

    @Override
    public void addBehavior(Behavior behavior) {
        behaviors.add(behavior);
    }

    @Override
    public void removeBehavior(Behavior behavior) {
        behaviors.remove(behavior);
    }

    void tick() {
        for (Behavior behavior : behaviors) {
            behavior.tick(this);
        }
    }

    @Override
    public String getProperty(String key) {
        return properties.get(key);
    }

    @Override
    public void setProperty(String key, String value) {
        properties.put(key, value);

        for (Behavior behavior : behaviors) {
            if (behavior instanceof PropertyChangeAware) {
                ((PropertyChangeAware) behavior).propertyChanged(this, key, value);
            }
        }
    }

    protected abstract T spawnEntity(Location location);
}
