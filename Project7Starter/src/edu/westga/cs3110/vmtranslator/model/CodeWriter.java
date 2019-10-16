package edu.westga.cs3110.vmtranslator.model;
/**
 * 
 * @author Andrew Stoddard and Kenneth Wilson
 *
 */
public class CodeWriter {

	private static int eqCounter = 0;

	public static String add() {
		StringBuilder sb = new StringBuilder();
		sb.append("@SP\n");
		sb.append("AM=M-1\n");
		sb.append("D=M\n");
		sb.append("A=A-1\n");
		sb.append("M=D+M\n");
		return sb.toString();
	}

	public static String sub() {
		StringBuilder sb = new StringBuilder();
		sb.append("@SP\n");
		sb.append("AM=M-1\n");
		sb.append("D=M\n");
		sb.append("A=A-1\n");
		sb.append("M=M-D\n");
		return sb.toString();
	}

	public static String neg() {
		StringBuilder sb = new StringBuilder();
		sb.append("@SP\n");
		sb.append("A=M-1\n");
		sb.append("M=-M\n");
		return sb.toString();
	}

	public static String eq() {
		StringBuilder sb = new StringBuilder();
		sb.append("@SP\n");
		sb.append("AM=M-1\n");
		sb.append("D=M\n");
		sb.append("A=A-1\n");
		sb.append("D=M-D\n");
		sb.append("@EQ_JUMP" + eqCounter + "\n");
		sb.append("D;JEQ\n");
		sb.append("D=-1\n");
		sb.append("(EQ_JUMP" + eqCounter + ")\n");
		sb.append("@SP\n");
		sb.append("A=M-1\n");
		sb.append("M=!D\n");
		eqCounter++;
		return sb.toString();
	}

	public static String gt() {
		StringBuilder sb = new StringBuilder();
		sb.append("@SP\n");
		sb.append("AM=M-1\n");
		sb.append("D=M\n");
		sb.append("A=A-1\n");
		sb.append("D=M-D\n");
		sb.append("@GT_JUMP" + eqCounter + "\n");
		sb.append("D;JGT\n");
		sb.append("@SP\n");
		sb.append("A=M-1\n");
		sb.append("M=0\n");
		sb.append("@GT_EXIT" + eqCounter + "\n");
		sb.append("0;JMP\n");
		sb.append("(GT_JUMP" + eqCounter + ")\n");
		sb.append("@SP\n");
		sb.append("A=M-1\n");
		sb.append("M=-1\n");
		sb.append("(GT_EXIT" + eqCounter + ")\n");
		eqCounter++;
		return sb.toString();
	}

	public static String lt() {
		StringBuilder sb = new StringBuilder();
		sb.append("@SP\n");
		sb.append("AM=M-1\n");
		sb.append("D=M\n");
		sb.append("A=A-1\n");
		sb.append("D=M-D\n");
		sb.append("@LT_JUMP" + eqCounter + "\n");
		sb.append("D;JLT\n");
		sb.append("@SP\n");
		sb.append("A=M-1\n");
		sb.append("M=0\n");
		sb.append("@LT_EXIT" + eqCounter + "\n");
		sb.append("0;JMP\n");
		sb.append("(LT_JUMP" + eqCounter + ")\n");
		sb.append("@SP\n");
		sb.append("A=M-1\n");
		sb.append("M=-1\n");
		sb.append("(LT_EXIT" + eqCounter + ")\n");
		eqCounter++;
		return sb.toString();

	}

	public static String and() {
		StringBuilder sb = new StringBuilder();
		sb.append("@SP\n");
		sb.append("AM=M-1\n");
		sb.append("D=M\n");
		sb.append("A=A-1\n");
		sb.append("M=D&M\n");
		return sb.toString();
	}

	public static String or() {
		StringBuilder sb = new StringBuilder();
		sb.append("@SP\n");
		sb.append("AM=M-1\n");
		sb.append("D=M\n");
		sb.append("A=A-1\n");
		sb.append("M=D|M\n");
		return sb.toString();
	}

