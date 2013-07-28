package com.zeroturnaround.rebellabs.oceanofmemories.article1.classlayout;

import com.zeroturnaround.rebellabs.oceanofmemories.article1.SampleBaseClass;
import com.zeroturnaround.rebellabs.oceanofmemories.article1.SampleClass;
import com.zeroturnaround.rebellabs.oceanofmemories.common.util.JvmUtil;

public class ClassMemoryLayoutDemo {

	public static void main(String[] args) throws Exception {
		JvmUtil.info();
		
		///////////////////////////////////////////////////////////////////////////////////////

		long addressOfObjectClass = JvmUtil.addressOfClass(Object.class);
		long addressOfClass = JvmUtil.addressOfClass(SampleClass.class);
		long addressOfBaseClass = JvmUtil.addressOfClass(SampleBaseClass.class);
		
		System.out.println("Address of java.lang.Object class: 0x" + Long.toHexString(addressOfObjectClass));
		
		System.out.println();

		System.out.println("Memory layout of SampleClass at 0x"  + Long.toHexString(addressOfClass) + ":");
		System.out.println("==========================================================");
		JvmUtil.dump(addressOfClass, 32 * JvmUtil.WORD);
		System.out.println("==========================================================");
		
		System.out.println();
		
		System.out.println("Memory layout of SampleBaseClass at 0x"  + Long.toHexString(addressOfBaseClass) + ":");
		System.out.println("==========================================================");
		JvmUtil.dump(addressOfBaseClass, 64 * JvmUtil.WORD);
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

}
