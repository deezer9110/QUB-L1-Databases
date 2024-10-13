package main;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class MainApp {

	static Scanner input = new Scanner(System.in);
	static String host = "jdbc:mysql://[host]:";
	static String port = "3306/";
	static String DB = "[database]";
	static String username = "[user]";
	static String password = "[password]";
	static String urlDB = host + port + DB;

	public static void main(String[] args) {

		boolean quit = false;
		String[] options = new String[] { "Insert new item", "Display a view", "Quit" };
		Menu mainMenu = new Menu("Main", options);
		do {
			int choice = mainMenu.getChoice();
			switch (choice) {
			case 0:
				insertNewItem();
				break;
			case 1:
				displayView();
				break;
			case 2:
				quit = true;
			}
		} while (quit == false);

		System.out.println("Exiting program!");
		System.exit(1);
	}

	public static void insertNewItem() {

		String tableName;
		tableName = pickTable();
		if (!tableName.equals("Quit")) {

			InsertItem newItem = new InsertItem(tableName);
			newItem.setValues(pickValues(newItem));

			boolean result = newItem.insertItem();
			if (result) {
				System.out.println("Success!");
			} else {
				System.out.println("Failed!");
			}
		} else {
			System.out.println("Back to main menu!");
		}

	}

	public static String pickTable() {
		String[] options = new String[] { "ProjectApplicant", "ProjectApplication", "ProjectDegree", "ProjectEmployer",
				"ProjectInterview", "ProjectInterviewer", "ProjectInterviewInterviewers", "ProjectJobOpening",
				"ProjectPlacement", "ProjectStatus", "Quit" };
		Menu tableMenu = new Menu("Table Menu", options);
		int choice = tableMenu.getChoice();
		if (choice < 0 || choice >= options.length) {
			System.out.println("Invalid selection");
			return pickTable();
		}
		return options[choice];
	}

	public static String[] pickValues(InsertItem newItem) {
		String[] columns = newItem.getColumns();
		String[] types = newItem.getColumnTypes();
		String[] values = new String[columns.length];
		for (int i = 0; i < columns.length; i++) {
			System.out.println(columns[i] + " [type = " + types[i] + "]: ");
			selectItem(types[i], i, values);
		}
		return values;
	}

	public static boolean selectItem(String type, int i, String[] values) {
		try {
			switch (type) {
			case "varchar":
				values[i] = input.nextLine();
				break;
			case "int":
				values[i] = Integer.toString(input.nextInt());
				input.nextLine();
				break;
			case "smallint/boolean":
				int option = input.nextInt();
				input.nextLine();
				if (option != 0 || option != 1) {
					throw new Exception();
				}
				values[i] = Integer.toString(option);
				break;
			case "date":
				System.out.println("Enter the Date [yyyy-MM-dd]: ");
				String date = input.nextLine();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				format.parse(date);
				values[i] = date;
				break;
			case "double":
				values[i] = Double.toString(input.nextDouble());
			}

			return true;
		} catch (Exception e) {
			System.out.println("Error: Input error");
			input.nextLine();
			return selectItem(type, i, values);
		}
	}

	public static void displayView() {

		String[] options = new String[] { "ApplicantExperienceBelfast", "ApplicantJourneys", "CurrentlyActiveJobs",
				"DegreeCountInApplicants", "InterviewReports", "JobsByEmployers", "NumberOfJobsInIndustries", "Quit" };
		Menu viewMenu = new Menu("View Menu", options);
		int choice = viewMenu.getChoice();
		if (choice < 0 || choice >= options.length) {
			displayView();
			return;
		}
		if (choice != options.length - 1) {

			String viewName;
			viewName = options[choice];
			switch (viewName) {

			}

			try {
				Connection con = DriverManager.getConnection(urlDB, username, password);
				Statement stmt = con.createStatement();

				ResultSet rs = stmt.executeQuery("SELECT * FROM " + viewName);
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();

				System.out.println("\n" + viewName + "\n********************\n");

				while (rs.next()) {
					String rec = "";
					for (int i = 1; i <= columnCount; i++) {
						rec = rsmd.getColumnLabel(i) + ": " + rs.getString(i);
						System.out.println(rec);
					}
					System.out.println("\n=======================\n");
				}
				stmt.close();
				con.close();
			} catch (Exception e) {
				System.out.println("Error: " + e);
				displayView();
			}
		}

	}

}
