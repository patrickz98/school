import java.awt.Shape;
import java.awt.geom.*;
import java.util.*;

/**
 * Beschreiben Sie hier die Klasse Schrankwand.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Stuhl_Gruppe extends Moebel
{
    private Tisch tisch;
    private Stuhl[] stuehle;
    
    /**
     * Konstruktor für Objekte der Klasse Schrankwand
     */
    public Stuhl_Gruppe()
    {
        xPosition = 40;
        yPosition = 80;
        farbe = "blau";
        orientierung = 0;
        istSichtbar = false;
        breite = 180;
        tiefe = 37;
        
        // koordinaten fuer die Stuele
        int[][] conf = new int[4][2];
        conf[0] = new int[]{0, 100};
        conf[1] = new int[]{100, 0};
        conf[2] = new int[]{100, 200};
        conf[3] = new int[]{200, 100};
        
        // Tisch fuer die Stuhl-Gruppe
        tisch = new Tisch(75, 75, 100);
        stuehle = new Stuhl[4];
        
        // generiert die Stuehle
        for (int x = 0; x < 4; x++)
        {
            // datenabruf fuer die position der Stuehle aus conf
            stuehle[x] = new Stuhl(conf[x][0], conf[x][1], "rot");
        }
    }
    
    /**
    * Berechnet das zu zeichnende Shape anhand der gegebenen Daten
    */
    protected Shape gibAktuelleFigur()
    {
        GeneralPath schrankwand = new GeneralPath();

        // hinzufuegen des Tisches
        schrankwand.append(tisch.gibAktuelleFigur(), false);
        
        // hinzufuergen der Stuehle
        for (int x = 0; x < 4; x++)
        {
            // datenabruf aus stuehle[] array
            schrankwand.append(stuehle[x].gibAktuelleFigur(), false);
        }
        
        // rueckgabe der Stuhl-Gruppe als ein obj.
        return transformiere(schrankwand);
    }
}
