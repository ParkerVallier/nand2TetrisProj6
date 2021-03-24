import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
	
	// Fields
	private BufferedReader reader;	// Reader and lines
	private String currentLine;		// ^^
	private String nextLine;		// ^^
	
	
	public Parser(File source) throws IOException {	// Starting method
	
		this.reader = new BufferedReader(new FileReader(source));	// Initializes the reader, current and next line
		this.currentLine = null;									// ^^
		this.nextLine = this.getNextLine();							// ^^
	}
	
	private String getNextLine() throws IOException {
		String nextLine;	
		
		do {	
			nextLine = this.reader.readLine();	// next line in the reader
			
			if (nextLine == null) {		// Line is empty
				return null;
			}
		} while (nextLine.trim().isEmpty() || this.isComment(nextLine));	// Checks if empty or comment
		
		int commentIndex = nextLine.indexOf("//");			// Gets the comment
		if (commentIndex != -1) {
			nextLine = nextLine.substring(0, commentIndex - 1);		// Gets rid of the comment, cuz 1010101010101's dont like comments :(
		}
		
		return nextLine;	// Return the next line
	}

	private boolean isComment(String input) {
		return input.trim().startsWith("//");	// Checks if its a comment
	}
	
	
	public void close() throws IOException {
		this.reader.close();	// Close the reader down
		
	}
	
	
	public boolean hasNext() {	// Has next??
		return (this.nextLine != null);
	}
	
	
	public void advance() throws IOException {	// Reads next and updates current and next
		this.currentLine = this.nextLine;
		this.nextLine = this.getNextLine();
	}
	
	public Type commandType() {		// Returns the type of the command
		String trimmedLine = this.currentLine.trim();
		
		if (trimmedLine.startsWith("(") && trimmedLine.endsWith(")")) {
			return Type.L;
		} else if (trimmedLine.startsWith("@")) {
			return Type.A;
		} else {
			return Type.C;
		}
	}
	
	public String symbol() {	// Returns the symbol if there is a symbol
		String trimmedLine = this.currentLine.trim();
		
		if (this.commandType().equals(Type.L)) {
			return trimmedLine.substring(1, this.currentLine.length() - 1);	// Gets rid of the ()
		} else if (this.commandType().equals(Type.A)) {		
			return trimmedLine.substring(1);		// Gets rid of the @
		} else {
			return null;
		}
	}
	
	public String dest() {	// Returns C dest
		
		String trimmed = this.currentLine.trim();
		
		int destI = trimmed.indexOf("=");
		
		if (destI == -1) {
			return null;
			
		} 
		else {
			return trimmed.substring(0, destI);
		}
	}
	

	public String comp() {	// Returns C comp
		
		String trimmed = this.currentLine.trim();
		
		int destI = trimmed.indexOf("=");
		
		if (destI != -1) {
			trimmed = trimmed.substring(destI + 1);
		}
		
		int compI = trimmed.indexOf(";");
		
		if (compI == -1) {
			return trimmed;
		} 
		else {
			return trimmed.substring(0, compI);
		}
	}
	

	public String jump() {	// Returns the C jump
		
		String trimmed = this.currentLine.trim();
		
		int destI = trimmed.indexOf(";");
		
		if (destI == -1) {
			return null;
		} 
		else {
			return trimmed.substring(destI + 1);
		}
	}
}