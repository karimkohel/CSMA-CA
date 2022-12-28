import time

class State():
    """
    A sate class (that should be an Enum) in both mobile/base stations, will handle timing and state change
    """

    # common
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
    emitRTS = 9
    SIFS_before_rcvCTS = 10 
    rcvCTS = 11
    SIFS_before_emitPKT = 12 
    emitPKT = 13
    SIFS_before_rcvACK = 14 
    rcvACK = 15

    def __init__(self, state = IDLE):
        self.changeTo(state)

    def changeTo(self, newState):
        self.ticks = time.time()
        self.state = newState

    def elapsedTimeSinceChange(self, i):
        return (time.time() - self.ticks) > i



if __name__ == "__main__":
    s = State()
    print(s.state)