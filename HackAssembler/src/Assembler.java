import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

public class Assembler {
	
	// Fields
	private File hackCode;				// Input kinda
	private BufferedWriter machineCode;	// Output kinda
	
	private SymbolTable symbolTable;
	private Code coder;
	
	
	// Starting method whatever that was called
	public Assembler(File input, File output) throws IOException {
		
		this.hackCode = input;							// Initialize
		FileWriter fw = new FileWriter(output);			// Create writer
		this.machineCode = new BufferedWriter(fw);		// Create buffered writer
		
		this.coder = new Code();						// Initializing code and symbolTable
		this.symbolTable = new SymbolTable();			// ^^
	}
	

	public void translate() throws IOException { 		// Call label recorder then parse
		this.labelRecorder();
		this.parse();
	}
	
	private void labelRecorder() throws IOException {	// Runs an initial parse thru the code to find labels and write down address
		Parser labelParse = new Parser(this.hackCode);
		
		while (labelParse.hasNext()) {		// Starts parse
			labelParse.advance();					// Runs thru parse
			
			Type commandType = labelParse.commandType();	// Decides on type
			
			if (commandType.equals(Type.L)) {	// If type is Label
				String symbol = labelParse.symbol();
				int address = this.symbolTable.getProgramAddress();		// Get address recorded
				this.symbolTable.addEntry(symbol, address);
			} else {
				this.symbolTable.incrementProgramAddress();
			}
		}
		labelParse.close();		// Close parser
	}
		

	private void parse() throws IOException {		// Parse source file
		Parser parse = new Parser(this.hackCode);
		
		while (parse.hasNext()) {	// If there is more
			parse.advance();		// Continue
	
			Type comType = parse.commandType();	// Decide type
			String instruction = null;	// Initialize
			
			if (comType.equals(Type.A)) {
				
				String symbol = parse.symbol();					// Format A-Instruction.
				Character firstChar = symbol.charAt(0);	// ^^
				boolean isSymbol = (!Character.isDigit(firstChar)); // ^^
				
				String address = null;
				if (isSymbol) {
					boolean symbolExists = this.symbolTable.contains(symbol);
					
					if (!symbolExists) {		// If symbol does not exist
						int dataAddress = this.symbolTable.getDataAddress();
						this.symbolTable.addEntry(symbol, dataAddress);
						this.symbolTable.incrementDataAddress();
					}
						
					
					address = Integer.toString(this.symbolTable.getAddress(symbol));	// Get symbol address
				} else {
					address = symbol;
				}
				
				instruction = this.formatAInstruction(address);
			} 
			else if (comType.equals(Type.C)) {
				String comp = parse.comp();	// C Type parsing
				String dest = parse.dest();	// ^^
				String jump = parse.jump();	// ^^	
				instruction = this.formatCInstruction(comp, dest, jump);
			}
	
			if (!comType.equals(Type.L)) {
				this.machineCode.write(instruction);	// Writes command to output
				this.machineCode.newLine();
			}
		}
		
		parse.close();
		this.machineCode.close();
	}

	
	private String formatAInstruction(String address) {			// Format the A types
		String formattedNumber = this.coder.formatNumberAsBinary(address);
		return "0" + formattedNumber;
	}
	
	private String formatCInstruction( String comp, String dest, String jump) {		// Format the C types
		StringWriter instruction = new StringWriter();
		instruction.append("111");
		instruction.append(this.coder.comp(comp));
		instruction.append(this.coder.dest(dest));
		instruction.append(this.coder.jump(jump));
		return instruction.toString();
	}
}