# WordCountApplication

⭐️ Design

⦿ Scope/Overview:

The Java project executed had the goal of designing and developing a program that implements  one multi-threaded server that works as the word count application for the various client connections to the system. The word count system follows certain rules that define what each element in the text file is. Each client connection is a new thread that gets created in the server and handles the requests made by that client which range from choosing to view the contents of an uploaded file to deleting an already uploaded file. The application has a database tier that stores relevant data associated with the system. This tier is implemented through the use of a global space inside the directory where the program files are located. Each file stored in this tier holds important information about the files that can then be accessed by the client even after they have shut the application down and ran it again. Additionally, a cache service that all clients can access is implemented to hold data elements of interest to the clients: line count of the file, word count of the file, among other things. The client and server java application compile and run from the command line by invoking instances of the Java Virtual machine. A reliable communication between server and client, and vice versa, was accomplished through the implementation of a TCP protocol with Java classes such as Socket and ServerSocket.

⦿ Data Design

📌 Input data

The Word Count System passes data inputted by the client to the server. Throughout the program, the client is asked for input on their choice of request from the displayed menu. This user choice is then read by the server which returns back an “echo” of what the user inputted as her/his choice from the menu. In certain cases that require for the filename of the client’s text file, they are required to input the filename which is sent to and echoed back by the server. According with the choice that the user selects from the menu and what filename they input, certain changes are made inside the system in a synchronized manner to keep every element in sync. For example, if a user decides to update a file, then not just the cache needs to be updated (this is if the element is in cache) but also the database needs to work accordingly with the requests from each client and update the file as well.

📌 Cache Structure

The cache was implemented as an array list of objects that are instances of the class FileSystem. This data structure was used due its dynamic size capability, which enables the cache to not be infinite sized but rather it is the size of the number of elements that are within the array. The different methods of an array list in Java are used to add and remove objects from the array by looping through it until an object that holds the same filename as the one requested by the client is found.

📌 Database Structure

The database for this application is a folder called “database” that is found with the .java classes along with the text files that the client can choose to upload to the system. Different text files will be written to, read, and deleted from this folder that serves as a persistent storage for the client-server system. Data is written to the database by separating each attribute with a “$” and new line character which also help the server to differentiate each element as it scans the file.

📌 Output data

Output data in this program is considered to be the messages that show up under both the client and server in response to an input data. The messages show confirmation of a request’s success, like when a file is updated successfully, the client can read a message in the command prompt that says so. The server also outputs the inputted data by the client demonstrating that it was read well through the socket. Additionally, it was the designer’s choice to output the state of the cache throughout the sever as requests come in order to keep track of the files whose information is stored inside it. When a client is connected or closes the system, the server also outputs a message about it for its relevance in the system.

⦿ Architectural Design

📌 Initialization

TCP provides a two-way communication protocol that enables data to be sent across both streams at the same time. On the client's end of the communication, a client application generates a socket and tries to connect it to a server. On the server's end of the communication, the server constructs a socket object. Writing to and reading from the socket allows the client and the server to communicate. Initially, once the client runs the program in the command prompt, and a connection is successfully established, the client class outputs a menu that asks the client to choose between the different options that the application offers:

1. Upload a file: This option lets users upload a file to the system. In this case, the server reads the option chosen by the client and inside an if statement implements a certain structure for the uploading of the file. If the cache and database do not have the file then the file is added with no problem, yet if the cache has the file, an error message is sent to the user informing them that the file has been added. The latter error is thrown too if the file is found in the database, however, if the cache happens to not have the file then the file’s information is gotten from the database and sent to the cache for addition.
2. Update an existing file: This option lets users update a file in the system. In this case, the server reads the option chosen by the client and inside an if statement implements a certain structure for the updating of the file. If the cache and database do not have the file then an error is thrown because a file that does not exist cannot be updated. If the cache or database have the file’s information, then both the database and cache get updated.
3. Remove an existing file: This option lets users delete a file in the system. In this case, the server reads the option chosen by the client and inside an if statement implements a certain structure for the deletion of the file. If the cache and database do not have the file then an error is thrown because a file that does not exist cannot be deleted. If the cache or database have the file’s information, then both the database and cache delete it.
4. See all stored files: This option lets users see all files in the system. In this case, the server reads the option chosen by the client and returns all filenames that are read from the database.
5. Read an existing file, 6. Get # of lines in an existing file, 7. Get # of words in an existing file, 8. Get # of characters in an existing file, 9. Get total characteristics of an existing file (lines, words, characters): These options let the users see the different characteristics of a file in the system respectively. In this case, the server reads the option chosen by the client and returns the corresponding requested information. If the cache contains the requested data then the information is gotten from the cache and outputted. Such is the case for when the data is in the database but not in the cache, with the exception that the cache gets updated with the data from the database as well. If database, nor cache, have the file’s information, then an according error message is sent to the client.

📌 Client

The client program is implemented as a separate class in Java. This component interacts with end user, as well as with the server. A graphical UI was not implemented, and rather it is kept to the command prompt. The client provides options to send a text file over to the server for the processing of it, however, it is required for the user to enter an option from the menu, plus, they must supply just the name (no file extension needed) of the text file in most cases. Output is displayed on the client’s screen. In addition, the client will name their file and provide instructions on how to handle it, like store the file, update the file, and remove the file.

📌 Server

The Word Count Server receives each client's request. After that, the server will examine the request and complete its task by doing the action that was indicated. The client's request is made up of a text file and a set of choices. As a result, the server must communicate with a data layer as well as conduct its Word Count functionality. The server is multi-threaded which means that it allows various client threads to be created every time a new client connects to the server. 

📌 Word Count System Grammar Rules

