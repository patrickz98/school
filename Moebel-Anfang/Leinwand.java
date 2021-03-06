import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
<<<<<<< HEAD
=======

>>>>>>> master
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.awt.geom.Point2D;
/**
 * Leinwand ist eine Klasse, die einfache Zeichenoperationen auf einer
 * leinwandartigen Zeichenfl�che erm�glicht.
 * Sie ist eine vereinfachte Version der Klasse Canvas (englisch f�r
 * Leinwand) des JDK und wurde speziell f�r das Projekt "Figuren"
 * geschrieben.
 *
 *
 * @author: Bruce Quig
 * @author: Michael K�lling (mik)
 * @author: Axel Schmolitzky
 *
 * @author: �nderungen von
 * @Java-MS Groupies
 * @hier: Uwe Debacher
 *
 * @version: 1.7 (5.12.2003)
 */
public class Leinwand implements MouseMotionListener, MouseListener
{
  // Hinweis: Die Implementierung dieser Klasse (insbesondere die
  // Verwaltung der Farben und Identit�ten der Figuren) ist etwas
  // komplizierter als notwendig. Dies ist absichtlich so, weil damit
  // die Schnittstellen und Exemplarvariablen der Figuren-Klassen
  // f�r den Lernanspruch dieses Projekts einfacher und klarer
  // sein k�nnen.
	private static Leinwand leinwandSingleton;
  /**
   * Fabrikmethode, die eine Referenz auf das einzige Exemplar
   * dieser Klasse zur�ckliefert. Wenn es von einer Klasse nur
   * genau ein Exemplar gibt, wird dieses als 'Singleton'
   * bezeichnet.
   */
<<<<<<< HEAD
	public static Leinwand gibLeinwand()
	{
	    if (leinwandSingleton == null)
	    {
	      leinwandSingleton = new Leinwand("Moebelprojekt Grafik", 600, 600, Color.white);
	    }
	    leinwandSingleton.setzeSichtbarkeit(true);
	    return leinwandSingleton;
	}
  //  ----- Exemplarvariablen -----
	private JFrame fenster;
	private Zeichenflaeche zeichenflaeche;
	private Graphics2D graphic;
	private Color hintergrundfarbe;
	private Image leinwandImage;
	private List<Object> figuren;
	private Map<Object, ShapeMitFarbe> figurZuShape; // Abbildung von Figuren zu Shapes
=======
  public static Leinwand gibLeinwand()
  {
    if (leinwandSingleton == null)
    {
      leinwandSingleton =
        new Leinwand("Moebelprojekt Grafik", 600, 600, Color.white);
    }
    leinwandSingleton.setzeSichtbarkeit(true);
    return leinwandSingleton;
  }

  //  ----- Exemplarvariablen -----

  private JFrame fenster;
  private Zeichenflaeche zeichenflaeche;
  private Graphics2D graphic;
  private Color hintergrundfarbe;
  private Image leinwandImage;
  private List<Object> figuren;
  private Map<Object, ShapeMitFarbe> figurZuShape; // Abbildung von Figuren zu Shapes

>>>>>>> master
  /**
   * Erzeuge eine Leinwand.
   * @param titel  Titel, der im Rahmen der Leinwand angezeigt wird
   * @param breite  die gew�nschte Breite der Leinwand
   * @param hoehe  die gew�nschte H�he der Leinwand
   * @param grundfarbe die Hintergrundfarbe der Leinwand
   */
	private Leinwand(String titel, int breite, int hoehe, Color grundfarbe)
	{
    fenster = new JFrame();
<<<<<<< HEAD
    // mouse-event listener fuer JFrame --> fenster
    fenster.addMouseMotionListener(this);
    fenster.addMouseListener(this);
=======

    // mouse-event listener fuer JFrame --> fenster
    fenster.addMouseMotionListener(this);
    fenster.addMouseListener(this);

>>>>>>> master
    zeichenflaeche = new Zeichenflaeche();
    fenster.setContentPane(zeichenflaeche);
    fenster.setTitle(titel);
    zeichenflaeche.setPreferredSize(new Dimension(breite, hoehe));
    hintergrundfarbe = grundfarbe;
    fenster.pack();
    figuren = new ArrayList();
    figurZuShape = new HashMap();
<<<<<<< HEAD
	}
	private int lastOffsetX;
	private int lastOffsetY;
  @Override
	public void mouseClicked(MouseEvent e)
          lastOffsetX = e.getX();
          lastOffsetY = e.getY();
      }
=======

  }

    @Override
    public void mouseClicked(MouseEvent e) {}
    
    private int lastOffsetX;
    private int lastOffsetY;

