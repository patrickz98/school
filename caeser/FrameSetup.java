import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FrameSetup extends EventHandler
{
	private Font myFont = new Font("Dialog", Font.PLAIN, 13);
	
	public JTextField jTextField1    = new JTextField();
	private JTextField jTextField2    = new JTextField();
	private JButton jButton1		= new JButton();
	private JButton jButton2		= new JButton();
	private JButton jButton3		= new JButton();
	private JLabel jLabel1		= new JLabel();
	private JLabel jLabel2		= new JLabel();
	private JButton jButton4		= new JButton();
	
	Container cp;
	
	public FrameSetup(Container cp)
	{
		this.cp = cp;
	}
	
	public void addJLabels()
	{
		jLabel1.setBounds(8, 24, 45, 20);
		jLabel1.setText("Texte");
		jLabel1.setFont(myFont);
		cp.add(jLabel1);
		
		jLabel2.setBounds(8, 136, 45, 20);
		jLabel2.setText("key");
		jLabel2.setFont(myFont);
		cp.add(jLabel2);	
	}
	
	public void addJTextfilds()
	{
		jTextField1.setBounds(64, 24, 217, 24);
		jTextField1.setText("GROSSBUCHSTABEN");
		jTextField1.setFont(myFont);
		cp.add(jTextField1);
		
		jTextField2.setBounds(64, 128, 129, 24);
		jTextField2.setText("ABC...");
		jTextField2.setFont(myFont);
		cp.add(jTextField2);
	}
	
	public void addJButtons()
	{
		jButton1.setBounds(8, 64, 123, 25);
		jButton1.setText("chiffriere");
		jButton1.setFont(myFont);
		cp.add(jButton1);
		
		jButton1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				System.out.println(jTextField1.getText());
				jButton1_ActionPerformed(evt);
			}
		});

		jButton2.setBounds(8, 96, 123, 25);
		jButton2.setText("dechiffriere");
		jButton2.setFont(myFont);
		cp.add(jButton2);
		
		jButton2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				jButton2_ActionPerformed(evt);
			}
		});

		jButton3.setBounds(8, 160, 123, 25);
		jButton3.setText("setzen");
		jButton3.setFont(myFont);
		cp.add(jButton3);
		
		jButton3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				jButton3_ActionPerformed(evt);
			}
		});

		jButton4.setBounds(168, 160, 115, 25);
		jButton4.setText("Beenden");
		jButton4.setFont(myFont);
		cp.add(jButton4);
		
		jButton4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				jButton4_ActionPerformed(evt);
			}
		});
	}
}