package edu.westga.cs3110.vmtranslator.model;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
/**
 * 
 * @author Andrew Stoddard and Kenneth Wilson
 *
 */
public class Parser {

	private Scanner sourceCode;
	private String name;
	
	public Parser(String filename) {
		File file = new File(filename);
		this.name = file.getName().replaceAll("\\.vm", "");
		StringBuilder sb = new StringBuilder();
		try {
			Scanner scan = new Scanner(new File(filename));
			while (scan.hasNext()) {
				String line = scan.nextLine().trim();
				if (line.length() == 0 || line.contains("//"))
					continue;
				sb.append(line + "\n");
			}
			scan.close();
		} catch (IOException err) {
			System.err.println("IOError");
			err.printStackTrace();
			System.exit(1);
		}
		sourceCode = new Scanner(sb.toString());
	}

	public boolean hasNext() {
		return this.sourceCode.hasNext();
	}

	public String parseNext() {
		String line = this.sourceCode.nextLine().trim();
		String[] statement = line.trim().split(" ");
		String instruction = "";
		if (statement.length == 0) {
			throw new IllegalArgumentException("bad vm command");
		}
		switch (statement[0]) {
		case "add":
			instruction += CodeWriter.add();
			break;
		case "sub":
			instruction += CodeWriter.sub();
			break;
		case "neg":
			instruction += CodeWriter.neg();
			break;
		case "not":
			instruction += CodeWriter.not();
			break;
		case "or":
			instruction += CodeWriter.or();
			break;
		case "eq":
			instruction += CodeWriter.eq();
			break;
		case "lt":
			instruction += CodeWriter.lt();
			break;
		case "gt":
			instruction += CodeWriter.gt();
			break;
		case "and":
			instruction += CodeWriter.and();
			break;
		case "push":
			if (statement[1].equals("static")) {
				instruction = CodeWriter.static_push(name, Integer.parseInt(statement[2]));
			} else {
				instruction += CodeWriter.push(statement[1], Integer.parseInt(statement[2]));
			}
			break;
		case "pop":
			if (statement[1].equals("static")) {
				instruction = CodeWriter.static_pop(name, Integer.parseInt(statement[2]));

			} else {
				instruction += CodeWriter.pop(statement[1], Integer.parseInt(statement[2]));
			}
			break;
		}
		return instruction;

	}
}
