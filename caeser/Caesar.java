public class Caesar
{
	private String original, chiffrat, dechiffrat;
	private char schluessel;

	public Caesar()
	{
		original = "ORIGINAL";
		chiffrat = "CHIFFRAT";
		dechiffrat = "DECHIFFRAT";
		dechiffrat = "DECHIFFRAT";
		schluessel = 'A';
	}
	
	public void setzeSchluessel(char buchstabe)
	{
		schluessel = buchstabe;
	}
	
	public void setzeOriginal(String text)
	{
		original = text.toUpperCase();
	}
	
	public void setzeChiffrat(String text)
	{
		chiffrat = text.toUpperCase();
	}
	
	public String gibOriginal()
	{
		return original;
	}

	public String gibChiffrat()
	{
		return chiffrat;
	}

	public String gibDechiffrat()
	{
		return dechiffrat;
	}
	
	private char chiffriereEinZeichen(char zeichen)
	{
		int zahl = (int) zeichen;
		int key = (int) schluessel;
		int ergebnis = ((zeichen + key - (65 * 2)) % 26) + 65;
		return (char) ergebnis;
	}

	private char dechiffriereEinZeichen(char zeichen)
	{
			int zahl = (int) zeichen;
			int key = (int) schluessel;
			int ergebnis = ((zahl - 65 - (key - 65) + 26) % 26) + 65;
			return (char) ergebnis;
	}
	
	public void chiffriere()
	{
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < original.length(); i++)
		temp.append(chiffriereEinZeichen(original.charAt(i)));
		chiffrat=temp.toString();
	}
		 
	public void dechiffriere()
	{
		StringBuffer temp = new StringBuffer();
		for (int i=0; i<chiffrat.length();i++)
		temp.append(dechiffriereEinZeichen(chiffrat.charAt(i)));
		dechiffrat=temp.toString();
	}
}
