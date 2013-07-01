package com.zeroturnaround.rebellabs.oceanofmemories.article1;

import java.io.Serializable;

public class SampleClass extends SampleBaseClass implements Serializable {

	private static final long serialVersionUID = 4140100526971264041L;

	private final static byte b = 100;
	
	private int i = 5;
	private long l = 10;
	
	public SampleClass() {
		
	}
	
	public SampleClass(int i, long l) {
		this.i = i;
		this.l = l;
	}
	
	public int getI() {
		return i;
	}
	
	public void setI(int i) {
		this.i = i;
	}

	public long getL() {
		return l;
	}
	
	public void setL(long l) {
		this.l = l;
	}

	public static byte getB() {
		return b;
	}
	
}
