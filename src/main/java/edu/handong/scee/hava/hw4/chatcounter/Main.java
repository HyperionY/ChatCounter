package edu.handong.scee.hava.hw4.chatcounter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * 
 * @author HyperionY
 * @Email yjyj989812 21700467@handong.edu
 * 
 *  create : 2018.06.17
 *
 */

public class Main {
	
	ArrayList<String> directories = new ArrayList<String>();
	FileSuperviser filesuperviser = new FileSuperviser();
	MessageSuperviser messagesuperviser = new MessageSuperviser();
	
	String path;
	boolean check;
	boolean alert;

	public static void main(String[] args) {
		Main equip = new Main();
		equip.actv(args);

	}

	private void actv(String[] args) {
		directories = setDirectory();
		
		Options option = createOption();
		
		if(parseOption(option, args)){
			if (alert){
				printAlert(option);
				return;
			}
			
			System.out.println("Provided \"" + path + "\" to use as value of the option p.");
			
			if(check) {
				System.out.println("Program eliminated. (This message mean that you maybe turn '-c' option.");
			}
		}
		
		directories.add(path);
		
		for(int i = 0; i< directories.size(); i++) {
			filesuperviser.ScanFile(directories.get(i));
		}
		messagesuperviser = filesuperviser.getMessageSuperviser();
		HashMap<String, Integer> inputdata = messagesuperviser.returnMap();
		TreeMap<Integer, String> sortdata = new TreeMap<Integer, String>();
		for(String key:inputdata.keySet()) {
			Integer value = inputdata.get(key);
			sortdata.put(value, key);
		}
		filesuperviser.TreeMapWriteFile(sortdata, "chatcount.csv");
		
	}
	
	public static class ScanFiles implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private ArrayList<String> setDirectory() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("input name of filedirectory? : ");
		ArrayList<String> filenames = new ArrayList<String>();
		filenames.add(keyboard.nextLine());
		keyboard.close();
		return filenames;
	}
	
	private boolean parseOption(Options option, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {
			CommandLine cmd = parser.parse(option, args);
			path = cmd.getOptionValue("p");
			check = cmd.hasOption("c");
			alert = cmd.hasOption("a");
			
		} catch (Exception e) {
			printAlert(option);
			return false;
		}
		return true;
	}
	
	private Options createOption() {
		Options opt = new Options();
		
		opt.addOption(Option.builder("p").longOpt("path")
				.desc("Display set a directory path or a file.")
				.hasArg()
				.argName("Path name to display")
				.required()
				.build());
		
		opt.addOption(Option.builder("c").longOpt("check")
				.desc("Display detailed messages.")
				.argName("check option")
				.build());
		
		opt.addOption(Option.builder("a").longOpt("alert")
		        .desc("Alert")
		        .build());

		return opt;
	}
	
	private void printAlert(Options opt) {
		HelpFormatter formatter = new HelpFormatter();
		String head = "chat counter program";
		String tail ="\nif have problem or issues, reported to https://github.com/HyperionY/ChatCounter";
		formatter.printHelp("HW3", head, opt, tail, true);
	}

}
