import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;


/**
 * Ein Schrank, der manipuliert werden kann und sich selbst auf einer Leinwand zeichnet.
 *
 * @author Claus Albowski
 * @version 2.3  (sept 07)
 */
public class Schrank extends Moebel
{

    /**
     * Erzeuge einen neuen Schrank mit einer Standardfarbe und Standardgroesse
     * an einer Standardposition. (Standardkonstruktor)
     */
    public Schrank()
    {
        xPosition = 330;
        yPosition = 50;
        farbe = "schwarz";
        orientierung = 0;
        istSichtbar = false;
        breite = 60;
        tiefe  = 37;
    }

    /**
     * Erzeuge einen neuen Schrank.
     */
    public Schrank(int xPosition,
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
     * [ Zum Anzeigen der Attributwerte ber Inspect muss hier die Sichtbarkeit
     *  auf public gesetzt werden. ]
     */
    protected Shape gibAktuelleFigur()
    {
        // einen GeneralPath definieren
        GeneralPath schrank = new GeneralPath();
        Rectangle2D rahmen = new Rectangle2D.Double(0, 0, breite, tiefe);
        schrank.append(rahmen, false);
        Line2D diagonale1 = new Line2D.Double(0, 0, breite, tiefe);
        schrank.append(diagonale1, false);
        Line2D diagonale2 = new Line2D.Double(0, tiefe, breite, 0);
        schrank.append(diagonale2, false);
        return transformiere(schrank);
    }
}
