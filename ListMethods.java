import java.util.*;

public class ListMethods
{
   public static ArrayList<Integer> makeList(int n)
   {
      ArrayList<Integer> tempList = null;
      if (n <= 0)  // The smallest list we can make
      {
          return new ArrayList<Integer>();



      }
      else        // All other size lists are created here
      {          
          tempList = makeList(n-1);
          tempList.add(n);
          



      }
      return tempList;
   }
   public static ArrayList<Integer> reverseList(ArrayList<Integer> tList)
   {
       ArrayList<Integer> list = ListMethods.deepClone(tList);
       if (list.size() <= 1)
       {
           return list;
       }
       else
       {
           int x = tList.remove(0);           
           list = reverseList(tList);
           list.add(x);
       }
       return list;
    }
    public static void main()
    {
        ArrayList<Integer> p = new ArrayList<Integer>();
        for (int i = 0; i <= 10; i+= 1)
        {
            p.add(i);
        }
        System.out.println(makeList(5));
        System.out.println(reverseList(p));
    }
   public static ArrayList<Integer> deepClone(ArrayList<Integer> tList)
   {
       ArrayList<Integer> list = new ArrayList<Integer>(); 
       for (Integer i : tList)
       {
           list.add(new Integer(i));
        }
        return list;
    } 
}