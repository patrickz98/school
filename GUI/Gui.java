import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.ArrayList;

public class Gui
{
	private Leinwand myFrame = null;

	private JMenuBar jmb = new JMenuBar();

	private JMenu GuiMenu = new JMenu("Gui");
	private JMenuItem DateiBeenden = new JMenuItem("Beenden");

	private JMenu DateiMenu = new JMenu("Datei");
	private JMenuItem DateiOeffnen = new JMenuItem("Oeffnen");
	private JMenuItem DateiSpeichern = new JMenuItem("Speichern");

	private JMenu BearbeitenMenu = new JMenu("Bearbeiten");
	private JMenu NewMenu = new JMenu("Neu");

	// -------------------------

	private JMenuItem BearbeitenNeueFarbe = new JMenuItem("Neue Farbe");

	// -------------------------

	private JMenuItem BearbeitenSort = new JMenuItem("Aufraeumen");
	private JMenuItem BearbeitenSortAll = new JMenuItem("Alle Aufraeumen");

	// -------------------------

	private JMenuItem BearbeitenDuplicate = new JMenuItem("Duplizieren");

	// -------------------------

	private JMenuItem BearbeitenLoeschen = new JMenuItem("Objekt Loeschen");
	private JMenuItem BearbeitenDeleteBG = new JMenuItem("Alle Loeschen");

	private String[] Moebel = {
		"Stuhl",
		"Tisch",
		"Bett",
		"Schrank",
		"Schrankwand",
		"Sessel"
	};

	private JMenuItem[] Moebel_Items = new JMenuItem[Moebel.length];

