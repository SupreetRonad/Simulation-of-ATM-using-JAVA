package pr;

import java.util.*;
import java.io.*;


public class atm3 extends bank
{	
    static bank b=new bank();
	public static void main(String[] args) throws Exception
    {
        System.out.println("--- Simple ATM ---\n\n");
		int amount = 0,choice = 0,sum,balance = 0;
        int transactions[] = new int[5];
		int flag=0;
		int accno=0,psswd=0;		
		
		while(flag!=1){
			System.out.print("Enter your Ac/No. : ");
			accno = keyboard.nextInt();
			System.out.print("\nEnter Password : ");
			psswd = keyboard.nextInt();
			flag=b.check(accno,psswd);
			if(flag!=1)
				System.out.println("Username or password error\n");
		}
		
		System.out.println("\n Welcome "+b.name.toUpperCase()+"\n");
		
        while (choice != 6)
		{
         	choice = menu();
         	switch(choice)  
         	{
         		case 1:
					System.out.println("\n## Your Balance : "+blnc+"\n");
					break;
				case 2:
         			System.out.print("\nEnter amount to deposit : ");
         			sum = keyboard.nextInt();
					if(sum == 0)
						System.out.print("\n## ENTER VALID AMOUNT PLEASE --!\n");
					else
					{
						amount = (int) + sum;
						makeTransactions(amount, accno);
						System.out.print("\n## Amount added successfully --!\n");
					}
					break;
         		case 3:
					System.out.print("\nEnter amount to be withdrawn : ");
					sum = keyboard.nextInt();
					if(sum == 0)
						System.out.print("\n## ENTER VALID AMOUNT PLEASE --!\n");
					else
					{
						amount = (int) - sum;
						makeTransactions(amount, accno);
					}
					break;
         		case 4:
					showTransactions(accno);
					break;
         		case 5:
					int y=change(accno);
					break;
				case 6:
					System.out.println("\n!-- VISIT AGAIN, THANK YOU !! ");
					break;
         	}	
         	System.out.println();
        }
    }
    
	public static int menu()
	{
		int choice = 0;
		System.out.println("\n## OPTIONS --\n");
		System.out.println("1. Check Balance ");
		System.out.println("2. Deposit ");
		System.out.println("3. Withdrawl ");
		System.out.println("4. transactions ");
		System.out.println("5. Change pin ");
		System.out.println("6. End ");
		System.out.print("\nEnter Your choice : ");		
		choice = keyboard.nextInt();
		return choice;
	}
	
	public static int change(int accno) throws Exception
	{
		int psswd=0,cnfrm=0;
		System.out.print("\n Enter new Pin(4 DIGITS) : ");
		psswd = keyboard.nextInt();
		System.out.print("\n Confirm Pin : ");
		cnfrm = keyboard.nextInt();
		if(psswd != cnfrm){
			System.out.println("\n## PIN DIDN'T MATCH\n");
			return 0;
		}
		String name="",amt="";
		File f=new File("atmcr.txt");
		Scanner sc=new Scanner(f);
		FileWriter fw = new FileWriter("atmcpy.txt");
		while(sc.hasNextLine()){
			String ss=sc.nextLine();
			if(Integer.parseInt(ss.split(";")[0])==accno){
				name=ss.split(";")[1];
				amt=ss.split(";")[3];
			}
			else{
				fw.write(ss+'\n');
			}
		}
		fw.close();
		
		File f1=new File("atmcpy.txt");
		Scanner sc1=new Scanner(f1);
		FileWriter fw1 = new FileWriter("atmcr.txt",true);
		while(sc1.hasNextLine()){
			String ss=sc1.nextLine();
			fw1.write(ss+'\n');
		}
		fw1.write(accno+";"+name+";"+psswd+";"+amt+";");
		fw1.close();
		System.out.println("\n## PIN CHANGED SUCCESSFULLY --!\n");
		return 1;
	}
	
	public static void showTransactions(int accno) throws Exception
	{
		File file = new File("atmtr.txt");
		Scanner sc = new Scanner(file);
		System.out.println("\n\t    TRANSACTIONS");
		System.out.println("---------------------------------------\n");
		while(sc.hasNextLine()){
			String ss=sc.nextLine();
			if(Integer.parseInt(ss.split(";")[0])==accno)
				System.out.println(ss.split(";")[1]);
		}
	}
	
	public static void makeTransactions(int amount, int accno) throws Exception
	{
		String name="",psswd="";
		int amt=0;
		File f=new File("atmcr.txt");
		Scanner sc=new Scanner(f);
		FileWriter fw = new FileWriter("atmcpy.txt");
		while(sc.hasNextLine()){
			String ss=sc.nextLine();
			if(Integer.parseInt(ss.split(";")[0])==accno){
				name=ss.split(";")[1];
				psswd=ss.split(";")[2];
				amt=Integer.parseInt(ss.split(";")[3]);
			}
			else{
				fw.write(ss+'\n');
			}
		}
		amt+=amount;
		if(amt <= 100){
			System.out.println("\n##Amount Exceede minimum balance--!\n");
			return;
		}
		fw.close();
		System.out.println("\n## Total available balance is : "+amt);
		
		File f1=new File("atmcpy.txt");
		Scanner sc1=new Scanner(f1);
		FileWriter fw1 = new FileWriter("atmcr.txt");
		while(sc1.hasNextLine()){
			String ss=sc1.nextLine();
			fw1.write(ss+'\n');
		}
		fw1.write(accno+";"+name+";"+psswd+";"+amt+";");
		fw1.close();
		if(amount<0)
			System.out.print("\n## Amount withdrawn successfully --!\n");
		statements(accno,amount,amt);
		blnc=amt;
	}
}
