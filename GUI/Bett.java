import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;


/**
 * Ein Bett, das manipuliert werden kann und sich selbst auf einer Leinwand zeichnet.
 *
 * @author Claus Albowski
 * @version 2.3  (sept 07)
 */
public class Bett extends Moebel
{

    /**
     * Erzeuge ein neues Bett mit einer Standardfarbe und Standardgroesse
     * an einer Standardposition. (Standardkonstruktor)
     */
    public Bett()
    {
        xPosition = 10;
        yPosition = 180;
        farbe = "schwarz";
        orientierung = 0;
        istSichtbar = false;
        breite = 200;
        tiefe  = 200;
    }

    /**
     * Erzeuge ein neues Bett.
     */
    public Bett(int xPosition,
                int yPosition,
                String farbe,
                int orientierung,
                int breite,
                int tiefe)
                {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.farbe = farbe;
        this.orientierung = orientierung;
        istSichtbar = false;
        this.breite = breite;
        this.tiefe  = tiefe;
    }

    /**
     * Berechnet das zu zeichnende Shape anhand der gegebenen Daten
     * [ Zum Anzeigen der Attributwerte ueber Inspect muss hier die Sichtbarkeit
     *  auf public gesetzt werden. ]
     */
    protected Shape gibAktuelleFigur()
    {
        // einen GeneralPath definieren
        GeneralPath bett = new GeneralPath();
        Rectangle2D rahmen = new Rectangle2D.Double(0, 0, breite, tiefe);
        bett.append(rahmen, false);
        Line2D falte1 = new Line2D.Double(0, 0, breite, tiefe/2);
        bett.append(falte1, false);
        Line2D mitte = new Line2D.Double(0, tiefe/2, breite, tiefe/2);
        bett.append(mitte, false);
        Line2D falte2 = new Line2D.Double(0, tiefe, breite, tiefe/2);
        bett.append(falte2, false);
        return transformiere(bett);
    }
}
