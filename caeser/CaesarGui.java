import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CaesarGui
{
	public CaesarGui(String title)
	{
		JFrame myFrame = new JFrame(title);

		myFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent evt)
			{
				System.exit(0);
			}
		});
    
		int frameWidth = 400; 
		int frameHeight = 600;
	
		myFrame.setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		int x = (d.width - myFrame.getSize().width) / 2;
		int y = (d.height - myFrame.getSize().height) / 2 ;
		
		myFrame.setLocation(x, y);
		myFrame.setVisible(true);
		
		Container cp = myFrame.getContentPane();
		cp.setLayout(null);
		
		FrameSetup myFrameSetup = new FrameSetup(cp);
		
		myFrameSetup.addJLabels();
		myFrameSetup.addJTextfilds();
		myFrameSetup.addJButtons();
	}
		
	public static void main(String[] args)
	{
		new CaesarGui("CaesarGui");
	}
}

