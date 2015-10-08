import java.awt.Shape;
import java.awt.geom.*;
import java.util.*;
import java.lang.Math.*;

/**
 * Beschreiben Sie hier die Klasse Schrankwand.
 *
 * @author Patrick Zierahn
 */
public class Stuhl_Gruppe extends Moebel
{
    private Tisch tisch;
    private ArrayList stuehle;
    private int anzahl_stuele;

    /**
     * Funktion die die Stuehle im Kreis anordnet
     */
    public void stuehl_circle(int anzahl_stuele, boolean up_down)
    {
        int d = 200;
        int x_intervall = (int) d / anzahl_stuele;

        int x;
        int y;
        int displacement;

        if (up_down)
        {
            x = 0;
            displacement = -1;
        }
        else
        {
            x = x_intervall;
            displacement = 1;
        }

        for (int z = 0; z < anzahl_stuele; z++)
        {
            y = (int) Math.sqrt(d * x - Math.pow(x, 2));

            stuehle.add( new Stuhl(x, (y * displacement) + 100, farbe) );
            x += x_intervall;
        }
    }

    /**
     * Konstruktor für Objekte der Klasse Schrankwand
     */
    public Stuhl_Gruppe(int input_stuele)
    {
        super.farbe = "rot";
        anzahl_stuele = input_stuele;

        stuehle = new ArrayList();

        // koordinaten fuer die Stuele
        if (input_stuele % 2 == 0)
        {
            stuehl_circle(input_stuele / 2, true);
            stuehl_circle(input_stuele / 2, false);
        }
        else
        {
            stuehl_circle((input_stuele - 1) / 2 + 1, true);
            stuehl_circle((input_stuele - 1) / 2,     false);
        }

        // Tisch fuer die Stuhl-Gruppe
        tisch = new Tisch(75, 75, 100, farbe);
    }

    /**
    * Berechnet das zu zeichnende Shape anhand der gegebenen Daten
    */
    protected Shape gibAktuelleFigur()
    {
        GeneralPath schrankwand = new GeneralPath();

        // hinzufuegen des Tisches
        schrankwand.append(tisch.gibAktuelleFigur(), false);

        // hinzufuergen der Stuehle
        for (int x = 0; x < anzahl_stuele; x++)
        {
            // datenabruf aus dem stuehle array
            schrankwand.append(((Stuhl) stuehle.get(x)).gibAktuelleFigur(), false);
        }

        // rueckgabe der Stuhl-Gruppe als ein obj.
        return transformiere(schrankwand);
    }
}
