import java.awt.Shape;
import java.awt.geom.*;

/**
 * Beschreiben Sie hier die Klasse Schrankwand.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Schrankwand extends Moebel
{
    Schrank schrank1;
    Schrank schrank2;
    Schrank schrank3;
    
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
        schrank1 = new Schrank(0, 0, farbe, orientierung, breite/3, tiefe);
        schrank2 = new Schrank(breite/3, 0, farbe, orientierung, breite/3, tiefe);
        schrank3 = new Schrank(2*breite/3, 0, farbe, orientierung, breite/3, tiefe);
    }
    
    /**
    * Berechnet das zu zeichnende Shape anhand der gegebenen Daten
    */
    protected Shape gibAktuelleFigur()
    {
        GeneralPath schrankwand = new GeneralPath();

        schrankwand.append(schrank1.gibAktuelleFigur(), false);
        schrankwand.append(schrank2.gibAktuelleFigur(), false);
        schrankwand.append(schrank3.gibAktuelleFigur(), false);
        
        return transformiere(schrankwand);
//        return schrankwand;
    }
}
