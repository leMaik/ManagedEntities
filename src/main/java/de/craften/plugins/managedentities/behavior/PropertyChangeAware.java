package de.craften.plugins.managedentities.behavior;

import de.craften.plugins.managedentities.ManagedEntity;

/**
 * Interface for behaviors that will be notified whenever a property of an entity changes.
 */
public interface PropertyChangeAware {
    /**
     * Callback that is invoked when a property is changed.
     *
     * @param entity entity the property was changed on
     * @param key    key of the property
     * @param value  new value of the property
     */
    void propertyChanged(ManagedEntity entity, String key, String value);
}
