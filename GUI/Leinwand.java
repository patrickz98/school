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

	// fix jumping objects
	private boolean jumping_controll = false;

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

		// MouseWheel Event
		MouseWheelListener myMouseWheel = new MouseWheelListener()
		{
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				// turn
				Controller.gibController().drehe(e.getWheelRotation() * 2);
			}
		};

		// MouseAdapter Event
		MouseAdapter myMouseAdapter = new MouseAdapter()
		{
			// fix jumping objects
			public void mouseMoved(MouseEvent e)
			{
				jumping_controll = false;
			}

			public void mouseClicked(MouseEvent e)
			{
				// left click
				if(SwingUtilities.isLeftMouseButton(e))
				{
					Controller.gibController().angeklickt(e.getX(), e.getY());
				}

				// right click
				if(SwingUtilities.isRightMouseButton(e))
				{
					// PopUp: true --> inside; false --> outside
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

			public void mouseDragged(MouseEvent e)
			{
				// fix jumping objects
				if (Controller.gibController().touched(e.getX(), e.getY()) || jumping_controll)
				{
					Controller.gibController().verschiebeAuf(e.getX(), e.getY());
					jumping_controll = true;
				}
				else
				{
					jumping_controll = false;
				}
			}

			public void mousePressed(MouseEvent e)
			{
				Controller.gibController().mausposition(e.getX(), e.getY());
			}
		};

		// Rezie Event
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
				// Autoresize
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

	public void setzeSichtbarkeit(boolean sichtbar)
	{
		// removed if-loop to make the jframe resizable
    	// if (graphic == null)
    	// {}

		// background fill
		Dimension size = zeichenflaeche.getSize();
		leinwandImage = zeichenflaeche.createImage(getWidth(), getHeight());
		graphic = (Graphics2D) leinwandImage.getGraphics();
		graphic.setColor(hintergrundfarbe);
		graphic.fillRect(0, 0, size.width, size.height);

		this.erneutZeichnen();
    	this.setVisible(sichtbar);
	}

	public void zeichne(Object figur, String farbe, Shape shape)
	{
    	figuren.remove(figur);
    	figuren.add(figur);
    	figurZuShape.put(figur, new ShapeMitFarbe(shape, farbe));
    	erneutZeichnen();
	}

	public void entferne(Object figur)
	{
    	figuren.remove(figur);
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
			case "orange":
				newColor = Color.orange;
				break;
			default:
				newColor = Color.black;
		}
		return newColor;
	}

	public void setzeZeichenfarbe(String farbname)
	{
		graphic.setColor(StringToColor(farbname));
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
	public void loeschen()
	{
		Color original = graphic.getColor();
		graphic.setColor(hintergrundfarbe);
		Dimension size = zeichenflaeche.getSize();
		graphic.fill(new Rectangle(0, 0, size.width, size.height));
		graphic.setColor(original);
	}

	// return Frame size for the sorting funcs.
	public Rectangle myFrameSize()
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
	 * Interne Klasse Zeichenflaeche - die Klasse fuer die GUI-Komponente,
	 * die tatschlich im Leinwand-Fenster angezeigt wird. Diese Klasse
	 * definiert ein JPanel mit der zustzlichen Moeglichkeit, das auf ihm
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
	 * graphic.fill() durch graphic.draw()
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

	// right click view
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
			"weiss",
			"orange"
		};
		private JMenuItem[] Moebel_Colors = new JMenuItem[farben.length];

		final int x;
		final int y;

	    public PopUp(boolean touched, int mx, int my)
		{
			this.x = mx;
			this.y = my;

			// true --> inside (Object Options); false --> everything else
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

			JMenuItem sort = new JMenuItem("Alle Aufraeumen");

			sort.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
					controller.sortAll(myFrameSize());
				}
			});
			this.add(sort);
		}

		protected void MoebelMenu()
		{
			JMenu farbenMenu = new JMenu("Farbe");

			// Colors
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

			// ---------------
			this.addSeparator();

			JMenuItem sort = new JMenuItem("Aufraeumen");

			sort.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
					controller.sortOne(myFrameSize());
				}
			});
			this.add(sort);

			// ---------------
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
					loeschen();
				}
			});

			this.add(remove);
		}
	}
}
