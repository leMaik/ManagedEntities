package de.craften.plugins.managedentities;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import de.craften.plugins.managedentities.behavior.Behavior;
import de.craften.plugins.managedentities.behavior.PropertyChangeAware;
import de.craften.plugins.managedentities.util.nms.NmsEntityUtil;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The abstract base for a managed entity.
 *
 * @param <T> type of the entity
 */
public abstract class ManagedEntityBase<T extends Entity> implements ManagedEntity<T> {
    EntityManager entityManager;
    private final UUID id = UUID.randomUUID();
    private final Multimap<Class<? extends Behavior>, Behavior> behaviors = ArrayListMultimap.create();
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

            if (entity instanceof LivingEntity) {
                NmsEntityUtil.disableAi((LivingEntity) entity);
                ((LivingEntity) entity).setRemoveWhenFarAway(false); //TODO this affects performance and should be optimized later (manual tracking of far away entities)
            }

            if (entityManager != null) {
                entityManager.addMapping(entity, this);
            }
        }
    }

    @Override
    public void remove() {
        if (entity != null) {
            entity.remove();
            if (entityManager != null) {
                entityManager.removeMapping(entity);
            }
            entity = null;
        }
    }

    @Override
    public void kill() {
        if (entity instanceof Damageable) {
            ((Damageable) entity).setHealth(0);
            if (entityManager != null) {
                entityManager.removeMapping(entity);
            }
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
    public Location getLocation() {
        return location;
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
        behaviors.put(behavior.getClass(), behavior);
    }

    @Override
    public void removeBehavior(Behavior behavior) {
        behaviors.remove(behavior.getClass(), behavior);
    }

    @Override
    public Collection<Behavior> getBehaviors(Class<? extends Behavior> behaviorType) {
        return behaviors.get(behaviorType);
    }

    void tick() {
        for (Behavior behavior : behaviors.values()) {
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

        for (Behavior behavior : behaviors.values()) {
            if (behavior instanceof PropertyChangeAware) {
                ((PropertyChangeAware) behavior).propertyChanged(this, key, value);
            }
        }
    }

    protected abstract T spawnEntity(Location location);
}
