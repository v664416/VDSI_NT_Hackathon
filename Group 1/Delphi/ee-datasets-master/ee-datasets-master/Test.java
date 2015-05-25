import java.io.*;
import java.util.*;
public class Test {
	static int count=1;
public static void printWords(int length,BufferedWriter br) {

    if (length < 1)
        throw new IllegalArgumentException();
    printWordsRec("", length,br);
}

private static void printWordsRec(String base, int length,BufferedWriter br) {
      HashMap <Character,Character> hm= new  HashMap<Character,Character>();
	  for (char c = 'a',c1='z'; c <= 'z'; c++,c1--)
	  {
		  hm.put(c,c1);
		  
	  }
	 
    for (char c = 'a'; c <= 'z'; c++) {
        if (length == 1) {
			try{
				String book=base+c;
				String author="";
				for(int i=0;i<book.length();i++)
				{
					author+=hm.get(book.charAt(i));
				}
            br.write("{\"_id\":" +count+", \"title\": \""+book+"\", \"author\":\""+author+"\"}");
			count++;
			br.newLine();
			}
			catch(IOException e){}
        }
        else {
            printWordsRec(base + c, length - 1,br);
        }
    }
}
public static void main(String[] args) {
        try {
        BufferedWriter out = new BufferedWriter(new FileWriter("book_db.eloader"));
		out.write("TRY DELETE /book_db");
		out.newLine();
		out.write("PUT /book_db {\"mappings\": {\"book\": {\"properties\": {\"title\": {\"type\": \"string\"}, \"author\": {\"type\": \"string\"}}}}}");
		out.newLine();
		out.write("BULK INDEX book_db/book");
		out.newLine();
		String a="abcdefghijklmnopqrstuvwxyz";
		int c=1;
	
		 printWords(1,out);
            out.close();
        } catch (IOException e) {}
    }
}