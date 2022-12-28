from state import State
from random import randint
from baseStation import BaseStation
from packet import Packet
from time import sleep

class MobileStation():
    """
    class for all mobile station operations, holds State, BaseStation, Packet 
    """

    def __init__(self, baseStation: BaseStation):
        self.state: State = State(State.IDLE)
        self.packetsToSend = []
        self.emittedPacket = None
        self.CWorder = 0
        self.backoffValue = 0
        self.nav = None
        self.baseStation: baseStation = baseStation
        self.isBusy = False

    def activeAction(self):
        if self.state == State.IDLE:
            if len(self.packetsToSend)>0:
                while self.baseStation.isBusy or self.nav > 0:
                    self.BEB()
                self.state.changeTo(State.DIFS_before_Countdown)

        elif self.state == State.DIFS_before_Countdown:
            
            if self.state.elapsedTimeSinceChange(5):
                self.state.changeTo(State.Countdown)
                if self.baseStation.isBusy() or self.nav > 0:
                    self.state = State.DIFS_before_Countdown

        elif self.state == State.Countdown:

            # delay Countdown:5
            self.state = State.Countdown # TODO: CHECK THIS LINE IN PROBLEM
            if self.baseStation.isBusy() or self.nav > 0:
                self.state = State.DIFS_before_Countdown



    def receptionAction(self, packet: Packet):
        # can actually capture a packet from other node but it will digress as it should only receive from base station
        pass

    def BEB(self):
        """
        Function to calculate binary exponential backOff
        """
        # should be restarted to zero somewhere later
        self.backoffValue = randint(0, 2**self.CWorder)
        self.CWorder =+ 1
        # sleep(self.backoffValue / 1000) # or whatever delaying technique to be used later
