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

    private JMenu     GuiMenu      = new JMenu("Gui");
    private JMenuItem DateiBeenden = new JMenuItem("Beenden");

    private JMenu     DateiMenu      = new JMenu("Datei");
	private JMenuItem DateiOeffnen   = new JMenuItem("Oeffnen");
	private JMenuItem DateiSpeichern = new JMenuItem("Speichern");

    private JMenu     BearbeitenMenu      = new JMenu("Bearbeiten");
    private JMenu     NewMenu             = new JMenu("Neu");
	private JMenuItem BearbeitenLoeschen  = new JMenuItem("Loeschen");
	private JMenuItem BearbeitenNeueFarbe = new JMenuItem("neue Farbe");
    private JMenuItem BearbeitenBackgroud = new JMenuItem("Hintergrund Farbe");

    // private JMenuItem BearbeitenResize = new JMenuItem("Resize");

    private String[] Moebel =
    {
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
        myFrame = Leinwand.gibLeinwand();

		myFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                exit(0);
            }
        });

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - myFrame.getSize().width) / 2;
		int y = (d.height - myFrame.getSize().height) / 2;
		myFrame.setLocation(x, y);

		// Container cp = myFrame.getContentPane();
		// cp.setLayout(null);

		myFrame.setJMenuBar(jmb);
        jmb.add(GuiMenu);

        //
        // ################### Menu ###################
        //

		jmb.add(DateiMenu);
		DateiOeffnen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				DateiOeffnen_ActionPerformed(evt);
			}
		});
		DateiMenu.add(DateiOeffnen);

		DateiSpeichern.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				DateiSpeichern_ActionPerformed(evt);
			}
		});
		DateiMenu.add(DateiSpeichern);

		DateiBeenden.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				DateiBeenden_ActionPerformed(evt);
			}
		});
		GuiMenu.add(DateiBeenden);

		jmb.add(BearbeitenMenu);

        for (int z = 0; z < Moebel.length; z++)
        {
            Moebel_Items[z] = new JMenuItem(Moebel[z]);
            ActionListener myAction = null;

            switch (Moebel[z])
            {
                case "Stuhl":
                    myAction = new ActionListener()
                    {
                        public void actionPerformed(ActionEvent evt)
                        {
                            New_ActionPerformed("Stuhl");
                        }
                    };
                    break;
                case "Tisch":
                    myAction = new ActionListener()
                    {
                        public void actionPerformed(ActionEvent evt)
                        {
                            New_ActionPerformed("Tisch");
                        }
                    };
                    break;
                case "Bett":
                    myAction = new ActionListener()
                    {
                        public void actionPerformed(ActionEvent evt)
                        {
                            New_ActionPerformed("Bett");
                        }
                    };
                    break;
                case "Schrank":
                    myAction = new ActionListener()
                    {
                        public void actionPerformed(ActionEvent evt)
                        {
                            New_ActionPerformed("Schrank");
                        }
                    };
                    break;
                case "Schrankwand":
                    myAction = new ActionListener()
                    {
                        public void actionPerformed(ActionEvent evt)
                        {
                            New_ActionPerformed("Schrankwand");
                        }
                    };
                    break;
                case "Sessel":
                    myAction = new ActionListener()
                    {
                        public void actionPerformed(ActionEvent evt)
                        {
                            New_ActionPerformed("Sessel");
                        }
                    };
                    break;
            }

            Moebel_Items[z].addActionListener(myAction);

            NewMenu.add(Moebel_Items[z]);
        }

        BearbeitenMenu.add(NewMenu);

		BearbeitenMenu.addSeparator();

        // BearbeitenResize.addActionListener(new ActionListener()
		// {
		// 	public void actionPerformed(ActionEvent evt)
		// 	{
		// 		myFrame.resize();
		// 	}
		// });
		// BearbeitenMenu.add(BearbeitenResize);

		BearbeitenNeueFarbe.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				BearbeitenNeueFarbe_ActionPerformed(evt);
			}
		});
		BearbeitenMenu.add(BearbeitenNeueFarbe);

        BearbeitenLoeschen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				BearbeitenLoeschen_ActionPerformed(evt);
			}
		});
		BearbeitenMenu.add(BearbeitenLoeschen);

		myFrame.setVisible(true);
	}

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

	// Anfang Methoden
	public void DateiOeffnen_ActionPerformed(ActionEvent evt)
	{
        String[] choices = listdir(new File("."));

        if (choices.length == 0) return;

        String dateiName = (String) JOptionPane.showInputDialog
        (
            null,
            "Choose now...",
            "Open",
            JOptionPane.QUESTION_MESSAGE,
            null,
            choices,
            choices[0]
        );

		try
		{
				try
				{
						try
                        {
                            controller.holen(dateiName);
						}
                        catch (ClassNotFoundException e)
                        {
                            // wtf Klasse gibt es nicht?
                            JOptionPane.showMessageDialog(null, "Klasse gibt es nicht!");
                            System.err.println("++> DateiOeffnen_ActionPerformed: Klasse gibt es nicht!");
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

	public void DateiSpeichern_ActionPerformed(ActionEvent evt)
	{
		String dateiName = JOptionPane.showInputDialog("Datei", "default");
		try
        {
            if (dateiName.equals(""))
            {
                controller.sichern("default.save");
                System.out.println("--> saved default.save");
            }
            else
            {
                controller.sichern(dateiName + ".save");
                System.out.printf("--> saved %s.save", dateiName);
            }
        }
		catch (IOException e)
        {
            System.err.println("++> DateiSpeichern_ActionPerformed: IOException");
        }
	}

    public void exit(int status)
	{
		Leinwand lw = Leinwand.gibLeinwand();
		lw.setzeSichtbarkeit(false);
		System.exit(status);
	}

	public void DateiBeenden_ActionPerformed(ActionEvent evt)
	{
        exit(0);
	}

	Controller controller = Controller.gibController();
	public void New_ActionPerformed(String object)
	{
		controller.neu(object);
	}

	public void BearbeitenLoeschen_ActionPerformed(ActionEvent evt)
	{
		controller.loeschen();
	}

    private String ChooseColor()
    {
        String[] farben =
        {
            "schwarz",
            "rot",
            "blau",
            "gelb",
            "gruen",
            "lila",
            "weiss"
        };

        String farbe = (String) JOptionPane.showInputDialog
        (
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

	public void BearbeitenNeueFarbe_ActionPerformed(ActionEvent evt)
	{
        String farbe = ChooseColor();

        if (farbe != null)
        {
            controller.aendereFarbe(farbe);
        }
	}

	// Ende Methoden

	public static void main(String[] args)
	{
        // test thead
        Runnable myController = new Runnable()
        {
            public void run()
            {
                for (int x = 0; x < 100; x++)
                {
                    System.out.printf("Hello from a thread! --> %d\n", x);

                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (Exception e)
                    {
                        // Exception ignorieren
                    }
                }
            }
        };

        Thread myThread = new Thread(myController);
        // myThread.start();

		new Gui("Gui");
	}
}
