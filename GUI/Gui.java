import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 10.05.2011
  * @author
*/

public class Gui
{
    private JFrame myFrame;

	private JMenuBar jmb = new JMenuBar();

    private JMenu     jDateiMenu      = new JMenu("Datei");
	private JMenuItem DateiJMenuItem1 = new JMenuItem("Oeffnen");
	private JMenuItem DateiJMenuItem2 = new JMenuItem("Speichern");
	private JMenuItem DateiJMenuItem3 = new JMenuItem("Beenden");

    private JMenu jBearbeitenMenu = new JMenu("Bearbeiten");

    private JMenu NewMenu = new JMenu("Neu");

	private JMenuItem BearbeitenJMenuItem8  = new JMenuItem("Loeschen");
	private JMenuItem BearbeitenJMenuItem10 = new JMenuItem("neue Farbe");

    private String[] Moebel = {"Stuhl", "Tisch", "Bett", "Schrank", "Schrankwand", "Sessel"};
    private JMenuItem[] Moebel_Items = new JMenuItem[Moebel.length];

	public Gui(String title)
	{
		// Frame-Initialisierung
        // myFrame = new JFrame(title);
        // myFrame = new Leinwand(title, 600, 500, Color.white);

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
		jmb.add(jDateiMenu);
		DateiJMenuItem1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				DateiJMenuItem1_ActionPerformed(evt);
			}
		});
		jDateiMenu.add(DateiJMenuItem1);

		DateiJMenuItem2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				DateiJMenuItem2_ActionPerformed(evt);
			}
		});
		jDateiMenu.add(DateiJMenuItem2);

		DateiJMenuItem3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				DateiJMenuItem3_ActionPerformed(evt);
			}
		});
		jDateiMenu.add(DateiJMenuItem3);

		jmb.add(jBearbeitenMenu);

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
        jBearbeitenMenu.add(NewMenu);

		jBearbeitenMenu.addSeparator();
		BearbeitenJMenuItem8.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				BearbeitenJMenuItem8_ActionPerformed(evt);
			}
		});
		jBearbeitenMenu.add(BearbeitenJMenuItem8);


		BearbeitenJMenuItem10.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				BearbeitenJMenuItem10_ActionPerformed(evt);
			}
		});
		jBearbeitenMenu.add(BearbeitenJMenuItem10);

		// Ende Komponenten

		myFrame.setResizable(false);
		myFrame.setVisible(true);
	}

	// Anfang Methoden
	public void DateiJMenuItem1_ActionPerformed(ActionEvent evt)
	{
		String dateiName = JOptionPane.showInputDialog("Datei:", "default.save");
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

	public void DateiJMenuItem2_ActionPerformed(ActionEvent evt)
	{
		String dateiName = JOptionPane.showInputDialog("Datei", "default.save");
		try
        {
            controller.sichern(dateiName);
        }
		catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Datei nicht gefunden!");
            System.err.println("DateiJMenuItem2_ActionPerformed: Datei nicht gefunden!");
        }
	}

    public void exit()
	{
		Leinwand lw = Leinwand.gibLeinwand();
		lw.setzeSichtbarkeit(false);
		System.exit(0);
	}

	public void DateiJMenuItem3_ActionPerformed(ActionEvent evt)
	{
        exit();
	}

	Controller controller = Controller.gibController();
	public void New_ActionPerformed(String object)
	{
		controller.neu(object);
	}

	public void BearbeitenJMenuItem8_ActionPerformed(ActionEvent evt)
	{
		controller.loeschen();
	}

	public void BearbeitenJMenuItem10_ActionPerformed(ActionEvent evt)
	{
		controller.aendereFarbe(JOptionPane.showInputDialog("Farbe:", ""));
	}

	// Ende Methoden

	public static void main(String[] args)
	{
		new Gui("Gui");
	}
}
