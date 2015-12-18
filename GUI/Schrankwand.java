import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


/**
 * Eine Schrankwand, die manipuliert werden kann und sich selbst auf einer Leinwand zeichnet.
 *
 * @author Claus Albowski
 * @version  (feb 11)
 */
public class Schrankwand extends Moebel
{

    private ArrayList<Schrank> schraenke;
    private int anzahl;

    /**
     * Erzeuge eine neue Schrankwand mit einer Standardfarbe und Standardgroesse
     * an einer Standardposition. (Standardkonstruktor)
     */
     public Schrankwand()
     {
         xPosition = 10;
         yPosition = 10;
         farbe = "schwarz";
         orientierung = 0;
         istSichtbar = false;
         breite = anzahl*60;
         tiefe  = 37;
         initSchraenke(3);
     }

    public Schrankwand(int anzahl)
    {
        xPosition = 10;
        yPosition = 10;
        farbe = "schwarz";
        orientierung = 0;
        istSichtbar = false;
        breite = anzahl*60;
        tiefe  = 37;
        initSchraenke(anzahl);
    }

    /**
     * Erzeuge eine neue Schrankwand.
     * Konstruktor, bei dem alle Attribute gesetzt werden können.
     */
    public Schrankwand(int xPosition,
                int yPosition,
                String farbe,
                int orientierung,
                int breite,
                int tiefe,
                int anzahl)
                {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.farbe = farbe;
        this.orientierung = orientierung;
        istSichtbar = false;
        this.breite = breite;
        this.tiefe  = tiefe;
        initSchraenke(anzahl);
    }

    /**
     * initialisiert die Schrankobjekte.
     * Das sollte im Konstruktor geschehen,  wird aber wegen der zwei Konstruktoren
     * in eine eigene Methode ausgegliedert.
     */
    private void initSchraenke(int anzahl)
    {
        this.anzahl=anzahl;
        schraenke=new ArrayList();
        for (int i=0; i<anzahl; i++)
        schraenke.add(new Schrank(i*breite/anzahl, 0, farbe, 0, breite/anzahl, tiefe));
    }

    /**
     * Berechnet das zu zeichnende Shape anhand der gegebenen Daten
     */
    protected Shape gibAktuelleFigur()
    {
        // einen GeneralPath definieren
        GeneralPath schrankwand = new GeneralPath();
        for (int i=0; i<anzahl; i++)
        // cast notwendig!!!
        schrankwand.append(schraenke.get(i).gibAktuelleFigur(), false);
        return transformiere(schrankwand);
    }
}
