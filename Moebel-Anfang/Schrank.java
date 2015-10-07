import java.awt.Shape;
import java.awt.geom.*;

/**
 * Beschreiben Sie hier die Klasse Schrank.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Schrank extends Moebel
{
    /**
    * Erzeuge einen neuen Schrank.
    */
    public Schrank(int x, int y, String f, int o, int b, int t)
    {
        xPosition = x;
        yPosition = y;
        farbe = f;
        orientierung = o;
        istSichtbar = false;
        breite = b;
        tiefe = t;
    }

    
    /**
    * Berechnet das zu zeichnende Shape anhand der gegebenen Daten
    */
    protected Shape gibAktuelleFigur()
    {
        GeneralPath schrank = new GeneralPath();
        Shape rahmen = new Rectangle2D.Double(0, 0, breite, tiefe);
        Shape linie1 = new Line2D.Double(0, 0, breite, tiefe);
        Shape linie2 = new Line2D.Double(breite, 0, 0, tiefe);
        schrank.append(rahmen, false);
        schrank.append(linie1, false);
        schrank.append(linie2, false);
        
         return transformiere(schrank);
//        return schrank;
    }
}