>>>>>>> master
    @Override
    public void mouseDragged(MouseEvent e)
    {
        //System.out.printf("Clicked --> x: %d y: %d\n", e.getX(), e.getY());
<<<<<<< HEAD
=======

>>>>>>> master
        for (Object shape: figurZuShape.keySet())
        {
            if (((ShapeMitFarbe) figurZuShape.get(shape)).self_contains(e.getX(), e.getY()))
            {
                for (Object obj: figuren)
                {
                    if (obj == shape)
                    {
                        int newX = e.getX() - lastOffsetX;
                        int newY = e.getY() - lastOffsetY;
<<<<<<< HEAD
                        lastOffsetX += newX;
                        lastOffsetY += newY;
                        AffineTransform t = new AffineTransform();
                        Rectangle2D umriss = ((ShapeMitFarbe) figurZuShape.get(shape)).Shape_getBounds2D();
                        t.translate( newX, newY);
                        t.rotate(Math.toRadians(0),
                            umriss.getX() + umriss.getWidth()/2,
                            umriss.getY() + umriss.getHeight()/2);
                        Shape obj_2 = t.createTransformedShape(((ShapeMitFarbe) figurZuShape.get(shape)).return_self());
=======

                        lastOffsetX += newX;
                        lastOffsetY += newY;

                        AffineTransform t = new AffineTransform();

                        Rectangle2D umriss = ((ShapeMitFarbe) figurZuShape.get(shape)).Shape_getBounds2D();

                        t.translate( newX, newY);

                        t.rotate(Math.toRadians(0),
                            umriss.getX() + umriss.getWidth()/2,
                            umriss.getY() + umriss.getHeight()/2);

                        Shape obj_2 = t.createTransformedShape(((ShapeMitFarbe) figurZuShape.get(shape)).return_self());

>>>>>>> master
                        entferne(obj);
                        zeichne(shape, "rot", obj_2);
                    }
                }
                zeichenflaeche.repaint();
            }
<<<<<<< HEAD
=======

>>>>>>> master
            // Recherche
            // System.out.println(myShape);
            // System.out.println(myShape.getClass());
            // System.out.println(figurZuShape.get(myShape));
            // System.out.println(figurZuShape.get(myShape).getClass());
        }
    }
<<<<<<< HEAD
    @Override
    public void mouseEntered(MouseEvent e)
    {
        lastOffsetX = e.getX();
        lastOffsetY = e.getY();
    }
    @Override
    public void mouseExited(MouseEvent e)
    {
        lastOffsetX = e.getX();
        lastOffsetY = e.getY();
    }
    @Override
    public void mouseMoved(MouseEvent e)
    {
        lastOffsetX = e.getX();
        lastOffsetY = e.getY();
    }
=======

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

>>>>>>> master
    @Override
    public void mousePressed(MouseEvent e)
    {
        lastOffsetX = e.getX();
        lastOffsetY = e.getY();
    }
<<<<<<< HEAD
    @Override
    public void mouseReleased(MouseEvent e)
	{
		lastOffsetX = e.getX();
		lastOffsetY = e.getY();
	}
=======

    @Override
    public void mouseReleased(MouseEvent e) {}

