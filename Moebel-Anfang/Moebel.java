import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.io.*;
/**
 * Ein Moebel, der manipuliert werden kann und sich selbst auf einer Leinwand zeichnet.
 *
 * @author Claus Albowski
 * @version 2.2  (aug 07)
 */
public abstract class Moebel
{
    protected int xPosition;
    protected int yPosition;
    protected int orientierung;
    protected String farbe;
    protected boolean istSichtbar;
    protected int breite;
    protected int tiefe;
    
    /**
     * Erzeuge einen neuen Moebel mit einer Standardfarbe und Standardgroesse
     * an einer Standardposition. (Standardkonstruktor)
     */
    public Moebel() {
        xPosition = 160;
        yPosition = 80;
        farbe = "blau";
        orientierung = 0;
        istSichtbar = false;
        breite = 40;
        tiefe  = 40;
    }
<<<<<<< HEAD
=======

>>>>>>> master
    // Transformfuntion für Shaps
    protected Shape transformiere(Shape shape)
    {
        AffineTransform t = new AffineTransform();
<<<<<<< HEAD
        t.translate(xPosition, yPosition);
        Rectangle2D umriss = shape.getBounds2D();
        t.rotate(Math.toRadians(orientierung),
        umriss.getX()+umriss.getWidth()/2,
        umriss.getY()+umriss.getHeight()/2);
        return t.createTransformedShape(shape);
    }
=======

        t.translate(xPosition, yPosition);

        Rectangle2D umriss = shape.getBounds2D();

        t.rotate(Math.toRadians(orientierung),
        umriss.getX()+umriss.getWidth()/2,
        umriss.getY()+umriss.getHeight()/2);

        return t.createTransformedShape(shape);
    }

>>>>>>> master
    /**
     * Berechnet das zu zeichnende Shape anhand der gegebenen Daten
     * [ Zum Anzeigen der Attributwerte �ber Inspect muss hier die Sichtbarkeit
     *  auf public gesetzt werden. ]
     */
    protected abstract Shape gibAktuelleFigur();
<<<<<<< HEAD
=======

>>>>>>> master
    /**
     * Mache dieses Objekt sichtbar. Wenn es bereits sichtbar ist, tue nichts.
     */
    public void zeige() {
        if (!istSichtbar) {
            istSichtbar = true;
            zeichne();
        }
    }
<<<<<<< HEAD
=======

>>>>>>> master
    /**
     * Mache dieses Objekt unsichtbar. Wenn es bereits unsichtbar ist, tue nichts.
     */
    public void verberge() {
        loesche(); // "tue nichts" wird in loesche() abgefangen.
        this.istSichtbar = false;
    }
<<<<<<< HEAD
=======

>>>>>>> master
    /**
     * Drehe auf den angegebenen Winkel
     */
    public void dreheAuf(int neuerWinkel) {
        loesche();
        orientierung = neuerWinkel;
        zeichne();
    }
<<<<<<< HEAD
=======

>>>>>>> master
    /**
     * Bewege dieses Objekt horizontal um 'entfernung' Bildschirmpunkte.
     */
    public void bewegeHorizontal(int entfernung) {
        loesche();
        xPosition += entfernung;
        zeichne();
    }

    /**
     * Bewege dieses objekt vertikal um 'entfernung' Bildschirmpunkte.
     */
    public void bewegeVertikal(int entfernung) {
        loesche();
        yPosition += entfernung;
        zeichne();
    }
<<<<<<< HEAD
=======


>>>>>>> master
    /**
     * Aendere die Farbe dieses Objektes in 'neueFarbe'.
     * Gueltige Angaben sind "rot", "gelb", "blau", "gruen",
     * "lila" und "schwarz".
     */
    public void aendereFarbe(String neueFarbe) {
        loesche();
        farbe = neueFarbe;
        zeichne();
    }
<<<<<<< HEAD
=======

>>>>>>> master
    /**
     * Zeichne dieses Objekt mit seinen aktuellen Werten auf den Bildschirm.
     */
    private void zeichne() {
        if (istSichtbar) {
            Shape figur = gibAktuelleFigur();
            Leinwand leinwand = Leinwand.gibLeinwand();
            leinwand.zeichne (
              this,           // leinwand kennt das Objekt
              farbe,          // definiert seine Zeichenfarbe
              figur);         // definiert seinen grafischen Aspekt
            leinwand.warte(10);
        }
    }
<<<<<<< HEAD
=======

>>>>>>> master
    /**
     * Loesche dieses Objekt vom Bildschirm.
     */
    private void loesche() {
        if (istSichtbar){
            Leinwand leinwand = Leinwand.gibLeinwand();
            leinwand.entferne(this);
        }
    }
}
