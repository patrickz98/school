import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

public class Main
{
    public static void main(String[] args)
    {
        MotorManager myMotor = new MotorManager();
        Thread thread = new Thread(myMotor);
        thread.start();

        LightSensorManager mySensor = new LightSensorManager(myMotor);
        Thread sthread = new Thread(mySensor);
        sthread.start();

        Simple.sleep(4000);

        myMotor.stopMotor();

        Simple.sleep(40000);

//
//        ButtonListener buttonListener = new ButtonListener()
//        {
//            @Override
//            public void buttonPressed(Button button)
//            {
//            }
//
//            @Override
//            public void buttonReleased(Button button)
//            {
//                LCD.drawString("Button: " + button.getId(), 0, 2);
//
//                if (button.getId() == Button.ID_ESCAPE)
//                {
//                    LCD.drawString("ESCAPE", 0, 2);
//                }
//            }
//        };
//
//        for (Button button: Button.BUTTONS)
//        {
////            button.addButtonListener(buttonListener);
//        }
//
//        TouchSensor touch = new TouchSensor(SensorPort.S4);
//
//        SensorPortListener portListener = new SensorPortListener()
//        {
//            int count = 0;
//
//            @Override
//            public void stateChanged(SensorPort sensorPort, int i, int i1)
//            {
//                LCD.drawString("Touched: " + count, 0, 0);
//                count++;
//            }
//        };
//
//        SensorPort.S4.addSensorPortListener(portListener);
//
//        LCD.drawString("Alive", 0, 3);
//        Simple.sleep(200000000);
////        while(! touch.isPressed())
////        {
////            LCD.drawString("" + touch.isPressed(), 0, 0);
////
////            pilot.travel(30d);
////            pilot.rotate(90d);
////
////            while (pilot.isMoving())
////            {
////                Thread.yield();
////            }
////        }
////
////        LCD.drawString("Exit            ", 0, 0);
    }
}
