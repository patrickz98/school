import java.util.ArrayList;
import java.io.*;

public class Controller
{
	private static Controller controller = null;

	public static Controller gibController()
	{
		if (controller == null) controller = new Controller();
		return controller;
	}

    public static void beenden()
	{
		Leinwand.gibLeinwand().setzeSichtbarkeit(false);
	}

    // Instanzen:
	private ArrayList<Moebel> moebel = null;
	private int ausgewaehlt;

	private Controller()
	{
		moebel = new ArrayList<Moebel>();
		ausgewaehlt = -1;
	}

	private Moebel ausgewaehltes()
	{
		if (ausgewaehlt < 0)
		{
			return null;
		}

		if (ausgewaehlt >= moebel.size())
		{
			return null;
		}
		else
		{
			return moebel.get(ausgewaehlt);
		}
	}

	public String neu(String typ)
	{
        Moebel neu = null;

        switch (typ)
        {
            case "Stuhl":
    			neu = new Stuhl();
    			break;
            case "Tisch":
                neu = new Tisch();
                break;
            case "Bett":
    			neu = new Bett();
    			break;
            case "Schrank":
    			neu = new Schrank();
    			break;
            case "Schrankwand":
    			neu = new Schrankwand();
    			break;
            case "Sessel":
        		neu = new Sessel();
    			break;
    		default:
                return "keines";
        }

		moebel.add(neu);
		neu.zeige();
		ausgewaehlt = moebel.size()-1;
		return ausgewaehltes().getClass().getName();
	}

	public void waagerecht(String inhalt)
	{
		 int weite = StringToInt(inhalt);
		 if (ausgewaehltes() != null) ausgewaehltes().bewegeHorizontal(weite);
	}

	public void drehe(int input)
	{
		 int original = ausgewaehltes().gibWinkel();
		 if (ausgewaehltes() != null) ausgewaehltes().dreheAuf(original + input);
	}

	public void aendereFarbe(String farbe)
	{
		if (ausgewaehltes() != null && !farbe.equals(""))
		{
			ausgewaehltes().aendereFarbe(farbe);
		}
	}

    public String voriges()
	{
		String aktuell = "keines";

		if (moebel.size() > 0)
		{
			ausgewaehlt--;
			if (ausgewaehlt < 0) ausgewaehlt = moebel.size() - 1;
			aktuell = ausgewaehltes().getClass().getName();
		}
		return aktuell;
	}
	public String naechstes()
	{
		String aktuell = "keines";
		if (moebel.size() > 0)
		{
			ausgewaehlt++;
			if (ausgewaehlt >= moebel.size()) ausgewaehlt = 0;
			aktuell = ausgewaehltes().getClass().getName();
		}
		return aktuell;
	}

	public void loeschen()
	{
		 if (ausgewaehlt >= 0)
		 {
			 ausgewaehltes().verberge();
			 moebel.remove(ausgewaehltes());
			 if (moebel.size() == 0) ausgewaehlt = -1;
			 else if (ausgewaehlt >= moebel.size()) ausgewaehlt = 0;
		 }
	}

	/**
	 * Maussteuerung
	 */
	private int xMaus = 0;
	private int yMaus = 0;	// sichert die letzte Mausposition
    public void mausposition(int x,int y)
	{
		if (moebel.size() == 0) return;
        for (int i=0; i < moebel.size(); i++)
        if ((x > moebel.get(i).gibUmriss().getBounds2D().getX())
				 && (x < moebel.get(i).gibUmriss().getBounds2D().getMaxX())
				 && (y > moebel.get(i).gibUmriss().getBounds2D().getY())
				 && (y < moebel.get(i).gibUmriss().getBounds2D().getMaxY()))
		{
			 ausgewaehlt = i;
			 xMaus = x;
			 yMaus = y;
		}
	}
	public void angeklickt(int x, int y)
	{
		if (moebel.size() == 0) return;
		for (int i=0; i < moebel.size(); i++)
		if ((x > moebel.get(i).gibUmriss().getBounds2D().getX())
				 && (x < moebel.get(i).gibUmriss().getBounds2D().getMaxX())
				 && (y > moebel.get(i).gibUmriss().getBounds2D().getY())
				 && (y < moebel.get(i).gibUmriss().getBounds2D().getMaxY()))
		{
			 ausgewaehlt = i;
			 xMaus=x;
			 yMaus=y;
		}
	}

	public void verschiebeAuf(int x, int y)
	{
		ausgewaehltes().bewegeHorizontal(x-xMaus);
		ausgewaehltes().bewegeVertikal(y-yMaus);
		xMaus=x;
		yMaus=y;
	}

    public boolean touched(int x, int y)
    {
        if (moebel.size() == 0) return false;
		for (int i=0; i < moebel.size(); i++)
		if ((x > moebel.get(i).gibUmriss().getBounds2D().getX())
				 && (x < moebel.get(i).gibUmriss().getBounds2D().getMaxX())
				 && (y > moebel.get(i).gibUmriss().getBounds2D().getY())
				 && (y < moebel.get(i).gibUmriss().getBounds2D().getMaxY()))
		{
            return true;
		}
        return false;
    }


	/**
	 * Dateizugriffe
	 */
	public void sichern(String dateiName)
	throws IOException, FileNotFoundException
	{
		if (dateiName.equals("")) dateiName = "default.save";

        ObjectOutputStream ausgabe = new ObjectOutputStream(new FileOutputStream(dateiName));

        for (int i = 0; i < moebel.size(); i++)
        {
            ausgabe.writeObject(moebel.get(i));
        }

        ausgabe.close();
	}

	public void holen(String dateiName)
	throws ClassNotFoundException, IOException, FileNotFoundException
	{
		if (dateiName.equals("")) dateiName = "default.save";

        for (int i = 0; i < moebel.size(); i++)
        {
            // Vorhandenes loeschen
            moebel.get(i).verberge();
        }

		moebel.clear();
		ausgewaehlt = -1;

        ObjectInputStream eingabe = new ObjectInputStream( new FileInputStream(dateiName));
		Moebel obj = null;
		boolean fertig = false;

        while (!fertig)
		{
			try
            {
                obj = (Moebel) eingabe.readObject();
            }
			catch (EOFException e)
            {
                fertig = true;
            }

            if (!fertig)
			{
					moebel.add(obj);
					obj.verberge(); // notwendig um zunaechst das Attribut sichtbar auf false zu setzen
					obj.zeige();
			}
		}
		eingabe.close();
	}

	public int StringToInt(String inhalt)
	{
		int wert = 0;
		try
		{
            wert = Integer.parseInt(inhalt);
		} catch (NumberFormatException e)
		{
			 // wert bleibt 0
		}
		return wert;
	}
}
