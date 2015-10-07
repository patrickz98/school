import java.awt.Shape;
import java.awt.geom.*;
import java.util.*;
import java.lang.Math;

/**
 * Beschreiben Sie hier die Klasse Schrankwand.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Stuhl_Gruppe extends Moebel
{
    private Tisch tisch;
    private ArrayList stuehle;
    
    /**
     * Konstruktor für Objekte der Klasse Schrankwand
     */
    public Stuhl_Gruppe(int anzahl_stuele)
    {
        double pi = Math.PI;
        int range = pi * 100 * 2;
        
        
        farbe = "rot";
        
        
        // koordinaten fuer die Stuele
        stuehle = new ArrayList();
        stuehle.add( new Stuhl(0, 100, farbe) );
        stuehle.add( new Stuhl(100, 0, farbe) );
        stuehle.add( new Stuhl(100, 200, farbe) );
        stuehle.add( new Stuhl(200, 100, farbe) );
        
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
        for (int x = 0; x < 4; x++)
        {
            // datenabruf aus stuehle[] array
            schrankwand.append(((Stuhl) stuehle.get(x)).gibAktuelleFigur(), false);
        }
        
        // rueckgabe der Stuhl-Gruppe als ein obj.
        return transformiere(schrankwand);
    }
}
