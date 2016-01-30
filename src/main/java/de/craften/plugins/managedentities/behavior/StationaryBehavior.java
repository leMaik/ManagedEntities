package de.craften.plugins.managedentities.behavior;

import de.craften.plugins.managedentities.ManagedEntityBase;
import org.bukkit.Location;

/**
 * A behavior that makes an entity stay at a location.
 */
@Deprecated
public class StationaryBehavior implements Behavior {
    private Location location;
    private boolean isTurningAllowed;

    public StationaryBehavior(Location location, boolean isTurningAllowed) {
        this.location = location;
        this.isTurningAllowed = isTurningAllowed;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isTurningAllowed() {
        return isTurningAllowed;
    }

    public void setTurningAllowed(boolean turningAllowed) {
        isTurningAllowed = turningAllowed;
    }

    @Override
    public void tick(ManagedEntityBase entity) {
        if (isTurningAllowed) {
            entity.teleport(location.clone().setDirection(entity.getEntity().getLocation().getDirection()));
        } else {
            entity.teleport(location);
        }
    }
}
