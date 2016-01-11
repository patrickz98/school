import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.*;

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

		MouseWheelListener myMouseWheel = new MouseWheelListener()
		{
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				Controller.gibController().drehe(e.getWheelRotation() * 2);
			}
		};

		MouseAdapter myMouseAdapter = new MouseAdapter()
		{
			public void mouseMoved(MouseEvent e)
			{
				jumping_contorl = false;
			}

			public void mouseClicked(MouseEvent e)
			{
				// SwingUtilities.isLeftMouseButton(MouseEvent anEvent)
				// SwingUtilities.isRightMouseButton(MouseEvent anEvent)
				// SwingUtilities.isMiddleMouseButton(MouseEvent anEvent)
				if(SwingUtilities.isLeftMouseButton(e))
				{
					Controller.gibController().angeklickt(e.getX(), e.getY());
				}

				if(SwingUtilities.isRightMouseButton(e))
				{
					if (Controller.gibController().touched(e.getX(), e.getY()))
					{
						PopUp menu = new PopUp(true, e.getX(), e.getY());
				        menu.show(e.getComponent(), e.getX(), e.getY());
					}
					else
					{
						PopUp menu = new PopUp(false, e.getX(), e.getY());
				        menu.show(e.getComponent(), e.getX(), e.getY());
					}
				}
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

		ComponentListener myComponentLister = new ComponentListener()
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

		zeichenflaeche.addComponentListener(myComponentLister);
		zeichenflaeche.addMouseListener(myMouseAdapter);
		zeichenflaeche.addMouseMotionListener(myMouseAdapter);
		zeichenflaeche.addMouseWheelListener(myMouseWheel);
		zeichenflaeche.setPreferredSize(new Dimension(breite, hoehe));

		hintergrundfarbe = grundfarbe;

		this.setLayout(null);
		this.setResizable(true);
		this.setContentPane(zeichenflaeche);
		this.setTitle(titel);
		this.pack();

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

		this.erneutZeichnen();
    	this.setVisible(sichtbar);
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
    	this.erneutZeichnen();
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
		this.loeschen();
		for (Iterator i = figuren.iterator(); i.hasNext();)
		{
			((ShapeMitFarbe) figurZuShape.get(i.next())).draw(graphic);
		}
		zeichenflaeche.repaint();
	}

	/**
	 * Loesche die gesamte Leinwand.
	 */
	private void loeschen()
	{
		Color original = graphic.getColor();
		graphic.setColor(hintergrundfarbe);
		Dimension size = zeichenflaeche.getSize();
		graphic.fill(new Rectangle(0, 0, size.width, size.height));
		graphic.setColor(original);
	}

	String WindowText = "";

	// Window text thread
	protected Thread myWindowTextThread = new Thread(new Runnable() {
		public void run()
		{
			setzeSichtbarkeit(true);
			pack();

			graphic.setColor(Color.black);
			graphic.setFont(new Font("Ubuntu", Font.PLAIN, 36));
			graphic.drawString(WindowText, 10, getHeight() - 70);

			try
			{
				Thread.sleep(1000);
			}
			catch (Exception e)
			{}
		}
	});

	// WindowText = "Add";
	// if(!myWindowTextThread.isAlive())
	// {
	// 	myWindowTextThread.start();
	// }

	public Rectangle mySize()
	{
		return this.getBounds();
	}

	public void resize()
	{
		this.setzeSichtbarkeit(true);
		this.setPreferredSize(new Dimension(getWidth(), getHeight()));
		this.pack();

		System.out.printf("--> window: x:%4d y:%4d\n", getWidth(), getHeight());
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

	class PopUp extends JPopupMenu
	{
		Controller controller = Controller.gibController();

		private String[] Moebel = {
			"Stuhl",
			"Tisch",
			"Bett",
			"Schrank",
			"Schrankwand",
			"Sessel"
		};
		private JMenuItem[] Moebel_Items = new JMenuItem[Moebel.length];

		private String[] farben = {
            "schwarz",
            "rot",
            "blau",
            "gelb",
            "gruen",
            "lila",
            "weiss"
        };
		private JMenuItem[] Moebel_Colors = new JMenuItem[farben.length];

		final int x;
		final int y;

	    public PopUp(boolean touched, int mx, int my)
		{
			this.x = mx;
			this.y = my;

			if(touched)
			{
				this.MoebelMenu();
			}
			else
			{
				this.addMoebel();
			}
	    }

		protected void addMoebel()
		{
			for(int z = 0; z < Moebel.length; z++)
			{
				Moebel_Items[z] = new JMenuItem(Moebel[z]);

				Moebel_Items[z].addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						controller.neuXY(evt.getActionCommand(), x, y);
					}
				});

				this.add(Moebel_Items[z]);
			}

			this.addSeparator();

			JMenuItem sort = new JMenuItem("Alle Sortieren");

			sort.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
					controller.sortAll(mySize());
				}
			});
			this.add(sort);
		}

		protected void MoebelMenu()
		{
			JMenu farbenMenu = new JMenu("Farbe");

			for(int z = 0; z < Moebel_Colors.length; z++)
			{
				Moebel_Colors[z] = new JMenuItem(farben[z]);

				Moebel_Colors[z].addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						controller.aendereFarbe(evt.getActionCommand());
					}
				});

				farbenMenu.add(Moebel_Colors[z]);
			}

			this.add(farbenMenu);

			this.addSeparator();

			JMenuItem sort = new JMenuItem("Sortieren");

			sort.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
					controller.sortOne(mySize());
				}
			});
			this.add(sort);


			this.addSeparator();

			JMenuItem duplicate = new JMenuItem("Duplizieren");

			duplicate.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
					try
					{
						controller.duplicate();
					}
					catch(CloneNotSupportedException cnsE)
					{
						cnsE.printStackTrace();
					}
				}
			});
			this.add(duplicate);

			JMenuItem remove = new JMenuItem("Loeschen");

			remove.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
					controller.loeschen();
				}
			});

			this.add(remove);
		}
	}
}
