//Design Programming Project 3 
//Brenda Garcia
//CSIS 3810 - Operating Systems Concepts 
//Dr. Gregory Simco
//Due: December 12th, 2021

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;


//Server class
public class Server {
	
	static ArrayList<FileSystem> files = new ArrayList<>();

	public static void main(String[] args) {

		ServerSocket server = null;
		  
        try {
  
            // server is listening on port 1989
            server = new ServerSocket(1989);
            server.setReuseAddress(true);
  
            //while new clients are coming in, handle the clients 
            while (true) {

                // socket object to receive incoming client
                // requests
                Socket client = server.accept();
  
                // Displaying that new client is connected
                // to server
                System.out.println("New client connected " + client.getInetAddress().getHostAddress());
  
                // create a new thread object
                ClientHandler clientSock = new ClientHandler(client);
                Thread clientThread = new Thread(clientSock);
                
                // This thread will handle the client independently
                clientThread.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally { //close the server
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
  
    // ClientHandler class
    private static class ClientHandler implements Runnable {
    	
        private final Socket clientSocket;
  
        //Default Constructor
        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }
  
        public void run()
        {	
        	PrintWriter out = null;
            BufferedReader in = null;
            try {
                    
                  // get the output stream of client
                out = new PrintWriter(clientSocket.getOutputStream(), true);
  
                  // get the input stream of client
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  
                String userInput="";
                String filename="";
                while (!(userInput = in.readLine()).equals("10")) {
  
                    // writing the received message from client
                	System.out.printf("Option selected by the client: %s\n", userInput);
                    
                    if(userInput.equals("1")) { //user selects to upload a file
                    	filename = in.readLine();
                    	System.out.printf("Filename sent from the client: %s\n", filename);
                        
                        boolean foundFileCache = false;
                        boolean foundFileDatabase = false;
                    	foundFileDatabase = FileSearch(filename);

                        for(FileSystem file : files) {
                        	if (file.getFilename().equals(filename)) { //file is already in cache, meaning that it is in database as well 
                        		foundFileCache = true;
                        	}
                        }
                        
                        if(!foundFileCache) { //file wasn't found in cache, lets check in database
                        	if (foundFileDatabase) { //if found in database, update cache, and return error message
                        		//update cache with information obtained from database
                        		DBtoCache(filename);
                        		//err message
                        		out.println("Sorry, a file with that same name has already been uploaded before");//so file has already been uploaded, return error message
                        	}  
                        	else { //if not found in database, then file needs to be uploaded, along with its counts, and content
                        		boolean err = CounterFile(filename);
                        		
                				if(!err) {
                        			out.println("File '" + filename + "' cannot be found. Please upload a file that has been created");
                        		}
                        		else {
                        			out.println("File '" + filename + "' was successfully uploaded!");
                        		}
                        	}
                        }
                        else {
                    		out.println("Sorry, a file with that same name has already been uploaded before");//so file has already been uploaded, return error message
                        }
                    }
                    else if(userInput.equals("2")){
                    	filename = in.readLine();
                    	System.out.printf(" Filename sent from the client: %s\n", filename);
                    	
                    	boolean foundFileCache = false;
                        boolean foundFileDatabase = false;
                        
                        //check if cache has file, if it does update database and cache
                        for( int i = 0; i < files.size(); i++ ) {
                            FileSystem file = files.get(i);
                        	if (file.getFilename().equals(filename)) {  
                        		files.remove(file);
                        		foundFileCache = true;
                        	}
                        }
                        
                        if(foundFileCache) {
                        	CounterFile(filename);
                			out.println("File '" + filename + "' was successfully updated!");
                        }
                        else { //not in cache, thus check if file exists in database
                        	foundFileDatabase = FileSearch(filename);
                        	if (foundFileDatabase) { //update database, and update cache
                            	CounterFile(filename);
                    			out.println("File '" + filename + "' was successfully updated!");
                        	}  
                        	else { //file is not anywhere in system, so throw error
                    			out.println("File '" + filename + "' does not exist! It cannot be updated");
                        	}
                        }
                    }
                    else if(userInput.equals("3")){
                    	filename = in.readLine();
                    	System.out.printf(" Filename sent from the client: %s\n", filename);
                    	
                        boolean foundFileDatabase = false;
                        
                        for( int i = 0; i < files.size(); i++ ) { //loop through cache
                            FileSystem file = files.get(i);
                        	if (file.getFilename().equals(filename)) {  //if file found in cache remove it
                        		files.remove(file);
                        	}
                        }
                    	
                        foundFileDatabase = FileSearch(filename); //check if file is in database
                    	
                        if (foundFileDatabase) { //if file is in db, remove it from db
                        	File file = new File("database/" + filename + ".txt");
                            file.delete();
                			out.println("File '" + filename + "' was succesfully removed");
                    	}  
                    	else { //file is not anywhere in system, so throw error
                			out.println("File '" + filename + "' does not exist! It cannot be updated");
                    	}
                    }
                    else if(userInput.equals("4")) {
                    	File directory = new File("database/");
                        
                        // store all names in the array
                        String[] flist = directory.list(new FilenameFilter() {
                            @Override
                            public boolean accept(File dir, String name) {
                                return !name.equals(".DS_Store");
                            }
                        });
                        
                        //if array is empty print that there are no files stored
                        if (flist.length == 0) {
							out.println("Sorry! No files have been uploaded");
						}

                        // Linear search in the array
						for (int i = 0; i < flist.length; i++) {
							String name = flist[i];
							out.println(name);
						}
						//end condition for client
						out.println("done");
                    }
					else if (userInput.equals("5") || userInput.equals("6") || userInput.equals("7")
							|| userInput.equals("8") || userInput.equals("9")) {
						filename = in.readLine();
                    	System.out.printf("Filename sent from the client: %s\n", filename);
						
                    	boolean foundFileCache = false;
						boolean foundFileDatabase = false;
						
						foundFileDatabase = FileSearch(filename);

						//check if file is in cache
						for (FileSystem file : files) {
							if (file.getFilename().equals(filename)) {
								//get content of text file from cache & output it
								String cacheOutput = getDataFromCache(file, userInput);
								out.println(cacheOutput);
								out.println("done");
								foundFileCache = true;
							}
						}
						
						//file is not in cache, but it is in database
						if(!foundFileCache) {
							if(foundFileDatabase) {
								//get content of text file from database
								String output = getDataFromDB(filename, userInput);
								//update the cache with the information of the file
								DBtoCache(filename);
								//return to user the count that they requested
								out.println(output);
								out.println("done");

							}
							else { //file is not in database and it is not in cache
								//error message
								out.println("Sorry the file entered cannot be found. Make sure you have uploaded it!");
								out.println("done");
							}
						}
					}
                    
                    //print status of cache every time a request is made 
                    System.out.println("-------------------------START OF CACHE-------------------------");
                    for(FileSystem file : files) {
                    	System.out.println(file);
                    }
                    System.out.println("-------------------------END OF CACHE-------------------------");

                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    	System.out.println("Server closed");
                        clientSocket.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        public boolean FileSearch(String filename) {

        	// Replace the file path with path of the directory
            File directory = new File("database/");
      
            // store all names with same name
            String[] flist = directory.list();
            boolean flag = false; //boolean that returns false if file not found, and true if file found
            
            // Linear search in the array
            for (int i = 0; i < flist.length; i++) {
                String name = flist[i];
                if (name.equalsIgnoreCase(filename + ".txt")) {
                    flag = true; //file was found in database
                }
            }
            return flag;
        }
        
		public boolean CounterFile(String filename) {	
			boolean success = false;
			try {
		      	String content = "";
	        	int wordCount = 0;
	        	int charCount = 0;
	        	int lineCount = 0;
	        	
				File myObj = new File(filename + ".txt");
				FileReader fr = new FileReader(myObj);
				BufferedReader br = new BufferedReader(fr);

				String line;

				// read all content from the file and assign it to a variable
				DataInputStream dis = new DataInputStream(new FileInputStream(filename + ".txt"));
				byte[] datainBytes = new byte[dis.available()];
				dis.readFully(datainBytes);
				dis.close();

				content = new String(datainBytes, 0, datainBytes.length);

				// get the word count, line count, and char count
				while ((line = br.readLine()) != null) {
					if (!"".equals(line.trim())) {
						for (int i = 0; i < line.length(); i++) {
							if (Character.isLetter(line.charAt(i)) || Character.isDigit(line.charAt(i)))
								charCount++;
						}
						String words[] = line.split("[\\\\\s,;.:-]+"); // separators: space, comma, semicolon, period,
																		// colon, dash
						wordCount += words.length;
						String[] lines = line.split("\r\n");
						lineCount += lines.length;
					}
				}
				fr.close();

				// store all data in database
				Database(filename, content, wordCount, charCount, lineCount);
				// place data in cache
				Cache(filename, content, wordCount, charCount, lineCount);
				
				success = true;
			} catch (Exception E) {
				success = false;
			}
			return success;
		}
		
		public void Cache(String fn,String ct, int wc, int cc, int lc) {
			// place data in cache
			FileSystem file = new FileSystem(fn, ct, wc, cc, lc);
			files.add(file);
        }
		
		public void Database(String fn,String ct, int wc, int cc, int lc) {
			// store data in database
			Data_store database = new Data_store(fn, ct, wc, cc, lc);
			database.Database(fn, ct, wc, cc, lc);
		}
		
		public void DBtoCache(String filename) {
			//update data in cache from the information that is in database
			String fn = ""; String ct = ""; String wc = ""; String cc ="";  String lc ="";
			
			try {
				Scanner file = new Scanner(new File("database/" + filename + ".txt")); //open text file
				
				file.useDelimiter( "[$]\n"); //separate on the condition that it reaches a $

				while(file.hasNext()) { //assign variables respectively to each line 
					fn = file.next();
					ct = file.next();
					wc = file.next();
					cc = file.next();
					lc = file.next();
				}
				
				Cache(fn, ct, Integer.parseInt(wc), Integer.parseInt(cc), Integer.parseInt(lc)); //update data in cache
			
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		//get data from the database that client requested
		public String getDataFromDB(String filename, String option) {

			String ct = ""; String wc = ""; String cc =""; String lc ="";
			
			try {
				Scanner file = new Scanner(new File("database/" + filename + ".txt")); //open text file
				
				file.useDelimiter( "[$]\n"); //separate on the condition that it reaches a $

				while(file.hasNext()) { //assign variables respectively to each line 
					file.next();
					ct = file.next();
					wc = file.next();
					cc = file.next();
					lc = file.next();
				}
				
				if(option.equals("5")) {
					return "Content of the file: " + ct;
				}
				else if(option.equals("6")) {
					return "Number of lines: " + lc;
				}
				else if(option.equals("7")) {
					return "Number of words: " + wc;
				}
				else if(option.equals("8")) {
					return "Number of characters: " + cc;
				}
				else if(option.equals("9")){
					return  "Number of lines: "  + lc + " Number of words: " + wc + " Number of characters: " + cc;
				}
			
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		//get data from the cache that client requested
		public String getDataFromCache(FileSystem file, String option) {
			
			if(option.equals("5")) {
				return "Content of the file: " + file.getContent() + "\n";
			}
			else if(option.equals("6")) {
				return "Number of lines: " + file.getLineCount() + "\n";
			}
			else if(option.equals("7")) {
				return "Number of words: " + file.getWordCount() + "\n";
			}
			else if(option.equals("8")) {
				return "Number of characters: " + file.getCharCount() + "\n";
			}
			else if(option.equals("9")){
				return  "Number of you lines: "  + file.getLineCount() + "\n"  +
						"Number of words: " + file.getWordCount() + "\n"  +
						"Number of characters: " + file.getCharCount();
			}
			return null;
		}
    }
}
