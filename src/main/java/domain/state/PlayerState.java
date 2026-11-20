package domain.state;

public class PlayerState {
    private boolean castleKingsideEligible;
    private boolean castleQueensideEligible;

    public PlayerState() {
        this.castleKingsideEligible = true;
        this.castleQueensideEligible = true;
    }

    public boolean getCastleKingsideEligible() {
        return castleKingsideEligible;
    }

    public boolean getCastleQueensideEligible() {
        return castleQueensideEligible;
    }

    public void revokeKingsideCastling() {
        castleKingsideEligible = false;
    }

    public void revokeQueensideCastling() {
        castleQueensideEligible = false;
    }
}