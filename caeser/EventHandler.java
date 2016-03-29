import java.awt.event.*;

public abstract class EventHandler
{
	public void jButton1_ActionPerformed(ActionEvent evt)
	{
		System.out.println("chiffriere");
	}
	
	public void jButton2_ActionPerformed(ActionEvent evt)
	{
		System.out.println("dechiffriere");
	}

	public void jButton3_ActionPerformed(ActionEvent evt)
	{
		System.out.println("setzen");
	}
	
	public void jButton4_ActionPerformed(ActionEvent evt)
	{
		System.out.println("Beenden");
	}
}