	public Gui(String title)
	{
		// Leinwand JFrame
		myFrame = Leinwand.gibLeinwand();

		// Add closing event
		myFrame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				exit(0);
			}
		});

		// Frame dimension + positioning
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - myFrame.getSize().width) / 2;
		int y = (d.height - myFrame.getSize().height) / 2;
		myFrame.setLocation(x, y);

		//
		// ################### Menu ###################
		//

		// Add main MenuBar
		myFrame.setJMenuBar(jmb);

		// Add GUI-Menu --> MenuBar
		jmb.add(GuiMenu);

		// Add close --> GUI-Menu
		DateiBeenden.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				exit(0);
			}
		});
		GuiMenu.add(DateiBeenden);

		// Add BearbeitenMenu --> DateiMenu
		jmb.add(DateiMenu);

		// Add DateiOeffnen --> BearbeitenMenu
		DateiOeffnen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				DateiOeffnen_ActionPerformed(evt);
			}
		});
		DateiMenu.add(DateiOeffnen);

		// Add DateiSpeichern --> BearbeitenMenu
		DateiSpeichern.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				DateiSpeichern_ActionPerformed(false);
			}
		});
		DateiMenu.add(DateiSpeichern);

		// Add BearbeitenMenu --> MenuBar
		jmb.add(BearbeitenMenu);

		// Add Meobel[] --> NewMenu
		for (int z = 0; z < Moebel.length; z++)
		{
			Moebel_Items[z] = new JMenuItem(Moebel[z]);
			ActionListener myAction = new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
					New_ActionPerformed(evt);
				}
			};

			Moebel_Items[z].addActionListener(myAction);

			NewMenu.add(Moebel_Items[z]);
		}

		// Add NewMenu --> BearbeitenMenu
		BearbeitenMenu.add(NewMenu);

		// ---------------------
		BearbeitenMenu.addSeparator();

		// Add BearbeitenNeueFarbe --> BearbeitenMenu
		BearbeitenNeueFarbe.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				BearbeitenNeueFarbe_ActionPerformed(evt);
			}
		});
		BearbeitenMenu.add(BearbeitenNeueFarbe);

		// ---------------------
		BearbeitenMenu.addSeparator();

		// Add Aufraemen --> BearbeitenMenu
		BearbeitenSort.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				sortOne();
			}
		});
		BearbeitenMenu.add(BearbeitenSort);

		// Add Alle Aufraemen --> BearbeitenMenu
		BearbeitenSortAll.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				sortAll();
			}
		});
		BearbeitenMenu.add(BearbeitenSortAll);

		// --------------------
		BearbeitenMenu.addSeparator();

		// Add Duplizieren --> BearbeitenMenu
		BearbeitenDuplicate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				try
				{
					controller.duplicate();
				}
				catch(CloneNotSupportedException e)
				{
					e.printStackTrace();
				}
			}
		});
		BearbeitenMenu.add(BearbeitenDuplicate);

		// ------------------
		BearbeitenMenu.addSeparator();

		// Add Loeschen --> BearbeitenMenu
		BearbeitenLoeschen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				BearbeitenLoeschen_ActionPerformed(evt);
			}
		});
		BearbeitenMenu.add(BearbeitenLoeschen);

		// Add Loesche Alle--> BearbeitenMenu
		BearbeitenDeleteBG.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				BearbeitenDeleteBG_ActionPerformed(evt);
			}
		});
		BearbeitenMenu.add(BearbeitenDeleteBG);

		//
		// ############## Menu ende ####################
		//

		myFrame.setVisible(true);
	}

	// list dir with .save
	public String[] listdir(final File folder)
	{
		ArrayList<String> Files = new ArrayList<String>();

		for (final File fileEntry: folder.listFiles())
		{
			if (fileEntry.isDirectory())
			{
				listdir(fileEntry);
			}
			else
			{
				if (fileEntry.getName().endsWith(".save"))
				{
					Files.add(fileEntry.getName());
				}
			}
		}

		return Files.toArray(new String[Files.size()]);
	}

	// Open file
	public void DateiOeffnen_ActionPerformed(ActionEvent evt)
	{
		// check current dir
		String[] choices = listdir(new File("."));

		if (choices.length == 0)
		{
			System.err.println("++> no files available");
			JOptionPane.showConfirmDialog(
				null,
				"no files available",
				"Oeffnen",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE
			);
			return;
		}

		String dateiName = (String) JOptionPane.showInputDialog
		(
			null,
			"Choose now...",
			"Oeffnen",
			JOptionPane.QUESTION_MESSAGE,
			null,
			choices,
			choices[0]
		);

		// check if dateiName is selected
		if ((dateiName != null) && (dateiName.length() > 0))
		{
			System.out.println("--> open: " + dateiName);

			try
			{
				try
				{
					try
					{
						// open .save
						controller.holen(dateiName);
					}
					catch (ClassNotFoundException e)
					{
						JOptionPane.showMessageDialog(null, "Klasse gibt es nicht!");
						System.err.println("++> DateiOeffnen_ActionPerformed: ClassNotFoundException");
					}
				}
				catch (FileNotFoundException e)
				{
					JOptionPane.showMessageDialog(null, "Datei nicht gefunden!");
					System.err.println("++> DateiOeffnen_ActionPerformed: Datei nicht gefunden!");
				}
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(null, "E/A-Fehler!");
				System.err.println("++> DateiOeffnen_ActionPerformed: IOException");
			}
		}
		else
		{
			System.err.println("++> no file select");
		}
	}

	// Save file: evt --> true = autosave; false --> normal save
	public void DateiSpeichern_ActionPerformed(boolean evt)
	{
		String dateiName = "default";

		if (!evt)
		{
			dateiName = JOptionPane.showInputDialog("Datei", "default");

			// Cancel evt
			if (dateiName == null)
			{
				return;
			}
		}
		else
		{
			dateiName = "autosave";
		}

		try
		{
			controller.sichern(dateiName + ".save");
			System.out.printf("--> saved: %s.save\n", dateiName);
		}
		catch (IOException e)
		{
			System.err.println("++> DateiSpeichern_ActionPerformed: IOException");
		}
	}

	// Exit with autosave
	public void exit(int status)
	{
		// exit save
		DateiSpeichern_ActionPerformed(true);

		// exit
		Leinwand lw = Leinwand.gibLeinwand();
		lw.setzeSichtbarkeit(false);
		System.exit(status);
	}

	Controller controller = Controller.gibController();

	// Add seleced Moebel
	public void New_ActionPerformed(ActionEvent evt)
	{
		System.out.printf("--> add: %s\n", evt.getActionCommand());
		controller.neu(evt.getActionCommand());
	}

	// delete
	public void BearbeitenLoeschen_ActionPerformed(ActionEvent evt)
	{
		controller.loeschen();
	}

	// delete all
	public void BearbeitenDeleteBG_ActionPerformed(ActionEvent evt)
	{
		myFrame.loeschen();
	}

	// Color Choose dialog
	private String ChooseColor()
	{
		String[] farben = {
			"schwarz",
			"rot",
			"blau",
			"gelb",
			"gruen",
			"lila",
			"weiss",
			"orange"
		};

		String farbe = (String) JOptionPane.showInputDialog(
			null,
			"Choose now...",
			"Color",
			JOptionPane.QUESTION_MESSAGE,
			null,
			farben,
			farben[0]
		);

		return farbe;
	}

	// New Color Event
	public void BearbeitenNeueFarbe_ActionPerformed(ActionEvent evt)
	{
		String farbe = ChooseColor();

		if (farbe != null)
		{
			controller.aendereFarbe(farbe);
		}
	}

	// Alle Aufraeumen
	public void sortAll()
	{
		controller.sortAll(myFrame.getBounds());
	}

	// Aufraeumen selected
	public void sortOne()
	{
		controller.sortOne(myFrame.getBounds());
	}

	public static void main(String[] args)
	{
		final Gui myGui = new Gui("Gui");

		// Autosave thead
		Thread autosaveThread = new Thread(new Runnable()
		{
			public void run()
			{
				while (true)
				{
					try
					{
						// Autosave delay: 20s
						Thread.sleep(20000);
					}
					catch (Exception e)
					{}

					myGui.DateiSpeichern_ActionPerformed(true);
				}
			}
		});

		autosaveThread.start();
	}
}
