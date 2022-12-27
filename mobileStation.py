from state import State
from random import randint

class MobileStation():

    def __init__(self, baseStation):
        self.state = State.IDLE
        self.packetsToSend = 0
        self.CWorder = 0
        self.backoffValue = 0
        self.nav = None
        self.state = State.IDLE
        self.baseStation = baseStation

    def activeAction(self):
        if self.state == State.IDLE:
            if self.packetsToSend>0:
                while self.baseStation.isBusy or self.nav > 0:
                    self.BEB()
                    # and backoff b2a here?
                self.state = State.DIFS_before_Countdown

        elif self.state == State.DIFS_before_Countdown:
            # delay DIFS_before_Countdown:5
            self.state = State.Countdown
            if self.baseStation.isBusy() or self.nav > 0:
                self.state = State.DIFS_before_Countdown

        elif self.state == State.Countdown:

            # delay Countdown:5
            self.state = State.Countdown # TODO: CHECK THIS LINE IN PROBLEM
            if self.baseStation.isBusy() or self.nav > 0:
                self.state = State.DIFS_before_Countdown



    def receptionAction(self):
        pass

    def BEB(self):
        self.backoffValue = randint(0, 2**self.CWorder)
        self.CWorder =+ 1