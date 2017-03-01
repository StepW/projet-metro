package trajetMetro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
	
	 public static void main(String[] args) {
		 
		 
		 String filename = "D:/JAVA/metro.txt";

		 File f = new File(filename);
		 Scanner in = null;
//		 try {
//			 in = new Scanner(f);
//		 while (in.hasNextLine()) {
//			 String line = in.nextLine();
//			 System.out.println(line);
//		 }
//		 } catch (FileNotFoundException e) {
//			 e.printStackTrace();
//		 } finally {
//			 if (in != null)
//			 in.close();
//		 }
		 

		 
		 
		 
		ArrayList<Station> l = new ArrayList<Station>();
		 
		 try{
				InputStream ips=new FileInputStream(filename); 
				InputStreamReader ipsr=new InputStreamReader(ips);
				BufferedReader br=new BufferedReader(ipsr);
				String ligne;

				while ((ligne=br.readLine())!=null){
//					if (ligne.startsWith("1:a:"))
//					{
						String[] st = ligne.split(":");

//						System.out.println("nom ligne = "+st[0]);
//						String l = ligne;
//						System.out.println(st[2]);
//					}
					l.add(new Station(st[2]));
		
				}
				br.close(); 
				
			}		
			catch (Exception e){
				System.out.println(e.toString());
			}
		 
		 for(int i=0;i<=l.size();i++){
			 System.out.println(l.get(i).hashCode());
		 }

		 
		 
		 

	 }




}
