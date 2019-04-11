package oop.ex6.main;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * The class runs the s-java validator
 */
public class Sjavac {
	/**
	 * The main method
	 * @param args an s-java file
	 */
    public static void main(String[] args) {
        if (args.length != 1) {
			System.err.println("Usage: java oop.ex6.main.Sjavac <s-java file>");
			System.out.println(2);
			System.exit(1);
        }
        File fileToValidate = new File(args[0]);
        // Check if the file is indeed an existing file
        if (!fileToValidate.exists() || fileToValidate.isDirectory()) {
			System.err.println("No file found");
			System.out.println(2);
			System.exit(1);
		}
		FileValidator validator = new FileValidator();
        try {
			validator.validate(fileToValidate);
			System.out.println(0);
		}
		catch (ValidatorException ex) {
        	System.err.println(ex.getMessage());
			System.out.println(1);
		} catch (FileNotFoundException e) {
			System.err.println("No file found");
			System.out.println(2);
			System.exit(1);
		}
	}
}
