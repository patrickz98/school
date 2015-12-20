import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;


/**
 * Ein Sessel, der manipuliert werden kann und sich selbst auf einer Leinwand zeichnet.
 *
 * @author Claus Albowski
 * @version 2.2  (aug 07)
 */
public class Sessel extends Moebel
{

    /**
     * Erzeuge einen neuen Sessel mit einer Standardfarbe und Standardgroesse
     * an einer Standardposition. (Standardkonstruktor)
     */
    public Sessel()
    {
        xPosition = 250;
        yPosition = 90;
        farbe = "schwarz";
        orientierung = 0;
        istSichtbar = false;
        breite = 60;
        tiefe  = 60;
    }

    /**
     * Berechnet das zu zeichnende Shape anhand der gegebenen Daten
     */
    protected Shape gibAktuelleFigur()
    {
        // einen GeneralPath definieren
        GeneralPath sessel = new GeneralPath();
        Rectangle2D linkeSesselLehne = new Rectangle2D.Double(0, 0, breite/6, tiefe);
        Rectangle2D rechteSesselLehne = new Rectangle2D.Double(5*breite/6, 0, breite/6, tiefe);
        Rectangle2D sitzFlaeche = new Rectangle2D.Double(breite/6, tiefe/6, 2*breite/3, tiefe-tiefe/6);
        Rectangle2D ruecken = new Rectangle2D.Double(breite/6, 0, 2*breite/3, tiefe/6);
        sessel.append(linkeSesselLehne,false);
        sessel.append(rechteSesselLehne,false);
        sessel.append(sitzFlaeche,false);
        sessel.append(ruecken,false);
        return transformiere(sessel);
    }

}
