import java.io.*;

public class MainFrame {

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
		for (int k = 0; k < 26; k++)
		for(int i = 0; i <4; i++,c++)
		{   String book="";
	        String author="";
			for(int j = 0; j <=i ; j++)
             {  book+=a.charAt(k);
		        author+=a.charAt(25-k);
              //ut.write(a.charAt(k));
            	}
		//out.newLine();
		
		out.write("{\"_id\":" +c+", \"title\": \""+book+"\", \"author\":\""+author+"\"}");
		out.newLine();
		book="";
		author="";
		}
            out.close();
        } catch (IOException e) {}
    }
}