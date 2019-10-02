package edu.westga.cs3110.assembler.controller;

import java.io.*;

import edu.westga.cs3110.assembler.model.Parser;

/**
 * Must read a filename of the form example.asm from the
 * command and produce the file example.hack in the same directory
 * the input file is located in.
 * @author CS3110
 *
 */
public class Assembler {

	public static void main(String[] args) {
		
	       if( args == null || args.length == 0){
	    	      System.err.println("Need assembly code filename");
	    	      System.exit(0);
	       } 
	       
	       File inputFile = new File(args[0]);
//	       if(!inputFile.exists()) {
//	    	   System.err.println(args[0] + " not found");
//	    	   System.exit(1);
//	       }
	       Parser parser = new Parser(inputFile);

	}

}
