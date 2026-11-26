
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        RookMovement other = (RookMovement) obj;

        return from.equals(other.from) && to.equals(other.to);
    }

    @Override
    public String toString() {
        return String.format("RookMovement{from=%s, to=%s}", from, to);
    }
}
