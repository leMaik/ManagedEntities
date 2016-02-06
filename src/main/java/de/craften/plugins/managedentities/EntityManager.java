package de.craften.plugins.managedentities;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A manager for entities.
 */
public class EntityManager {
    private final Plugin plugin;
    private final Map<UUID, ManagedEntityBase> entities = new HashMap<>();

    public EntityManager(Plugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                for (ManagedEntityBase entity : new ArrayList<>(entities.values())) {
                    entity.tick();
                }
            }
        }, 0, 1);
    }

    public <T extends Entity> ManagedEntity<T> create(Location location, Class<T> type) {
        ManagedEntityBase<T> entity = new GenericManagedEntity<>(location, type);
        addEntity(entity);
        return entity;
    }

    /**
     * Adds the given entity.
     *
     * @param entity entity to add
     */
    public void addEntity(ManagedEntityBase entity) {
        entities.put(entity.getUniqueId(), entity);
    }

    /**
     * Reoves the given entity from this manager and from the world.
     *
     * @param entity entity to remove
     */
    public void removeEntity(ManagedEntityBase entity) {
        entities.remove(entity.getUniqueId());
        entity.remove();
    }

    /**
     * Gets the managed entity for the given entity, if it is managed.
     *
     * @param entity entity
     * @param <T>    type of the entity
     * @return managed entity for the given entity or null if the given entity is not managed
     */
    public <T extends Entity> ManagedEntity<T> getEntity(T entity) {
        return entities.get(entity.getUniqueId());
    }

    /**
     * Removes all entities.
     */
    public void removeAll() {
        for (ManagedEntity entity : entities.values()) {
            entity.remove();
        }
        entities.clear();
    }
}
