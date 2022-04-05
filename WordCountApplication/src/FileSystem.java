
public class FileSystem {
	
    public String filename, content;
    public int wordCount, charCount, lineCount;

    public FileSystem(String fn, String ct, int wc, int cc, int lc) {
        this.filename = fn;
        this.content = ct;
        this.wordCount = wc;
        this.charCount = cc;
        this.lineCount = lc;
    }

	public String getFilename() {
		return filename;
	}

	public String getContent() {
		return content;
	}

	public void setContents(String content) {
		this.content = content;
	}

	public int getWordCount() {
		return wordCount;
	}

	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}

	public int getCharCount() {
		return charCount;
	}

	public void setCharCount(int charCount) {
		this.charCount = charCount;
	}

	public int getLineCount() {
		return lineCount;
	}

	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}
	
	@Override
	public String toString() {
		return ("File in Cache: " + this.getFilename());
	}
}
