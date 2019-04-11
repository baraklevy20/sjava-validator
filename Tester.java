package oop.ex6.main;

import java.io.*;
import java.util.Scanner;

public class Tester {
	public static void main(String[] args) {
//		new Tester();
		Sjavac.main(new String[] {"test.sjava"});
	}

	private Tester() {
		runTests(new File("Tests"));
	}

	private void runTests(File file) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				runTests(f);
			}
		}
		else {
			runTest(file);
		}
	}

	private void runTest(File file) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			System.setOut(new PrintStream(baos));

			Sjavac.main(new String[]{file.getAbsolutePath()});
			boolean hadError = baos.toString().startsWith("1");
			System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

			Process p = Runtime.getRuntime().exec(
					"java \"oop/ex6/main/Sjavac\" \"" +
							file.getPath() + "\"");

			boolean hadErrorInReal = !new Scanner(p.getInputStream()).nextLine().equals("0");
			if (hadError != hadErrorInReal) {
				System.out.println("Mistake in " + file.getPath() + ". Should be " + (hadErrorInReal ? "bad" : "good"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
