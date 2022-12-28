from enum import Enum
import time

class State():
    IDLE = 0

    # Base station States
    SIFS_before_emitCTS = 1
    emitCTS = 2
    SIFS_before_rcvPKT = 3
    rcvPKT = 4
    SIFS_before_emitACK = 5
    emitACK = 6

    # Mobile Station States
    DIFS_before_Countdown = 7
    Countdown = 8

    def __init__(self, state):
        self.state = None
        self.changeTo(State.IDLE)

    def changeTo(self, newState):
        self.ticks = time.time()
        self.state = newState

    def elapsedTimeSinceChange(self, i):
        return (time.time() - self.ticks) > i

