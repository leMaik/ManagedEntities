package de.craften.plugins.managedentities.behavior;

import de.craften.plugins.managedentities.ManagedEntity;

/**
 * Interface for behaviors that will be notified whenever an entity is removed.
 */
public interface RemoveAware {
    /**
     * Callback that is invoked when an entity is about to be removed.
     *
     * @param entity entity that is about to be removed
     */
    void onBeforeRemove(ManagedEntity entity);
}
