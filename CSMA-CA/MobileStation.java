import java.util.Random;

public class MobileStation {

    private int PacketToSend = 0;

    private int CWorder = 0;
    private int backoffValue;

    public int getBackoffValue() {
        return backoffValue;
    }
    private Random rand;

    private int nav; // approximation of how long the channel is going to be busy

    public BaseStation baseStation;

    State state = State.IDLE;

    public void activeAction()
    {
        if(this.state == State.IDLE)
        {
            if (PacketToSend > 0) {
                if (baseStation.isBusy() || nav > 0) {
                    BEB();
                }
                this.state = State.DIFS_before_Countdown;
            }

        }
        else if(this.state == State.DIFS_before_Countdown)
        {
            //delay DIFS_before_Countdown:5
            this.state = State.Countdown;
            if (baseStation.isBusy() || nav > 0) {
                this.state = State.DIFS_before_Countdown;
            }
        }
        else if(this.state == State.Countdown)
        {
            //delay Countdown:5
            this.state = State.Countdown;
            if (baseStation.isBusy() || nav > 0) {
                this.state = State.DIFS_before_Countdown;
            }
        }

    }
    public void receptionAction(Packet packet)
    {

    }

    public void BEB()
    {
        this.backoffValue = rand.nextInt(0,(int)(Math.pow(2,CWorder)));
        CWorder++;
    }
}
