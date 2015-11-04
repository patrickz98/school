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
    private ArrayList<Stuhl> stuehle;
    private int anzahl_stuele;

    /**
     * Funktion die die Stuehle im Kreis anordnet
     */
    public void stuhl_kreis(int anzahl_stuele, boolean up_down)
    {
        // Durchmesser des Tisches
        int d = 200;
        
        // Intervall der Stuehle auf der x-Achse
        int x_intervall = (int) d / anzahl_stuele;

        // Kordinaten fuer die Stuehle
        int x, y;
        
        // Es kann nur ein Halbkreis modelliert werden. (Oben/Unten)
        int placement;

        // Einstellung der Position
        if (up_down)
        {
            // Unten
            x = 0;
            placement = -1;
        }
        else
        {
            // Oben
            x = x_intervall;
            placement = 1;
        }

        // Erstellung der Stuehle
        for (int z = 0; z < anzahl_stuele; z++)
        {
            // funktion zu y berechnung auf Basis eines Halbkreises
            y = (int) Math.sqrt(d * x - Math.pow(x, 2));

            stuehle.add( new Stuhl(x, (y * placement) + 100, farbe) );
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

        stuehle = new ArrayList<Stuhl>();

        // koordinaten fuer die Stuele.
        // Ungerade- / Geradezahl
        if (input_stuele % 2 == 0)
        {
            stuhl_kreis(input_stuele / 2, true);
            stuhl_kreis(input_stuele / 2, false);
        }
        else
        {
            stuhl_kreis((input_stuele - 1) / 2 + 1, true);
            stuhl_kreis((input_stuele - 1) / 2,     false);
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