	public static String not() {
		StringBuilder sb = new StringBuilder();
		sb.append("@SP\n");
		sb.append("A=M-1\n");
		sb.append("M=!M\n");
		return sb.toString();
	}

	public static String static_pop(String name, int offset) {
		String location = "@" + name + "." + offset + "\n";
		StringBuilder sb = new StringBuilder();
		sb.append(location);
		sb.append("D=A\n");
		sb.append(commonPop());

		return sb.toString();
	}

	public static String static_push(String name, int offset) {
		String location = "@" + name + "." + offset + "\n";
		StringBuilder sb = new StringBuilder();
		sb.append(location);
		sb.append("D=M\n");
		sb.append(commonPush());

		return sb.toString();
	}

	public static String push(String segment, int offset) {
		StringBuilder sb = new StringBuilder();
		switch (segment) {
		case "local":
			sb.append("@LCL\n");
			sb.append(callOffsetPush(offset, "M"));
			break;
		case "constant":
			sb.append("@" + offset + "\n");
			sb.append("D=A\n");
			break;
		case "pointer":
			if (offset == 0) {
				sb.append("@THIS\n");
				sb.append("D=M\n");
			} else {
				sb.append("@THAT\n");
				sb.append("D=M\n");
			}
			break;
		case "temp":
			sb.append("@R5\n");
			sb.append(callOffsetPush(offset, "A"));
			break;
		case "argument":
			sb.append("@ARG\n");
			sb.append(callOffsetPush(offset, "M"));
			break;
		case "this":
			sb.append("@THIS\n");
			sb.append(callOffsetPush(offset, "M"));
			break;
		case "that":
			sb.append("@THAT\n");
			sb.append(callOffsetPush(offset, "M"));
			break;
		}
		sb.append(commonPush());

		return sb.toString();
	}

	public static String pop(String segment, int offset) {
		StringBuilder sb = new StringBuilder();
		switch (segment) {
		case "local":
			sb.append("@LCL\n");
			sb.append(callOffsetPop(offset, "M"));
			break;
		case "pointer":
			if (offset == 0) {
				sb.append("@THIS\n");
				sb.append("D=A\n");
			} else {
				sb.append("@THAT\n");
				sb.append("D=A\n");
			}
			break;
		case "temp":
			sb.append("@R5\n");
			sb.append(callOffsetPop(offset, "A"));
			break;
		case "argument":
			sb.append("@ARG\n");
			sb.append(callOffsetPop(offset, "M"));
			break;
		case "this":
			sb.append("@THIS\n");
			sb.append(callOffsetPop(offset, "M"));
			break;
		case "that":
			sb.append("@THAT\n");
			sb.append(callOffsetPop(offset, "M"));
			break;
		}
		sb.append(commonPop());

		return sb.toString();
	}

	private static String callOffsetPop(int offset, String choice) {
		StringBuilder sb = new StringBuilder();
		sb.append("D=" + choice + "\n");
		sb.append("@" + offset + "\n");
		sb.append("D=D+A\n");
		return sb.toString();
	}

	private static String callOffsetPush(int offset, String choice) {
		StringBuilder sb = new StringBuilder();
		sb.append("D=" + choice + "\n");
		sb.append("@" + offset + "\n");
		sb.append("A=D+A\n");
		sb.append("D=M\n");
		return sb.toString();
	}

	private static String commonPush() {

		StringBuilder sb = new StringBuilder();
		sb.append("@SP\n");
		sb.append("A=M\n");
		sb.append("M=D\n");
		sb.append("@SP\n");
		sb.append("M=M+1\n");

		return sb.toString();
	}

	private static String commonPop() {

		StringBuilder sb = new StringBuilder();
		sb.append("@R13\n");
		sb.append("M=D\n");
		sb.append("@SP\n");
		sb.append("AM=M-1\n");
		sb.append("D=M\n");
		sb.append("@R13\n");
		sb.append("A=M\n");
		sb.append("M=D\n");

		return sb.toString();
	}

}
