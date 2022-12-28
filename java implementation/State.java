import java.util.HashMap;

public enum State {
    IDLE,
    SIFS_before_emitCTS,
    emitCTS,
    SIFS_before_rcvPKT,
    rcvPKT,
    SIFS_before_emitACK,
    emitACK,
    DIFS_before_Countdown,
    Countdown,
    emitRTS,
    SIFS_before_rcvCTS,
    rcvCTS,
    SIFS_before_emitPKT,
    emitPKT,
    SIFS_before_rcvACK,
    rcvACK;

    private final HashMap<String, String> stateColor = new HashMap<String, String>();

    State(HashMap<String, String> stateColor) {
        stateColor.put("no Frame","White");
        stateColor.put("RTS","Bright Blue");
        stateColor.put("CTS","Dark Blue");
        stateColor.put("Data Frame","Black");
        stateColor.put("ACK","Green");
        stateColor.put("Channel Free","Grey");
        stateColor.put("Channel Busy","Red");
        stateColor.put("Channel Considered Busy","Yellow");
        stateColor.put("Countdown","Dark Grey");
    }

    State() {
    }
    public void getStateColor(String key)
    {
        stateColor.get(key);
    }

}

