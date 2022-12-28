import java.util.HashMap;
import java.lang.Object;
public class BaseStation {

    public MobileStation curSender; //keeps track of the mobile station ur working with

    long start ; // start time for a state
    long finish ; // end time for a state
    private boolean Busy;

    State state = State.IDLE;
    Packet emittedPacket;
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
            if(elapsedTime(3))
                this.changeState(State.emitCTS);
        }
        else if(this.state == State.emitCTS)
        {
            if(elapsedTime(15))
                // send your CTS using reception function
                this.changeState(State.SIFS_before_rcvPKT);
        }
        else if(this.state == State.SIFS_before_rcvPKT)
        {
           if(elapsedTime(3))
               this.changeState(State.rcvPKT);
        }
        else if(this.state == State.rcvPKT)
        {
            if(elapsedTime(61))
                this.changeState(State.IDLE); // case of timeout, it will be received by reception function
        }
        else if(this.state == State.SIFS_before_emitACK)
        {
            if(elapsedTime(3))
                this.changeState(State.emitACK);
        }
        else if(this.state == State.emitACK)
        {
            if(elapsedTime(10))
                // send your ACK using reception function
                this.changeState(State.IDLE);
        }

    }
    public void receptionAction(Packet packet)
    {
        if(this.state == State.IDLE)
        {
            if(!packet.isCorrupted() && packet.getType() == Packet.RTS)
            {
                this.curSender = packet.getOwner();
                this.changeState(State.SIFS_before_emitCTS);
            }
        }
        else if (this.state == State.rcvPKT)
        {
            if(!packet.isCorrupted() && packet.getType() == 3 &&  packet.getOwner() == this.curSender) // 3 for PKT
            {
                this.changeState(State.SIFS_before_emitACK);
            }
           /* else
            {
                this.changeState(State.IDLE);
                this.curSender = null;
            }*/
        }
    }

    public boolean elapsedTime(int i)
    {
        finish = System.nanoTime();
        return ((finish - start) / 1000000) >= i;
    }
    private void changeState(State s)
    {
        start = System.nanoTime();
        if(s == State.IDLE)
        {

        } else if (s == State.SIFS_before_emitCTS) {

        } else if (s == State.emitCTS) {

        } else if (s == State.SIFS_before_rcvPKT) {

        } else if (s == State.rcvPKT) {

        } else if (s == State.SIFS_before_emitACK) {

        } else if (s == State.emitACK) {

        }

    }
}
