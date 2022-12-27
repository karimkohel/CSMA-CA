public class Packet {
    public static int CTS = 1;
    public static int RTS = 2;

    private int Type;
    private boolean Corrupted;
    private MobileStation mobileStation;
    private int NAV;

    public void setType(int type) {
        Type = type;
    }

    public void setCorrupted(boolean corrupted) {
        Corrupted = corrupted;
    }

    public void setMobileStation(MobileStation mobileStation) {
        this.mobileStation = mobileStation;
    }

    public void setNAV(int NAV) {
        this.NAV = NAV;
    }

    public int getType() {
        return Type;
    }

    public boolean isCorrupted() {
        return Corrupted;
    }

    public MobileStation getMobileStation() {
        return mobileStation;
    }

    public int getNAV() {
        return NAV;
    }
}
