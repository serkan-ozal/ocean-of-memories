package com.zeroturnaround.rebellabs.oceanofmemories.article1;

public final class SampleClass extends SampleBaseClass {

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
