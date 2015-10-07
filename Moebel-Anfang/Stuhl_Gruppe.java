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

    // public Stuhl stuehl_positon()
    // {
    //
    // }

    /**
     * Konstruktor für Objekte der Klasse Schrankwand
     */
    public Stuhl_Gruppe(int imput_stuele)
    {
        super.farbe = "rot";
        anzahl_stuele = imput_stuele;

        int d = 200;
        int x = (int) d / anzahl_stuele;
        int x_posi = 0;
        int tmp_y;

        stuehle = new ArrayList();

        // koordinaten fuer die Stuele
        for (int y = 0; y < anzahl_stuele; y++)
        {
            // double quadratwurzel = Math.sqrt(2 * d * x - x ^ 2);
            tmp_y = (int) Math.sqrt(d * x_posi - Math.pow(x_posi, 2));

            System.out.println("x-orgi: " + x + " x: " + x_posi + " y: " + tmp_y);

            stuehle.add( new Stuhl(x_posi, (tmp_y * -1) + 100, farbe) );
            x_posi += x;
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
