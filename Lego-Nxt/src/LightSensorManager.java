import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;

public class LightSensorManager implements Runnable
{
    private LightSensor  sensor;
    private MotorManager motor;

    private boolean running;

    public LightSensorManager(MotorManager motor)
    {
        this.sensor  = new LightSensor(SensorPort.S4);
        this.motor   = motor;
        this.running = true;

        sensor.readValue();
    }

    @Override
    public void run()
    {
        startSensor();
    }

    private void startSensor()
    {
        while (running)
        {
            String normal = "value: " + sensor.readNormalizedValue();
            Simple.print(normal, 0);

            String value = "value: " + sensor.readValue();
            Simple.print(value, 1);

//            motor.steer();
        }
    }

    public void stopSensor()
    {
        running = false;
    }
}
