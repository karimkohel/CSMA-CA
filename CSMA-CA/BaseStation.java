public class BaseStation {

    public MobileStation curSender; //keeps track of the mobile station ur working with
    private boolean Busy;

    State state = State.IDLE;
    public boolean isBusy() {
        return Busy;
    }

    public void setBusy(boolean busy) {
        Busy = busy;
    }

    public void activeAction()
    {
        if(this.state == State.IDLE)
        {
            return;
        }
        else if (this.state == State.SIFS_before_emitCTS)
        {
            // delay SIFS_before_emitCTS: 3
            this.state = State.emitCTS;
        }
        else if(this.state == State.emitCTS)
        {
            // delay SIFS_before_emitCTS: 15
            // send your CTS using reception function
            this.state = State.SIFS_before_rcvPKT;
        }
        else if(this.state == State.SIFS_before_rcvPKT)
        {
            // delay SIFS_before_rcvPKT: 3
            this.state = State.rcvPKT;
        }
        else if(this.state == State.rcvPKT)
        {
            // delay rcvPKT: 61
            this.state = State.IDLE;
        }
        else if(this.state == State.SIFS_before_emitACK)
        {
            // delay SIFS_before_emitACK : 3
            this.state = State.emitACK;
        }
        else if(this.state == State.emitACK)
        {
            // send your ACK using reception function
            this.state = State.IDLE;
        }

    }
    public void receptionAction(Packet packet)
    {
        if(this.state == State.IDLE)
        {
            if(!packet.isCorrupted() && packet.getType() == Packet.RTS)
            {
                this.curSender = packet.getMobileStation();
                this.state = State.SIFS_before_emitCTS;
            }
        }
        else if (this.state == State.rcvPKT)
        {
            if(!packet.isCorrupted() && packet.getType() == 3 &&  packet.getMobileStation() == this.curSender) // 3 for PKT
            {
                this.curSender = packet.getMobileStation();
                this.state = State.SIFS_before_emitACK;
            }
        }
    }
}
