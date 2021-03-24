import java.io.File;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		// TODO: Handle input 
		
		if (args.length == 0) {
			// Exit Due to lack of file
			System.exit(1);
		}
		

		File sourceFile = new File(args[0].trim());	// Create Grab sourceFile
		String fileName = sourceFile.getName();
		
		
		String sAP = sourceFile.getAbsolutePath();		// Pathing of file	ie C:\Users\......
		int fNEI = fileName.lastIndexOf(".");			// File extension ie "test.asm" => asm
		String fileNameNE = fileName.substring(0, fNEI);	// File name with no extention, ie "test.asm" => "test"
		int fileNameI = sourceFile.getAbsolutePath().indexOf(sourceFile.getName());	// Index
		String sourceD = sAP.substring(0, fileNameI);	// Directory
		String outputFilePath = sourceD + fileNameNE + ".hack";	// New path
		File outputFile = new File(outputFilePath);		// New output file 
		
		try {
			if (outputFile.exists()) {
				outputFile.delete();	// Override file
			}
			outputFile.createNewFile();	// Create new file
			
			// Translate source file.
			Assembler assembler = new Assembler(sourceFile, outputFile);
			assembler.translate();
			
			
		} catch (IOException e) {
			// Something went wrong
			System.exit(1);
		}
	}
}