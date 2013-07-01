package com.zeroturnaround.rebellabs.oceanofmemories.article1.classlayout;

import com.zeroturnaround.rebellabs.oceanofmemories.article1.SampleClass;
import com.zeroturnaround.rebellabs.oceanofmemories.common.util.ClassUtil;

public class ClassFileLayoutDemo {

	public static void main(String[] args) throws Exception {
		ClassUtil.printContentOfClassFileByShowingPrintableCharacters(SampleClass.class);
	}
	
}
