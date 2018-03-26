import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class MotorManager implements Runnable
{
    private boolean running = true;
    private DifferentialPilot pilot;

    public MotorManager()
    {
        pilot = new DifferentialPilot(2.3f, 5.5f, Motor.B, Motor.C, false);
        pilot.setTravelSpeed(30d);

        // pilot.rotate(90d);
    }

    @Override
    public void run()
    {
        startMotor();
    }

    public void startMotor()
    {
        Simple.print("Start", 2);

        while(running)
        {
            pilot.travel(3d);

            while (pilot.isMoving()) Thread.yield();
        }
    }

    public void stopMotor()
    {
        Simple.print("Stop ", 2);
        running = false;
    }

    public void steer()
    {
        pilot.steer(10d);
    }
}
