import java.util.*;
import java.io.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Controller
{
	private static Controller controller = null;

	public static Controller gibController()
	{
		if (controller == null) controller = new Controller();
		return controller;
	}

	private ArrayList<Moebel> moebel = null;
	private int ausgewaehlt;

	private Controller()
	{
		moebel = new ArrayList<Moebel>();
		ausgewaehlt = 0;
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

	protected Moebel StringToMoebel(String typ)
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
		}

		return neu;
	}

	public void neu(String typ)
	{
		Moebel neu = StringToMoebel(typ);

		moebel.add(neu);
		neu.zeige();
		ausgewaehlt = moebel.size() - 1;
	}

	public void neuXY(String typ, int x, int y)
	{
		Moebel neu = StringToMoebel(typ);

		neu.aenderePosition(x, y);
		moebel.add(neu);
		neu.zeige();
		ausgewaehlt = moebel.size() - 1;
	}

	public void drehe(int input)
	{
		if(ausgewaehltes() != null)
		{
			int original = ausgewaehltes().gibWinkel();
			ausgewaehltes().dreheAuf(original + input);
		}
	}

	public void aendereFarbe(String farbe)
	{
		if (ausgewaehltes() != null && !farbe.equals(""))
		{
			ausgewaehltes().aendereFarbe(farbe);
		}
	}

	public void loeschen()
	{
		 if (ausgewaehlt >= 0)
		 {
			 ausgewaehltes().verberge();
			 moebel.remove(ausgewaehltes());
			 if (moebel.size() == 0) ausgewaehlt = 0;
			 else if (ausgewaehlt >= moebel.size()) ausgewaehlt = 0;
		 }
	}

	public void duplicate() throws CloneNotSupportedException
	{
		if (ausgewaehlt >= 0)
		{
			moebel.add(ausgewaehltes().clone());

			ausgewaehlt = moebel.size() - 1;

			ausgewaehltes().bewegeHorizontal(10);
			ausgewaehltes().bewegeVertikal(10);

			ausgewaehltes().zeige();
		}
	}

	int delay = 10;
	Rectangle frameSize = null;
	int border = 20;

	// function which moves the Moebels in visible
	public void sort(int m, int delay)
	{
		int x = 1;

		// Oben-Links
		while(moebel.get(m).gibUmriss().getBounds2D().getX() < border &&
			  moebel.get(m).gibUmriss().getBounds2D().getY() < border)
		{
			ausgewaehltes().bewegeHorizontal(x);
			ausgewaehltes().bewegeVertikal(x);

			warte(delay);
			x++;
		}

		// Unten-Links
		while(moebel.get(m).gibUmriss().getBounds2D().getX() < border &&
			  moebel.get(m).gibUmriss().getBounds2D().getMaxY() > frameSize.height - border - 50)
		{
			ausgewaehltes().bewegeHorizontal(x);
			ausgewaehltes().bewegeVertikal(-x);

			warte(delay);
			x++;
		}

		// Oben-Rechts
		while(moebel.get(m).gibUmriss().getBounds2D().getMaxX() > frameSize.width - border &&
			  moebel.get(m).gibUmriss().getBounds2D().getY() < border)
		{
			ausgewaehltes().bewegeHorizontal(-x);
			ausgewaehltes().bewegeVertikal(x);

			warte(delay);
			x++;
		}

		// Unten-Rechts
		// border is wrong because of the JMenuBar --> -50
		while(moebel.get(m).gibUmriss().getBounds2D().getMaxX() > frameSize.width - border &&
			  moebel.get(m).gibUmriss().getBounds2D().getMaxY() > frameSize.height - border - 50)
		{
			ausgewaehltes().bewegeHorizontal(-x);
			ausgewaehltes().bewegeVertikal(-x);

			warte(delay);
			x++;
		}

		// Links
		while(moebel.get(m).gibUmriss().getBounds2D().getX() < border)
		{
			ausgewaehltes().bewegeHorizontal(x);

			warte(delay);
			x++;
		}

		// Oben
		while(moebel.get(m).gibUmriss().getBounds2D().getY() < border)
		{
			ausgewaehltes().bewegeVertikal(x);

			warte(delay);
			x++;
		}

		// Rechts
		while(moebel.get(m).gibUmriss().getBounds2D().getMaxX() > frameSize.width - border)
		{
			ausgewaehltes().bewegeHorizontal(-x);

			warte(delay);
			x++;
		}

		// Unter
		// border is wrong because of the JMenuBar --> -50
		while(moebel.get(m).gibUmriss().getBounds2D().getMaxY() > frameSize.height - border - 50)
		{
			ausgewaehltes().bewegeVertikal(-x);

			warte(delay);
			x++;
		}
	}

	public void sortAll(Rectangle size)
	{
		frameSize = size;

		// sort Thread for all object
		Thread mySortThread = new Thread(new Runnable()
		{
			public void run()
			{
				for(int m = 0; m < moebel.size(); m++)
				{
					ausgewaehlt = m;
					sort(m, delay);
				}
			}
		});

		mySortThread.start();
	}

	public void sortOne(Rectangle size)
	{
		frameSize = size;

		// sort Thread for one object
		Thread mySortThread = new Thread(new Runnable()
		{
			public void run()
			{
				sort(ausgewaehlt, delay);
			}
		});

		mySortThread.start();
	}


	/**
	 * Maussteuerung
	 */
	private int xMaus = 0;
	private int yMaus = 0;
	public void mausposition(int x, int y)
	{
		if (moebel.size() == 0) return;
		for (int i = 0; i < moebel.size(); i++)
		if ((x > moebel.get(i).gibUmriss().getBounds2D().getX()) &&
			(x < moebel.get(i).gibUmriss().getBounds2D().getMaxX()) &&
			(y > moebel.get(i).gibUmriss().getBounds2D().getY()) &&
			(y < moebel.get(i).gibUmriss().getBounds2D().getMaxY()))
		{
			 ausgewaehlt = i;
			 xMaus = x;
			 yMaus = y;
		}
	}

	public void angeklickt(int x, int y)
	{
		if (moebel.size() == 0) return;
		for (int i = 0; i < moebel.size(); i++)
		if ((x > moebel.get(i).gibUmriss().getBounds2D().getX()) &&
			(x < moebel.get(i).gibUmriss().getBounds2D().getMaxX()) &&
			(y > moebel.get(i).gibUmriss().getBounds2D().getY()) &&
			(y < moebel.get(i).gibUmriss().getBounds2D().getMaxY()))
		{
			 ausgewaehlt = i;
			 xMaus = x;
			 yMaus = y;

			 return;
		}
	}

	public void verschiebeAuf(int x, int y)
	{
		ausgewaehltes().bewegeHorizontal(x - xMaus);
		ausgewaehltes().bewegeVertikal(y - yMaus);
		xMaus = x;
		yMaus = y;
	}

	public boolean touched(int x, int y)
	{
		if (moebel.size() == 0) return false;
		for (int i = 0; i < moebel.size(); i++)
		if ((x > moebel.get(i).gibUmriss().getBounds2D().getX()) &&
			(x < moebel.get(i).gibUmriss().getBounds2D().getMaxX()) &&
			(y > moebel.get(i).gibUmriss().getBounds2D().getY()) &&
			(y < moebel.get(i).gibUmriss().getBounds2D().getMaxY()))
		{
			return true;
		}
		return false;
	}

	// save
	public void sichern(String dateiName)
	throws IOException, FileNotFoundException
	{
		// if (dateiName.equals("")) dateiName = "default.save";
		if (dateiName.equals("")) return;

		ObjectOutputStream ausgabe = new ObjectOutputStream(new FileOutputStream(dateiName));

		for (int i = 0; i < moebel.size(); i++)
		{
			ausgabe.writeObject(moebel.get(i));
		}

		ausgabe.close();
	}

	// open
	public void holen(String dateiName)
	throws ClassNotFoundException, IOException, FileNotFoundException
	{
		// if (dateiName.equals("")) dateiName = "default.save";
		if (dateiName.equals("")) return;

		for (int i = 0; i < moebel.size(); i++)
		{
			// Vorhandenes loeschen
			moebel.get(i).verberge();
		}

		moebel.clear();
		ausgewaehlt = 0;

		ObjectInputStream eingabe = new ObjectInputStream( new FileInputStream(dateiName) );
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
				obj.verberge();
				obj.zeige();
			}
		}
		eingabe.close();
	}

	public void warte(int millisekunden)
	{
		try
		{
			Thread.sleep(millisekunden);
		}
		catch (Exception e)
		{
			// Exception ignorieren
		}
	}
}
