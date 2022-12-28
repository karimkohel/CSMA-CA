import java.util.Random;

public class MobileStation {

    public BaseStation baseStation;

    long start ; // start time for a state
    long finish ; // end time for a state
    private int PacketToSend = 0;

    private int CWorder = 0;
    private int backoffValue;

    public int getBackoffValue() {
        return backoffValue;
    }

    private int nav; // approximation of how long the channel is going to be busy

    private boolean Busy;

    public boolean isBusy() {
        return Busy;
    }

    public void setBusy(boolean busy) {
        Busy = busy;
    }

    State state = State.IDLE;

    Packet emittedPacket;

    public void activeAction()
    {
        if(this.state == State.IDLE)
        {
            if (PacketToSend > 0) {
                if (baseStation.isBusy() || nav > 0) {
                    BEB();
                }
                this.changeState(State.DIFS_before_Countdown);
            }

        }
        else if(this.state == State.DIFS_before_Countdown)
        {
            if(elapsedTime(5))
                this.changeState(State.Countdown);
                if (baseStation.isBusy() || nav > 0)
                    this.changeState(State.DIFS_before_Countdown);

        }
        else if(this.state == State.Countdown)
        {
            if(elapsedTime(5))
                backoffValue--;
                this.changeState(State.Countdown);
                if (baseStation.isBusy() || nav > 0)
                    this.changeState(State.IDLE);
        }
        else if(this.state == State.emitRTS)
        {
            if(elapsedTime(15))
                // send the RTS packet
                this.changeState(State.SIFS_before_rcvCTS);
        }
        else if(this.state == State.SIFS_before_rcvCTS)
        {
            if(elapsedTime(3))
                this.changeState(State.rcvCTS);
        }
        else if(this.state == State.rcvCTS)
        {
            if(elapsedTime(16))
                BEB();
                this.changeState(State.DIFS_before_Countdown); //this only happends when timeout
        }
        else if(this.state == State.SIFS_before_emitPKT)
        {
            if(elapsedTime(3))
                this.changeState(State.emitPKT);
        }
        else if(this.state == State.emitPKT)
        {
            if(elapsedTime(60))
                //send the packet
                this.changeState(State.SIFS_before_rcvACK);
        }
        else if(this.state == State.SIFS_before_rcvACK)
        {
            if(elapsedTime(3))
                this.changeState(State.rcvACK);
        }
        else if(this.state == State.rcvACK)
        {
            if(elapsedTime(11))
                BEB();
                this.changeState(State.DIFS_before_Countdown); //happends in case of timeout
        }
    }


    public void receptionAction(Packet packet)
    {
        if(this.state == State.IDLE || this.state == State.DIFS_before_Countdown || this.state == State.Countdown)
        {
            if( (!packet.isCorrupted()) && (packet.getNAV() > this.nav))
            {
                this.nav = packet.getNAV();
            }
        }
        else if(this.state == State.rcvCTS)
        {
            if( (!packet.isCorrupted()) && (packet.getOwner() == this) && (packet.getType() == Packet.CTS))
            {
                this.changeState(State.SIFS_before_emitACK);
            }
            else
            {
                BEB();
                this.state = State.DIFS_before_Countdown;
            }
        }
        else if(this.state == State.rcvACK)
        {
            if( (!packet.isCorrupted()) && (packet.getOwner() == this) && (packet.getType() == 0)) // 0 for ACK
            {
                PacketToSend--;
                if(PacketToSend > 0)
                {
                    CWorder =3;
                    BEB();
                    this.changeState(State.DIFS_before_Countdown);
                }
                else
                    this.changeState(State.IDLE);
            }
            else
            {
                BEB();
                this.changeState(State.DIFS_before_Countdown);
            }
        }
    }

    public void BEB()
    {
        Random rand = new Random();
        this.backoffValue = rand.nextInt((int)(Math.pow(2,CWorder)));
        CWorder++;
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

        } else if (s == State.DIFS_before_Countdown) {

        } else if (s == State.Countdown) {
            if (this.backoffValue <=0)
                this.changeState(State.emitRTS);    // check this if it is correct or not
        } else if (s == State.emitRTS) {

        } else if (s == State.SIFS_before_rcvCTS) {

        } else if (s == State.rcvCTS) {

        } else if (s == State.SIFS_before_emitPKT) {

        } else if (s == State.emitPKT) {

        } else if (s == State.SIFS_before_rcvACK) {

        } else if (s == State.rcvACK) {

        }
    }
}
