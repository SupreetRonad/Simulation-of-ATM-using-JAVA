package pr;

import java.util.*;
import java.io.*;

interface branch{
	public int check(int accno,int psswd) throws Exception;
}

class bank implements branch
{
	static int blnc=0;
	static Scanner keyboard = new Scanner(System.in);
	String name="";
	
	public int check(int accno,int psswd) throws Exception
	{
		File f=new File("atmcr.txt");
		Scanner sc=new Scanner(f);
		while(sc.hasNextLine()){
			String ss=sc.nextLine();
			if(Integer.parseInt(ss.split(";")[0])==accno && Integer.parseInt(ss.split(";")[2])==psswd){
				this.name=ss.split(";")[1];
				this.blnc=Integer.parseInt(ss.split(";")[3]);
				return 1;
			}
		}
		return 0;	
	}
	
	
	public static void statements(int accno,int amount,int bal) throws Exception
	{
		FileWriter fw = new FileWriter("atmtr.txt",true);
		String s="";
		if(amount>0)
			s="Was Deposited";
		else{
			s="Was Credited";
			amount=0-amount;
		}
		fw.write(accno+";"+amount+"\t"+s+"\tBALACNE : "+bal+"\n");
		fw.close();
	}
}