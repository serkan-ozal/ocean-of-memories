package com.zeroturnaround.rebellabs.oceanofmemories.article1.classlayout;

import com.zeroturnaround.rebellabs.oceanofmemories.common.util.JvmUtil;

public class ClassLayoutDemo {

	public static void main(String[] args) throws Exception {
		JvmUtil.info();
		
		///////////////////////////////////////////////////////////////////////////////////////
		
		long addressOfClass = JvmUtil.addressOfClass(SampleClass.class);
		long addressOfBaseClass = JvmUtil.addressOfClass(SampleBaseClass.class);
		
		
		System.out.println("Memory layout of SampleClass at 0x"  + Long.toHexString(addressOfClass) + ":");
		System.out.println("==========================================================");
		JvmUtil.dump(addressOfClass, 128);
		System.out.println("==========================================================");
		
		System.out.println();
		
		System.out.println("Memory layout of SampleBaseClass at 0x"  + Long.toHexString(addressOfBaseClass) + ":");
		System.out.println("==========================================================");
		JvmUtil.dump(addressOfBaseClass, 128);
		System.out.println("==========================================================");
		
		///////////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("Field Layout of SampleClass: ");
		System.out.println("==========================================================");
		System.out.println(JvmUtil.fieldsLayoutAsString(SampleClass.class));
		System.out.println("==========================================================\n\n");
		
		System.out.println();
		
		System.out.println("Field Layout of SampleBaseClass: ");
		System.out.println("==========================================================");
		System.out.println(JvmUtil.fieldsLayoutAsString(SampleBaseClass.class));
		System.out.println("==========================================================\n\n");
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////

	public static class SampleClass extends SampleBaseClass {
		
		private final static byte b = 100;
		
		private int i = 5;
		private long l = 10;
		
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
	
	public static class SampleBaseClass {
		
		protected short s = 20;

	}

}
