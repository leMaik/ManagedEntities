package de.craften.plugins.managedentities;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

class EntityListener implements Listener {
    private final EntityManager entityManager;

    public EntityListener(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent event) {
        ManagedEntity entity = entityManager.getEntity(event.getEntity());
        if (entity instanceof ManagedEntityBase) {
            ((ManagedEntityBase) entity).onDeath();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChunkUnload(ChunkUnloadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (entityManager.getEntity(entity) != null) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
