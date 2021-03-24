
import java.util.Hashtable;

public class SymbolTable {
	
	// Fields
	private static final int DATA_START_ADD = 16;
	private static final int DATA_END_ADD = 16384;
	private static final int PROGRAM_START_ADD = 0;
	private static final int PROGRAM_END_ADD = 32767;
	
	private Hashtable<String, Integer> symbolAddMap;
	private int programAddress;
	private int dataAddress;
	
	public SymbolTable() {		// Starting method
		this.initSymbolTable();
		this.programAddress = PROGRAM_START_ADD;
		this.dataAddress = DATA_START_ADD;
	}
	
	private void initSymbolTable() {	// Initializes the symbol table 
		this.symbolAddMap = new Hashtable<String, Integer>();
		this.symbolAddMap.put("SP", 0);
		this.symbolAddMap.put("LCL", 1);
		this.symbolAddMap.put("ARG", 2);
		this.symbolAddMap.put("THIS", 3);
		this.symbolAddMap.put("THAT", 4);
		this.symbolAddMap.put("R0", 0);
		this.symbolAddMap.put("R1", 1);
		this.symbolAddMap.put("R2", 2);
		this.symbolAddMap.put("R3", 3);
		this.symbolAddMap.put("R4", 4);
		this.symbolAddMap.put("R5", 5);
		this.symbolAddMap.put("R6", 6);
		this.symbolAddMap.put("R7", 7);
		this.symbolAddMap.put("R8", 8);
		this.symbolAddMap.put("R9", 9);
		this.symbolAddMap.put("R10", 10);
		this.symbolAddMap.put("R11", 11);
		this.symbolAddMap.put("R12", 12);
		this.symbolAddMap.put("R13", 13);
		this.symbolAddMap.put("R14", 14);
		this.symbolAddMap.put("R15", 15);
		this.symbolAddMap.put("SCREEN", 16384);
		this.symbolAddMap.put("KBD", 24576);
	}
	
	
	public void addEntry(String symbol, int address) {		// Adds to the table
		this.symbolAddMap.put(symbol, Integer.valueOf(address));
	}
	
	
	public boolean contains(String symbol) {		// Checks if an entry exists
		return this.symbolAddMap.containsKey(symbol);
	}
	
	
	public int getAddress(String symbol) {		// Returns address
		return this.symbolAddMap.get(symbol);
	}
	
	public void incrementProgramAddress() {
		this.programAddress++;
		if (this.programAddress > PROGRAM_END_ADD) {
			throw new RuntimeException();
		}
	}
	
	public void incrementDataAddress() {
		this.dataAddress++;
		if (this.dataAddress > DATA_END_ADD) {
			throw new RuntimeException();
		}
	}
	
	public int getProgramAddress() {
		return this.programAddress;
	}
	
	public int getDataAddress() {
		return this.dataAddress;
	}
}