Based on particular grammar rules, the designed/developed server acts as a Word Count parser. The server is responsible for enforcing grammatical rules and handling exceptions. The rules for parsing Word Count are represented by this grammar. In the grammar, lines and words are described. The number of characters that are valid within the grammar will be expressed as characters.

The Word Count rules are as follows; it is to be noted that a line is counted as words preceded by a new line character: 

file -> lines

lines -> line | lines line

line -> words ‘\n’ 

words -> word | words separator word 

word -> unit | word unit 

unit -> A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z| a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z| 0|1|2|3|4|5|6|7|8|9 

separator -> blank|dot| :| ;| - 

blank -> ‘ ‘ | blank ‘ ‘ 

dot -> ‘.’ | ‘.’ blank 
	
📌 Termination
	
The server waits for a client to enter 10 as their choice. When this option is selected by the client, a goodbye message shows up on the user’s screen, and on the server side it says that the connection between that client and the server is closed. Within the server, the thread finishes running, and the client socket is closed. 
	
📌 Class and Object Design/Modules
	
1. Client
•	main(String[])
o	The main is the entry point of the Client program. This class establishes a connection by providing host and port number and communicates with the server. It holds a welcome message, a menu, and the different methods to write and read to and from the server respectively.
2. Server
•	files : ArrayList<FileSystem> 
o	This data member called files is the cache system that holds instances of the class FileSystem. It allows for the lookup, deletion, and update of the cache elements. 
•	main(String[])
o	The main is the entry point of the Server program. The server listens on port 1989, and while new clients are coming in, it handles them by implementing a socket object to receive incoming clients’ requests and creating and running a thread that will handle each client independently. 
3. ClientHandler -> implements Runnable
•	clientSocket : Socket
o	A socket is an endpoint for communication between two machines. This data type is used for communication between the server and the client
•	ClientHandler(Socket)
o	Default constructor for the class which has as an attribute the socket object to receive incoming client requests
•	Cache(String, String, int, int, int)
o	This function is in charge of placing relevant data in the cache by creating an object of the FileSystem class and adding to the array list of objects of that class (i.e., the cache array).
•	CounterFile(String)
o	This is a key function in the program as it provides the server capability of counting characteristics from a file. This function calculates the word count, line count, character count, as well as retrieves the content. Lastly, if no errors occur, the database and cache functions get called so the calculated data can be stored in the two systems.
•	Database(String, String, int, int, int)
o	This function is in charge of placing relevant data in the database by creating an object of the Data_store class and calling the function within that class that is in charge of storing the information in the database as persistent data.
•	DBtoCache(String)
o	This function is in charge of updating the data in the cache by retrieving the information that is in the database. 
•	FileSearch(String)
o	This function is used to search for a file in the database to check if it exists or not.
•	getDataFromCache(FileSystem, String)
o	This function is used to get the data from the cache that the client requested. 
•	getDataFromDB(String, String)
o	This function is used to get certain requested data by the client from the database.
•	run()
o	Calling the run() method of a thread calls the code specified in it to be executed. This method in particular reads the client’s choice from the menu and executes different code depending on the choice. When a client enters “10”, the socket and connection ends for that client.
3. Data_store 
The following data members are used in the class:
•	charCount : int => details the number of characters in a text file.
•	content : String => withholds the content of a text file.
•	filename : String => details the filename.
•	lineCount : int => details the number of lines in a text file.
•	wordCount : int => details the number of words in a text file.
The following methods are used in the class:
•	Data_store(String, String, int, int, int)
o	Constructor for the class that passes in the filename, the content, and the line, word, and character count.
•	Database(String, String, int, int, int)
o	This function in the one that actually goes inside the database folder in the file where the Java files are located and creates a file that holds relevant data to the system. The text file created separates attributes by adding “$\n” at the end of one, which facilitates the reading of the file from the server.
4. FileSystem
The following data members are used in the class:
•	charCount : int => details the number of characters in a text file.
•	content : String => withholds the content of a text file.
•	filename : String => details the filename.
•	lineCount : int => details the number of lines in a text file.
•	wordCount : int => details the number of words in a text file.
The following methods are used in the class:
•	FileSystem(String, String, int, int, int)
o	Constructor for the cache class that passes in the filename, the content, and the line, word, and character count.
•	Getters: getCharCount(), getContent(), getFilename(), getLineCount(), getWordCount()
•	Setters: setCharCount(int), setContents(String), setLineCount(int), setWordCount(int)
•	toString()
o	This method returns a custom message that indicates the names of the files that are in cache. It is used to keep track of what gets saved in the cache as the application runs.

⭐️ Interface Design
	
The zip file called “WordCountApplication” should empty its content into one subdirectory. This subdirectory contains a folder that holds the five Java classes. The user is to open up two command prompts (one for the server to run, and another for the client, but different clients can run on different command prompts) and from within the program’s directory, compile the program by writing “javac *.java”. Right after, five “.class” files will be created for each Java class. The zip folder also contains 4 text files, each called “usa”, “france”, “spain”, and “germany”, which can be used by each client. Additional text files can also be added to the subdirectory if desired, but they need to be in the “.txt” format. To run the program, the user should type “java Server” on one command prompt, and “java Client” on the other command prompt(s). The command line for the client should pop up a welcome message with the menu right away and the server should say that a new client has been connected. Messages on the server and client are neatly organized and separated to provide a client with a more comprehensible UI. Additionally, if an error were to occur, a message indicating the error would be listed. Overall, within the command prompt, the user will find important information about the requests they make to the word count server, like when a file has been successfully uploaded, and when a file has been deleted. Additionally, an information-message dialog titled "Message" will show up when a file has been saved with no problem inside the database, which would be triggered either when the user uploads a new file or updates an existing file.




