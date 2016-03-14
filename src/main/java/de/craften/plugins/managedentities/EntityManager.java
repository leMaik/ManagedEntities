package de.craften.plugins.managedentities;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A manager for entities.
 */
public class EntityManager {
    private final Plugin plugin;
    private final List<ManagedEntityBase> entities = new CopyOnWriteArrayList<>();
    private final Map<UUID, ManagedEntityBase> entityMappings = new HashMap<>();

    public EntityManager(Plugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                for (ManagedEntityBase entity : entities) {
                    if (entity.getEntity() != null) {
                        entity.tick();
                    }
                }
            }
        }, 0, 1);

        plugin.getServer().getPluginManager().registerEvents(new EntityListener(this), plugin);
    }

    /**
     * Create a managed entity of the given type at the given location.
     *
     * @param location location
     * @param type     entity type
     * @param <T>      entity type
     * @return managed entity
     */
    public <T extends Entity> ManagedEntityBase<T> create(Location location, Class<T> type) {
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
        entities.add(entity);
        entity.entityManager = this;
    }

    /**
     * Removes the given entity from this manager and from the world.
     *
     * @param entity entity to remove
     */
    public void removeEntity(ManagedEntityBase entity) {
        entities.remove(entity);
        entity.remove();
    }

    /**
     * Kills the given entity and removed it from this manager.
     *
     * @param entity entity to kill and remove
     */
    public void killAndRemoveEntity(ManagedEntityBase entity) {
        entities.remove(entity);
        entity.kill();
    }

    /**
     * Gets the managed entity for the given entity, if it is managed.
     *
     * @param entity entity
     * @param <T>    type of the entity
     * @return managed entity for the given entity or null if the given entity is not managed
     */
    @SuppressWarnings("unchecked")
    public <T extends Entity> ManagedEntity<T> getEntity(T entity) {
        return entityMappings.get(entity.getUniqueId());
    }

    /**
     * Removes all entities.
     */
    public void removeAll() {
        for (ManagedEntity entity : entities) {
            entity.remove();
        }
        entities.clear();
        entityMappings.clear();
    }

    <T extends Entity> void addMapping(T entity, ManagedEntityBase managedEntity) {
        entityMappings.put(entity.getUniqueId(), managedEntity);
    }

    <T extends Entity> void removeMapping(T entity) {
        entityMappings.remove(entity.getUniqueId());
    }
}
