import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

/**
 * Ein Stuhl, der manipuliert werden kann und sich selbst auf einer Leinwand zeichnet.
 * 
 * @author Claus Albowski
 * @version 2.2  (aug 07)
 */
public class couch extends Moebel
{
    /**
     * Erzeuge einen neue couch mit einer Standardfarbe und Standardgroesse
     * an einer Standardposition. (Standardkonstruktor)
     */
    public couch() {
        breite = 40;
        xPosition = 160;
        yPosition = 80;
        farbe = "rot";
        orientierung = 0;
        istSichtbar = false;
        breite = 40;
        tiefe  = 40;
    }

    
    /**
     * Berechnet das zu zeichnende Shape anhand der gegebenen Daten
     * [ Zum Anzeigen der Attributwerte �ber Inspect muss hier die Sichtbarkeit 
     *  auf public gesetzt werden. ]
     */
    public Shape gibAktuelleFigur() {
        // einen GeneralPath definieren
        GeneralPath couch = new GeneralPath();
        couch.moveTo(0 , 0);
        couch.lineTo(breite, 0);
        couch.lineTo(0, breite);
        couch.lineTo(0 , 0);
        
        return transformiere(couch);
    }
}