>>>>>>> master
  /**
   * Setze, ob diese Leinwand sichtbar sein soll oder nicht. Wenn die
   * Leinwand sichtbar gemacht wird, wird ihr Fenster in den
   * Vordergrund geholt. Diese Operation kann auch benutzt werden, um
   * ein bereits sichtbares Leinwandfenster in den Vordergrund (vor
   * andere Fenster) zu holen.
   * @param sichtbar boolean f�r die gew�nschte Sichtbarkeit:
   * true f�r sichtbar, false f�r nicht sichtbar.
   */
	public void setzeSichtbarkeit(boolean sichtbar)
	{
    if (graphic == null)
    {
      // erstmaliger Aufruf: erzeuge das Bildschirm-Image und f�lle
      // es mit der Hintergrundfarbe
      Dimension size = zeichenflaeche.getSize();
      leinwandImage = zeichenflaeche.createImage(size.width, size.height);
      graphic = (Graphics2D) leinwandImage.getGraphics();
      graphic.setColor(hintergrundfarbe);
      graphic.fillRect(0, 0, size.width, size.height);
      graphic.setColor(Color.black);
    }
    fenster.setVisible(sichtbar);
	}
  /**
   * Zeichne f�r das gegebene Figur-Objekt eine Java-Figur (einen Shape)
   * auf die Leinwand.
   * @param  figur  das Figur-Objekt, f�r das ein Shape gezeichnet
   *                 werden soll
   * @param  farbe  die Farbe der Figur
   * @param  shape  ein Objekt der Klasse Shape, das tats�chlich
   *                 gezeichnet wird
   */
	public void zeichne(Object figur, String farbe, Shape shape)
	{
    figuren.remove(figur); // entfernen, falls schon eingetragen
    figuren.add(figur); // am Ende hinzuf�gen
    figurZuShape.put(figur, new ShapeMitFarbe(shape, farbe));
    erneutZeichnen();
	}
  /**
   * Entferne die gegebene Figur von der Leinwand.
   * @param  figur  die Figur, deren Shape entfernt werden soll
   */
	\1
	{
    figuren.remove(figur); // entfernen,falls schon eingetragen
    figurZuShape.remove(figur);
    erneutZeichnen();
	}
  /**
   * Setze die Zeichenfarbe der Leinwand.
   * @param  farbname der Name der neuen Zeichenfarbe.
   */
	public void setzeZeichenfarbe(String farbname)
	{
    if (farbname.equals("rot"))
      graphic.setColor(Color.red);
    else if (farbname.equals("schwarz"))
    	graphic.setColor(Color.black);
    else if (farbname.equals("blau"))
      graphic.setColor(Color.blue);
    else if (farbname.equals("gelb"))
      graphic.setColor(Color.yellow);
    else if (farbname.equals("gruen"))
      graphic.setColor(Color.green);
    else if (farbname.equals("lila"))
      graphic.setColor(Color.magenta);
    else if (farbname.equals("weiss"))
      graphic.setColor(Color.white);
    else
      graphic.setColor(Color.black);
	}
  /**
   * Warte f�r die angegebenen Millisekunden.
   * Mit dieser Operation wird eine Verz�gerung definiert, die
   * f�r animierte Zeichnungen benutzt werden kann.
   * @param  millisekunden die zu wartenden Millisekunden
   */
	public void warte(int millisekunden)
	{
    try
    {
      Thread.sleep(millisekunden);
    }
    catch (Exception e)
    {
      // Exception ignorieren
    }
	}
  /**
   * Zeichne erneut alle Figuren auf der Leinwand.
   */
	private void erneutZeichnen()
	{
        loeschen();
        for (Iterator i = figuren.iterator(); i.hasNext();)
        {
            ((ShapeMitFarbe) figurZuShape.get(i.next())).draw(graphic);
        }
        zeichenflaeche.repaint();
	}
  /**
   * L�sche die gesamte Leinwand.
   */
	private void loeschen()
	{
    Color original = graphic.getColor();
    graphic.setColor(hintergrundfarbe);
    Dimension size = zeichenflaeche.getSize();
    graphic.fill(new Rectangle(0, 0, size.width, size.height));
    graphic.setColor(original);
	}
  /************************************************************************
   * Interne Klasse Zeichenflaeche - die Klasse f�r die GUI-Komponente,
   * die tats�chlich im Leinwand-Fenster angezeigt wird. Diese Klasse
   * definiert ein JPanel mit der zus�tzlichen M�glichkeit, das auf ihm
   * gezeichnet Image aufzufrischen (erneut zu zeichnen).
   */
	private class Zeichenflaeche extends JPanel
	{
    public void paint(Graphics g)
    {
      g.drawImage(leinwandImage, 0, 0, null);
    }
	}
  /************************************************************************
   * Interne Klasse ShapeMitFarbe - Da die Klasse Shape des JDK nicht auch
   * eine Farbe mitverwalten kann, muss mit dieser Klasse die Verkn�pfung
   * modelliert werden.
   * graphic.fill() durch graphic.draw() ersetzt von Uwe Debacher am 5.12.2003
   */
	private class ShapeMitFarbe
	{
    private Shape shape;
    private String farbe;
    public ShapeMitFarbe(Shape shape, String farbe)
    {
      this.shape = shape;
      this.farbe = farbe;
    }
    public void draw(Graphics2D graphic)
    {
      setzeZeichenfarbe(farbe);
      graphic.draw(shape);
    }
<<<<<<< HEAD
=======

>>>>>>> master
    // funktion die ueberprueft ob das mouse-event im Shape inhalten ist
    public boolean self_contains(int x, int y)
    {
        return shape.contains(x, y);
    }
<<<<<<< HEAD
=======

>>>>>>> master
    public Rectangle2D Shape_getBounds2D()
    {
        return shape.getBounds2D();
    }
<<<<<<< HEAD
=======

>>>>>>> master
    public Shape return_self()
    {
        return shape;
    }
<<<<<<< HEAD
	}
=======
  }

>>>>>>> master
}
