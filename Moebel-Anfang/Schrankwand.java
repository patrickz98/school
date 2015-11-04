import java.awt.Shape;
import java.awt.geom.*;

import java.util.*;
/**
 * Beschreiben Sie hier die Klasse Schrankwand.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Schrankwand extends Moebel
{
    int anzahl;
    ArrayList<Schrank> schraenke = new ArrayList<Schrank>();
    
    /**
     * Konstruktor für Objekte der Klasse Schrankwand
     */
    public Schrankwand()
    {
        xPosition = 40;
        yPosition = 80;
        farbe = "blau";
        orientierung = 0;
        istSichtbar = false;
        breite = 180;
        tiefe = 37;
        
        
        schraenke.add(new Schrank(0, 0, farbe, orientierung, breite/3, tiefe));
        schraenke.add(new Schrank(breite/3, 0, farbe, orientierung, breite/3, tiefe));
        schraenke.add(new Schrank(2*breite/3, 0, farbe, orientierung, breite/3, tiefe));
    }
    
    public Schrankwand(int anzahl)
    {
        xPosition = 40;
        yPosition = 80;
        farbe = "blau";
        orientierung = 0;
        istSichtbar = false;
        breite = 180;
        tiefe = 37;
        
        anzahl = anzahl;
        
        for (int x = 0; x < anzahl; x++)
        {
            schraenke.add(new Schrank(x * breite / 3, 0, farbe, orientierung, breite/3, tiefe));
        }
    }
    
    /**
    * Berechnet das zu zeichnende Shape anhand der gegebenen Daten
    */
    protected Shape gibAktuelleFigur()
    {
        GeneralPath schrankwand = new GeneralPath();

        for (Iterator<Schrank> schrank = schraenke.iterator(); schrank.hasNext();)
        {
            schrankwand.append(schrank.next().gibAktuelleFigur(), false);
        }
        
        return transformiere(schrankwand);
    }
}
