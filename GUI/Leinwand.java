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

import java.awt.event.*;
import java.io.*;

/**
 * Leinwand ist eine Klasse, die einfache Zeichenoperationen auf einer
 * leinwandartigen Zeichenflaeche ermoeglicht.
 * Sie ist eine vereinfachte Version der Klasse Canvas (englisch fr
 * Leinwand) des JDK und wurde speziell fuer das Projekt "Figuren"
 * geschrieben.
 */
public class Leinwand extends JFrame
{
	private static Leinwand leinwandSingleton;

	// Leinwand Singleton
	public static Leinwand gibLeinwand()
	{
		if (leinwandSingleton == null)
		{
			leinwandSingleton = new Leinwand("Moebelprojekt", 900, 600, Color.white);
		}
		leinwandSingleton.setzeSichtbarkeit(true);
		return leinwandSingleton;
	}

	private Zeichenflaeche zeichenflaeche;
	private Graphics2D graphic;
	private Color hintergrundfarbe;
	private Image leinwandImage;
	private List<Object> figuren;
	private Map<Object, ShapeMitFarbe> figurZuShape; // Abbildung von Figuren zu Shapes

	private boolean jumping_contorl = false;

	/**
	 * Erzeuge eine Leinwand.
	 * @param titel	Titel, der im Rahmen der Leinwand angezeigt wird
	 * @param breite	die gewnschte Breite der Leinwand
	 * @param hoehe	die gewnschte Hhe der Leinwand
	 * @param grundfarbe die Hintergrundfarbe der Leinwand
	 */
	public Leinwand(String titel, int breite, int hoehe, Color grundfarbe)
	{
		super(titel);
		zeichenflaeche = new Zeichenflaeche();

		MouseWheelListener mouseWheel = new MouseWheelListener()
		{
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				Controller.gibController().drehe(e.getWheelRotation() * 2);
			}
		};

		MouseAdapter mouseAdapter = new MouseAdapter()
		{
			public void mouseMoved(MouseEvent e)
			{
				jumping_contorl = false;
			}

			public void mouseClicked(MouseEvent e)
			{
				Controller.gibController().angeklickt(e.getX(), e.getY());
			}

			// jumping objects #bug
			public void mouseDragged(MouseEvent e)
			{
				// System.out.println("--> mouseDragged");
				if (Controller.gibController().touched(e.getX(), e.getY()) || jumping_contorl)
				{
					Controller.gibController().verschiebeAuf(e.getX(), e.getY());
					jumping_contorl = true;
				}
				else
				{
					jumping_contorl = false;
				}
			}

			public void mousePressed(MouseEvent e)
			{
				// System.out.println("--> mousePressed");
				Controller.gibController().mausposition(e.getX(), e.getY());
			}
		};

		ComponentListener componentlister = new ComponentListener()
        {
           @Override
           public void componentHidden(ComponentEvent e)
           {}

           @Override
           public void componentMoved(ComponentEvent e)
           {}

           @Override
           public void componentResized(ComponentEvent e)
           {
               resize();
           }

           @Override
           public void componentShown(ComponentEvent e)
           {}
        };

		zeichenflaeche.addComponentListener(componentlister);
		zeichenflaeche.addMouseListener(mouseAdapter);
		zeichenflaeche.addMouseMotionListener(mouseAdapter);
		zeichenflaeche.addMouseWheelListener(mouseWheel);

		setLayout(null);
		setResizable(true);
		setContentPane(zeichenflaeche);
		setTitle(titel);

		zeichenflaeche.setPreferredSize(new Dimension(breite, hoehe));
		hintergrundfarbe = grundfarbe;

		pack();

