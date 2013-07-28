package com.zeroturnaround.rebellabs.oceanofmemories.article1.objectlayout;

import com.zeroturnaround.rebellabs.oceanofmemories.article1.SampleClass;
import com.zeroturnaround.rebellabs.oceanofmemories.common.util.JvmUtil;

public class ObjectMemoryLayoutDemo {

	public static void main(String[] args) {
		JvmUtil.info();
		
		System.out.println();
		
		///////////////////////////////////////////////////////////////////////////////////////
		
		long addressOfSampleClass = JvmUtil.addressOfClass(SampleClass.class);
		
		System.out.println("Address of SampleClass class is 0x"  + Long.toHexString(addressOfSampleClass));
		
		System.out.println();
		
		///////////////////////////////////////////////////////////////////////////////////////	

		SampleClass sampleClassObject = new SampleClass();
		long addressOfObject = JvmUtil.addressOf(sampleClassObject);

		System.out.println("Memory layout of SampleClass object at 0x"  + Long.toHexString(addressOfObject) + ":");
		System.out.println("==========================================================");
		JvmUtil.dump(addressOfObject, JvmUtil.sizeOf(sampleClassObject));
		System.out.println("==========================================================");
		
		System.out.println();
		
		//////////////////////////////////////////////////
		
		System.out.println("Field Layout of SampleClass: ");
		System.out.println("==========================================================");
		System.out.println(JvmUtil.fieldsLayoutAsString(SampleClass.class));
		System.out.println("==========================================================\n\n");
	}

}
