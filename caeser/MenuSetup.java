import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuSetup extends EventHandler
{
	private JMenuBar jmb		= new JMenuBar();
	private JMenu jDateiMenu	= new JMenu("Datei");
	private JMenuItem jDateiMenuItem1 = new JMenuItem("Beenden");
	private JMenu jBearbeitenMenu = new JMenu("Bearbeiten");
	private JMenuItem jBearbeitenMenuItem1 = new JMenuItem("chiffriere");
	private JMenuItem jBearbeitenMenuItem2 = new JMenuItem("dechiffriere");
	private JMenuItem jBearbeitenMenuItem3 = new JMenuItem("Schluessel setzen");
	private JMenuItem jBearbeitenMenuItem4 = new JMenuItem("Original setzen");
	private JMenuItem jBearbeitenMenuItem5 = new JMenuItem("Chiffrat setzen");
	private JMenuItem jBearbeitenMenuItem6 = new JMenuItem("Original zeigen");
	private JMenuItem jBearbeitenMenuItem7 = new JMenuItem("Chiffrat zeigen");
	private JMenuItem jBearbeitenMenuItem8 = new JMenuItem("Dechiffrat zeigen");
	private JMenu jHilfeMenu = new JMenu("Hilfe");
	private JMenuItem jHilfeMenuItem1 = new JMenuItem("Hilfe");
}