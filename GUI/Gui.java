import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.ArrayList;

public class Gui
{
    private JFrame myFrame;

	private JMenuBar jmb = new JMenuBar();

    private JMenu GuiMenu = new JMenu("Gui");
    private JMenuItem Size = new JMenuItem("Size");


    private JMenu DateiMenu = new JMenu("Datei");
	private JMenuItem DateiOeffnen = new JMenuItem("Oeffnen");
	private JMenuItem DateiSpeichern = new JMenuItem("Speichern");
	private JMenuItem DateiBeenden = new JMenuItem("Beenden");

    private JMenu BearbeitenMenu = new JMenu("Bearbeiten");
    private JMenu NewMenu = new JMenu("Neu");
	private JMenuItem BearbeitenLoeschen = new JMenuItem("Loeschen");
	private JMenuItem BearbeitenNeueFarbe = new JMenuItem("neue Farbe");

    private String[] Moebel = {"Stuhl", "Tisch", "Bett", "Schrank", "Schrankwand", "Sessel"};
    private JMenuItem[] Moebel_Items = new JMenuItem[Moebel.length];

	public Gui(String title)
	{
        myFrame = Leinwand.gibLeinwand();

        WindowListener windowListener = new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                exit();
            }
        };

		myFrame.addWindowListener(windowListener);
        ///myFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // int frameWidth = 600;
		// int frameHeight = 500;
        // myFrame.setSize(frameWidth, frameHeight);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - myFrame.getSize().width) / 2;
		int y = (d.height - myFrame.getSize().height) / 2;
		myFrame.setLocation(x, y);

		// Container cp = myFrame.getContentPane();
		// cp.setLayout(null);
		// Anfang Komponenten

		myFrame.setJMenuBar(jmb);
        jmb.add(GuiMenu);

        // DateiMenu
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

// ###############

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

// ###############
        BearbeitenMenu.add(NewMenu);

		BearbeitenMenu.addSeparator();
		BearbeitenLoeschen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				BearbeitenLoeschen_ActionPerformed(evt);
			}
		});
		BearbeitenMenu.add(BearbeitenLoeschen);


		BearbeitenNeueFarbe.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				BearbeitenNeueFarbe_ActionPerformed(evt);
			}
		});
		BearbeitenMenu.add(BearbeitenNeueFarbe);

		// Ende Komponenten

		myFrame.setResizable(true);
		myFrame.setVisible(true);
	}

    public String[] listFilesForFolder(final File folder)
    {
        ArrayList<String> Files = new ArrayList<String>();

        for (final File fileEntry : folder.listFiles())
        {
            if (fileEntry.isDirectory())
            {
                listFilesForFolder(fileEntry);
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
        String[] choices = listFilesForFolder(new File("."));

        if (choices.length == 0) return;

        // String[] choices = { "A", "B", "C", "D", "E", "F" };
        String dateiName = (String) JOptionPane.showInputDialog(
            null,
            "Choose now...",
            "Open",
            JOptionPane.QUESTION_MESSAGE,
            null,
            choices,
            choices[0]
        );

		// String dateiName = JOptionPane.showInputDialog("Datei:", "default.save");
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
                            JOptionPane.showMessageDialog(null, "Klasse gibt es nicht!");
                            System.err.println("Klasse gibt es nicht!");
                        }
				}
                catch (FileNotFoundException e)
                {
                    JOptionPane.showMessageDialog(null, "Datei nicht gefunden!");
                    System.err.println("Datei nicht gefunden!");
                }
		}
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "E/A-Fehler!");
            System.err.println("E/A-Fehler!");
        }
	}

	public void DateiSpeichern_ActionPerformed(ActionEvent evt)
	{
		String dateiName = JOptionPane.showInputDialog("Datei", "default");
		try
        {
            controller.sichern(dateiName + ".save");
        }
		catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Datei nicht gefunden!");
            System.err.println("DateiSpeichern_ActionPerformed: Datei nicht gefunden!");
        }
	}

    public void exit()
	{
		Leinwand lw = Leinwand.gibLeinwand();
		lw.setzeSichtbarkeit(false);
		System.exit(0);
	}

	public void DateiBeenden_ActionPerformed(ActionEvent evt)
	{
        exit();
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

	public void BearbeitenNeueFarbe_ActionPerformed(ActionEvent evt)
	{
        String[] farben = {"schwarz", "rot", "blau", "gelb", "gruen", "lila", "weiss"};
        String farbe = (String) JOptionPane.showInputDialog(
            null,
            "Choose now...",
            "Color",
            JOptionPane.QUESTION_MESSAGE,
            null,
            farben,
            farben[0]
        );

        controller.aendereFarbe(farbe);
	}

	// Ende Methoden

	public static void main(String[] args)
	{
		new Gui("Gui");
	}
}