		figuren = new ArrayList<Object>();
		figurZuShape = new HashMap<Object, ShapeMitFarbe>();
	}

	/**
	 * Setze, ob diese Leinwand sichtbar sein soll oder nicht. Wenn die
	 * Leinwand sichtbar gemacht wird, wird ihr	in den
	 * Vordergrund geholt. Diese Operation kann auch benutzt werden, um
	 * ein bereits sichtbares Leinwand in den Vordergrund (vor
	 * andere ) zu holen.
	 * @param sichtbar boolean fr die gewnschte Sichtbarkeit:
	 * true fr sichtbar, false fr nicht sichtbar.
	 */
	public void setzeSichtbarkeit(boolean sichtbar)
	{
		// removed if-loop to make the jframe resizable
    	// if (graphic == null)
    	// {}

		// erstmaliger Aufruf: erzeuge das Bildschirm-Image und fuelle
		// es mit der Hintergrundfarbe
		Dimension size = zeichenflaeche.getSize();
		leinwandImage = zeichenflaeche.createImage(getWidth(), getHeight());
		graphic = (Graphics2D) leinwandImage.getGraphics();
		graphic.setColor(hintergrundfarbe);
		graphic.fillRect(0, 0, size.width, size.height);
		graphic.setColor(Color.black);
		erneutZeichnen();

    	setVisible(sichtbar);
	}

	/**
	 * Zeichne fr das gegebene Figur-Objekt eine Java-Figur (einen Shape)
	 * auf die Leinwand.
	 * @param	figur	das Figur-Objekt, fr das ein Shape gezeichnet
	 *				 werden soll
	 * @param	farbe	die Farbe der Figur
	 * @param	shape	ein Objekt der Klasse Shape, das tatschlich
	 *				 gezeichnet wird
	 */
	public void zeichne(Object figur, String farbe, Shape shape)
	{
    	figuren.remove(figur); // entfernen, falls schon eingetragen
    	figuren.add(figur); // am Ende hinzufgen
    	figurZuShape.put(figur, new ShapeMitFarbe(shape, farbe));
    	erneutZeichnen();
	}

	/**
	 * Entferne die gegebene Figur von der Leinwand.
	 * @param	figur	die Figur, deren Shape entfernt werden soll
	 */
	public void entferne(Object figur)
	{
    	figuren.remove(figur); // entfernen,falls schon eingetragen
    	figurZuShape.remove(figur);
    	erneutZeichnen();
	}

	public Color StringToColor(String color)
	{
		Color newColor = Color.black;
		switch (color)
		{
			case "rot":
				newColor = Color.red;
				break;
			case "schwarz":
				newColor = Color.black;
				break;
			case "blau":
				newColor = Color.blue;
				break;
			case "gelb":
				newColor = Color.yellow;
				break;
			case "gruen":
				newColor = Color.green;
				break;
			case "lila":
				newColor = Color.magenta;
				break;
			case "weiss":
				newColor = Color.white;
				break;
			default:
				newColor = Color.black;
		}
		return newColor;
	}

	/**
	 * Setze die Zeichenfarbe der Leinwand.
	 * @param	farbname der Name der neuen Zeichenfarbe.
	 */
	public void setzeZeichenfarbe(String farbname)
	{
		graphic.setColor(StringToColor(farbname));
	}

	/**
	 * Warte fr die angegebenen Millisekunden.
	 * Mit dieser Operation wird eine Verzgerung definiert, die
	 * fr animierte Zeichnungen benutzt werden kann.
	 * @param	millisekunden die zu wartenden Millisekunden
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
	 * Lsche die gesamte Leinwand.
	 */
	private void loeschen()
	{
		Color original = graphic.getColor();
		graphic.setColor(hintergrundfarbe);
		Dimension size = zeichenflaeche.getSize();
		graphic.fill(new Rectangle(0, 0, size.width, size.height));
		graphic.setColor(original);
	}

	public void resize()
	{
		setzeSichtbarkeit(true);
		this.setPreferredSize(new Dimension(getWidth(), getHeight()));
		pack();

		System.out.printf("--> resized window x:%d y:%d\n", getWidth(), getHeight());
	}

	/************************************************************************
	 * Interne Klasse Zeichenflaeche - die Klasse fr die GUI-Komponente,
	 * die tatschlich im Leinwand-Fenster angezeigt wird. Diese Klasse
	 * definiert ein JPanel mit der zustzlichen Mglichkeit, das auf ihm
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
	 * eine Farbe mitverwalten kann, muss mit dieser Klasse die Verknpfung
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
	}
}
