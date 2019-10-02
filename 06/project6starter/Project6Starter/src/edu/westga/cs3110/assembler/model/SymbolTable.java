package edu.westga.cs3110.assembler.model;

import java.util.Map;
import java.util.TreeMap;

/**
 * The SymbolTable Class stores the symbol table
 * in a Map<String,Integer> object
 * @author CS3110
 *
 */
public class SymbolTable {
	
  private Map<String,Integer> table;
  private int nextAddress;
  
  public SymbolTable(){
	  table = new TreeMap<String,Integer>();
	  for(int i=0;i<16;i++)
		  table.put("R"+i, i);
	  table.put("SCREEN", 16384);
	  table.put("KBD", 24576);
	  table.put("SP", 0);
	  table.put("LCL", 1);
	  table.put("ARG", 2);
	  table.put("THIS", 3);
	  table.put("THAT", 4);
	  nextAddress = 16;
  }
  
  /**
   * returns true if the symbol is in the table
   * 
   */
  public boolean contains(String symbol){
	 
	  return this.table.containsKey(symbol);
  }
  
  /**
   * adds label to the table with address nextAddress and increments nextAddress
   */
  public void add(String symbol){
   
     if(this.contains(symbol)) {
    	 throw new IllegalArgumentException("can't add duplicate symbols");
     }
     this.table.put(symbol, this.nextAddress);
     this.nextAddress++;
	  
  }
  
  /**
   * adds (symbol,address) to the table
   */
  public void add(String symbol,int address){
	  if(this.contains(symbol)) {
	    	 throw new IllegalArgumentException("can't add duplicate symbols");
	     }
	     this.table.put(symbol, address);
  }
  
  /**
   * return the address of the symbol

   */
  public int get(String symbol){
	  if(!this.contains(symbol)) {
		  this.add(symbol);
	  }
	  return this.table.get(symbol);

  }
}

