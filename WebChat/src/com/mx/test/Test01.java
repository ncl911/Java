package com.mx.test;

public class Test01 {
	
	public int bb(int a) { //length表示生成字符串的长度  
	  if(a==1)
		  return 10;
	  else
		  return bb(a-1)+2;
	 } 
	public static void main(String[] args) {		
		int str=new Test01().bb(8);
		System.out.println(str);
		
		
	}

}
