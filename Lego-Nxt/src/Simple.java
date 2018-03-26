import lejos.nxt.LCD;

public class Simple
{
    public static void sleep(long mil)
    {
        try
        {
            Thread.sleep(mil);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void print(String str, int y)
    {
        LCD.drawString(str, 0, y);
    }
}
