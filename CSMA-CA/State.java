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
    emitRTS
}
