import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;

/**
 * Ein Tisch, der manipuliert werden kann und sich selbst auf einer Leinwand zeichnet.
 * 
 * @author Claus Albowski
 * @version 2.2  (aug 07)
 */
public class Tisch extends Moebel
{
    /**
     * Erzeuge einen neuen Stuhl mit einer Standardfarbe und Standardgroesse
     * an einer Standardposition. (Standardkonstruktor)
     */
    public Tisch()  {
        xPosition = 120;
        yPosition = 150;
        orientierung = 0;
        farbe = "rot";
        istSichtbar = false;
        breite = 120;
        tiefe  = 100;
    }
    
    /**
     * Erzeuge einen neuen Stuhl mit einer Standardfarbe und Standardgroesse
     * an einer Standardposition. (Standardkonstruktor)
     */
    public Tisch(int x, int y, int diameter, String color)  {
        xPosition = x;
        yPosition = y;
        orientierung = 0;
        farbe = color;
        istSichtbar = false;
        breite = diameter;
        tiefe  = diameter;
    }
    
    /**
     * Berechnet das zu zeichnende Shape anhand der gegebenen Daten
     * [ Zum Anzeigen der Attributwerte �ber Inspect muss hier die Sichtbarkeit 
     *  auf public gesetzt werden. ]
     */
    protected Shape gibAktuelleFigur()
    {
        // definieren
        Shape tisch = new Ellipse2D.Double(0 , 0, breite, tiefe);
        
        return  transformiere(tisch);
    }
}
