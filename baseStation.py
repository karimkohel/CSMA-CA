from state import State
from mobileStation import MobileStation
from packet import Packet

class BaseStation():

    # still need to implement wait delay

    def __init__(self):
        self.isBusy = False
        self.state = State(State.IDLE)
        self.currSender = None


    def activeAction(self):
        if self.state == State.IDLE:
            return
        elif self.state == State.SIFS_before_emitCTS:
            # delay SIFS_before_emitCTS: 3
            self.state = State.emitCTS

        elif self.state == State.emitCTS:
            # delay SIFS_before_emitCTS: 15
            # send your CTS using reception function
            self.state = State.SIFS_before_rcvPKT

        elif self.state == State.SIFS_before_rcvPKT:
            # delay SIFS_before_rcvPKT: 3
            self.state = State.rcvPKT

        elif self.state == State.rcvPKT:
            # delay rcvPKT: 61
            self.state = State.IDLE

        elif self.state == State.SIFS_before_emitACK:
            # delay SIFS_before_emitACK : 3
            self.state = State.emitACK

        elif self.state == State.emitACK:
            # send your ACK using reception function
            self.state = State.IDLE


    def receptionAction(self, packet):
        if (self.state == State.IDLE) and (not packet.corrupted) and (packet.type == Packet.RTS):
            self.currSender = packet.mobileStation
            self.state = State.SIFS_before_emitACK
        elif (self.state == State.rcvPKT) and (not packet.corrupted):
            if (packet.type == Packet.PKT) and (packet.mobileStation == self.currSender):
                self.state = State.SIFS_before_emitACK
            else:
                self.state.changeTo(State.IDLE)