from mobileStation import MobileStation

class Packet():

    ACK = 0
    CTS = 1
    RTS = 2
    PKT = 3

    def __init__(self, pktType, mobileStation):
        self.pktType = pktType
        self.corrupted = False
        self.mobileStation = mobileStation