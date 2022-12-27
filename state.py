from enum import Enum

class State(Enum):
    IDLE = 0
    SIFS_before_emitCTS = 1
    emitCTS = 2
    SIFS_before_rcvPKT = 3
    rcvPKT = 4
    SIFS_before_emitACK = 5
    emitACK = 6