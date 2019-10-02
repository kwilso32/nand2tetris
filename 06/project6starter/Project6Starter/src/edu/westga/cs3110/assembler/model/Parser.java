package edu.westga.cs3110.assembler.model;

import java.io.*;
import java.util.Scanner;

/**
 * The call is responsible for generating the machine code It should make 2 or 3
 * passes through the assembly code
 * 
 * @author CS3110
 *
 */
public class Parser {
	private SymbolTable symbolTable;
	private Scanner sourceCode;
	private final String pad = "0000000000000000";

	/**
	 * The constructor should open the assembly code file and then execute the first
	 * pass if your assembler is a two pass assembler or the first two passes if
	 * your assembler is a three pass assembler. The pass (or passes) should strip
	 * away all comments and white spaces and add all labels to the symbol table.
	 * 
	 */
	public Parser(File file) {
		this.symbolTable = new SymbolTable();
		Scanner firstScan = null;

		try {
			firstScan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		StringBuilder sb1 = new StringBuilder();
		while (firstScan.hasNext()) {
			String line = firstScan.nextLine().strip().replace(" ", "");
			if (line.contains("//")) {
				line = line.substring(0, line.indexOf("//"));

			}
			if (!line.isEmpty()) {
				sb1.append(line + "\n");
			}

		}
		firstScan.close();

		Scanner secondScan = new Scanner(sb1.toString());
		StringBuilder sb2 = new StringBuilder();
		int count = 0;
		while (secondScan.hasNext()) {
			String line = secondScan.nextLine().strip();
			if (this.isLabel(line)) {
				this.symbolTable.add(line, count);

			} else {
				sb2.append(line + "\n");
				count++;
			}
		}
		secondScan.close();
		System.out.println(sb2.toString());
		this.sourceCode = new Scanner(sb2.toString());

		System.out.println();
		System.out.println();
		System.out.println();
		StringBuilder sb3 = new StringBuilder();
		while (this.sourceCode.hasNext()) {
			String line = this.parseNext();
			System.out.println(line);
			sb3.append(line + "\n");

		}
		File example = new File("src/example.hack");
		try {
			PrintWriter pw = new PrintWriter(example);
			pw.print(sb3.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		

	}

	/**
	 * returns true if sourceCode has another command to process
	 * 
	 */
	public boolean hasNext() {
		return this.sourceCode.hasNext();
	}

	/**
	 * return true if value is a literal number
	 * 
	 * @param value
	 * @return true if value is a number, false otherwise
	 */
	public boolean isLiteral(String value) {

		try {
			Integer.parseInt(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * returns the 16 bit binary string of the next command to process
	 * 
	 * @return the 16 bit machine code of the next instruction
	 */
	public String parseNext() {
		String line = this.sourceCode.nextLine();

		String result = "";

		if (line.startsWith("@")) {
			if (!this.isLiteral(line.substring(1)) && Character.isLowerCase(line.charAt(1))) {
				result = this.getBinaryStringForAInstruction(this.symbolTable.get(line));
			} else if (this.isLiteral(line.substring(1))) {
				result = this.getBinaryStringForAInstruction(Integer.parseInt(line.substring(1)));
			} else {
				// binary for a label

				String label = "(" + line.substring(1) + ")";

				result = this.getBinaryStringForAInstruction(this.symbolTable.get(label));
			}

		} else if (line.contains(";")) {
			result += "111";
			String[] firstSplit = line.split(";");
			String[] secondSplit = firstSplit[0].split("=");

			if (secondSplit.length == 2) {
				result += CInstruction.comp(secondSplit[1]);
				result += CInstruction.dest(secondSplit[0]);
				result += CInstruction.jump(firstSplit[1]);
			} else {
				result += CInstruction.comp("null");
				result += CInstruction.dest(secondSplit[0]);
				result += CInstruction.jump(firstSplit[1]);
			}
		} else if (!line.contains(";") && line.contains("=")) {
			String[] split = line.split("=");
			result += "111";
			result += CInstruction.comp(split[1]);
			result += CInstruction.dest(split[0]);
			result += CInstruction.jump("null");
		} else {
			result += "111";
			result = CInstruction.comp(line);
			result = CInstruction.dest("null");
			result += CInstruction.jump("null");

		}

		return result;
	}

	private String getBinaryStringForAInstruction(int ramPlace) {
		String result = "0";
		String binaryString = Integer.toBinaryString(ramPlace);
		for (int i = 0; i < (16 - (binaryString.length() + 1)); i++) {
			result += "0";
		}
		result += binaryString;
		return result;
	}

	/**
	 * returns true if line has the form (xxxx)
	 * 
	 * @param line a line of assembly code
	 * @return true if line is a label
	 */
	private boolean isLabel(String line) {
		return line.charAt(0) == '(';
	}

	/**
	 * accepts (xxxx) and returns xxxx
	 * 
	 * @param line
	 * @return
	 */
	private String getLabel(String line) {
		if (!this.isLabel(line)) {
			throw new IllegalArgumentException("Not a label");
		}
		return line.substring(1, line.length() - 1);
	}
}
