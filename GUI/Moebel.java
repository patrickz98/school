import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * Die Klasse Moebel definiert die gemeinsamen Eigenschaften aller Mbelklassen.
 *
 * @author Claus Albowski
 * @version 2.2  (aug 07)
 */
public abstract class Moebel implements Serializable
{
    protected int xPosition;
    protected int yPosition;
    protected int orientierung;
    protected String farbe;
    protected boolean istSichtbar;
    protected int breite;
    protected int tiefe;

    // Kein Konstruktor !!!!


    /**
     * Diese Methode ist der Kern der Abstraktion.
     */
    protected abstract Shape gibAktuelleFigur();

    /**
     * Mache dieses Objekt sichtbar. Wenn es bereits sichtbar ist, tue nichts.
     */
    public void zeige()
    {
        if (!istSichtbar)
        {
            istSichtbar = true;
            zeichne();
        }
    }

    /**
     * transformiert das aktuelle Shape.
     */
    protected Shape transformiere(Shape shape)
    {
        AffineTransform t = new AffineTransform();
        t.translate(xPosition, yPosition);
        Rectangle2D umriss = shape.getBounds2D();
        t.rotate(Math.toRadians(orientierung),umriss.getX()+umriss.getWidth()/2,umriss.getY()+umriss.getHeight()/2);
        return  t.createTransformedShape(shape);
    }

    protected Shape gibUmriss()
    {
      Shape shape=gibAktuelleFigur();
      Rectangle2D umriss = shape.getBounds2D();
      return umriss;
    }

    /**
     * Mache dieses Objekt unsichtbar. Wenn es bereits unsichtbar ist, tue nichts.
     */
    public void verberge()
    {
        loesche(); // "tue nichts" wird in loesche() abgefangen.
        istSichtbar = false;
    }

    /**
     * Drehe auf den angegebenen Winkel
     */
    public void dreheAuf(int neuerWinkel)
    {
        loesche();
        orientierung = neuerWinkel;
        zeichne();
    }

    /**
     * Bewege dieses Objekt horizontal um 'entfernung' Bildschirmpunkte.
     */
    public void bewegeHorizontal(int entfernung)
    {
        loesche();
        xPosition += entfernung;
        zeichne();
    }

    /**
     * Bewege dieses objekt vertikal um 'entfernung' Bildschirmpunkte.
     */
    public void bewegeVertikal(int entfernung)
    {
        loesche();
        yPosition += entfernung;
        zeichne();
    }


    /**
     * Aendere die Farbe dieses Objektes in 'neueFarbe'.
     * Gueltige Angaben sind "rot", "gelb", "blau", "gruen",
     * "lila" und "schwarz".
     */
    public void aendereFarbe(String neueFarbe)
    {
        loesche();
        farbe = neueFarbe;
        zeichne();
    }

    /**
     * Zeichne dieses Objekt mit seinen aktuellen Werten auf den Bildschirm.
     */
    private void zeichne()
    {
        if (istSichtbar)
        {
            Shape figur = gibAktuelleFigur();
            Leinwand leinwand = Leinwand.gibLeinwand();
            leinwand.zeichne (
              this,           // leinwand kennt das Objekt
              farbe,          // definiert seine Zeichenfarbe
              figur);         // definiert seinen grafischen Aspekt
            leinwand.warte(10);
        }
    }

    /**
     * Loesche dieses Objekt vom Bildschirm.
     */
    private void loesche()
    {
        if (istSichtbar)
        {
            Leinwand leinwand = Leinwand.gibLeinwand();
            leinwand.entferne(this);
        }
    }
}
