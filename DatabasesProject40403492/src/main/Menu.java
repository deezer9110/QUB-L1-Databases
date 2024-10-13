package main;

import java.util.Scanner;

public class Menu {

	private static Scanner input = new Scanner(System.in);
	private String title;
	private String[] options;

	public Menu(String Title, String[] Options) {
		setTitle(Title);
		setOptions(Options);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String[] getOptions() {
		return options;
	}

	private void setOptions(String[] options) {
		this.options = options;
	}

	public String displayOptions() {
		String result = "";

		result += "\n\n" + getTitle() + "\n**********************\n";
		options = getOptions();
		for (int i = 0; i < options.length; i++) {
			result += (i + 1) + ". " + options[i] + "\n";
		}

		result += "\nSelect a valid option: ";

		return result;
	}

	public int getChoice() {
		try {
			System.out.println(displayOptions());
			int choice = input.nextInt();
			return choice - 1;

		} catch (Exception e) {
			System.out.println("Error: " + e);
			input.nextLine();
			return getChoice();
		}

	}

}
