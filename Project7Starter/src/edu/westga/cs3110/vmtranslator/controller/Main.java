package edu.westga.cs3110.vmtranslator.controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import edu.westga.cs3110.vmtranslator.model.Parser;
/**
 * 
 * @author Andrew Stoddard and Kenneth Wilson
 *
 */
public class Main {

	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			System.err.println("Need VM code filename");
			System.exit(1);
		}
		for (int i = 0; i < args.length; i++) {
			Parser parser = new Parser(args[i]);
			String output_filename = args[i].substring(0, args[i].length() - 2) + "asm";
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(output_filename);
			} catch (FileNotFoundException err) {
				System.err.println("Can't create file: " + output_filename);
				System.exit(1);
			}
			while (parser.hasNext()) {
				writer.print(parser.parseNext());
			}

			writer.close();
		}

	}

}
