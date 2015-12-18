import java.util.Iterator;
public interface Queue
{
    // Gibt true für eine leere queue zurück.
    public boolean isEmpty();
<<<<<<< HEAD
=======

>>>>>>> master
    // Fügt das Element o am Ende der Queue an.
    public boolean add(Object o);
    /**
       * Liefert das erste Element der Queue und entfernt es
       * daraus. Falls die Queue leer ist, erhält man null.
    */
<<<<<<< HEAD
   public Object retrieve();
   // Liefert die Anzahl der Elemente der Queue.
   public int size();
   // Entfernt alle Elemente aus der Queue.
   public void clear();
   // Liefert einen Iterator über alle Elemente der Queue.
   public Iterator iterator();
}
=======
 
   public Object retrieve();
 
   // Liefert die Anzahl der Elemente der Queue.
   public int size();
   
   // Entfernt alle Elemente aus der Queue.
   public void clear();
 
   // Liefert einen Iterator über alle Elemente der Queue.
   public Iterator iterator();
}
>>>>>>> master
