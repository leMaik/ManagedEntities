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

    public <T extends Entity> ManagedEntity<T> spawn(Location location, Class<T> type) {
        ManagedEntityBase<T> entity = new GenericManagedEntity<>(location, type);
        entities.put(entity.getUniqueId(), entity);
        return entity;
    }
}