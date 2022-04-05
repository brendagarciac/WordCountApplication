import java.io.*;

import javax.swing.JOptionPane;

public class Data_store {
	
	public String filename, content;
    public int wordCount, charCount, lineCount;

    public Data_store(String fn, String ct, int wc, int cc, int lc) {
        this.filename = fn;
        this.content = ct;
        this.wordCount = wc;
        this.charCount = cc;
        this.lineCount = lc;
    }
    
    public void Database(String fn, String ct, int wc, int cc, int lc){

    	try {
    		FileWriter fw = new FileWriter("database/" + fn + ".txt");//append data, not override
    		BufferedWriter bw = new BufferedWriter(fw);
    		PrintWriter pw = new PrintWriter(bw);
    		
    		pw.println(fn + "$\n" + ct + "$\n" + wc + "$\n" + cc + "$\n" + lc + "$");
    		pw.flush();
    		pw.close();
    		
    		JOptionPane.showMessageDialog(null, "File saved");
    	}
    	catch(Exception E){
    		JOptionPane.showMessageDialog(null, "File not saved");

    	}
    }
}
