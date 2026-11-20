
package domain.state;

import domain.Location;
import java.util.Objects;

public final class RookMovement {
    private final Location from;
    private final Location to;

    public RookMovement(Location from, Location to) {
        this.from = Objects.requireNonNull(from);
        this.to = Objects.requireNonNull(to);
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }
}
