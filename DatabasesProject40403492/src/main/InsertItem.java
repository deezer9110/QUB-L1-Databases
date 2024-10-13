package main;

import java.sql.*;

public class InsertItem {

	private String tableName;
	private String[] values;
	String host = "jdbc:mysql://[host]:";
	String port = "3306/";
	String DB = "[database]";
	String username = "[user]";
	String password = "[password]";
	String urlDB = host + port + DB;

	public InsertItem(String tableName) {
		setTableName(tableName);
	}

	public InsertItem(String tableName, String[] values) {
		setTableName(tableName);
		setValues(values);
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String[] getColumns() {

		switch (getTableName()) {

		case "ProjectApplicant":
			return new String[] { "applicantFName", "applicantSName", "applicantDOB", "applicantEmail",
					"applicantPhone", "applicantAddress", "applicantCity", "applicantYearsExperience", "degreeID", "CVLink" };
		case "ProjectApplication":
			return new String[] { "applicantID", "jobID", "appDate", "accepted" };
		case "ProjectDegree":
			return new String[] { "degreeName" };
		case "ProjectEmployer":
			return new String[] { "employerName", "employerAddress", "employerPhone", "employerEmail",
					"employerIndustry" };
		case "ProjectInterview":
			return new String[] { "applicantID", "jobID", "interviewFeedback", "accepted", "intDate" };
		case "ProjectInterviewer":
			return new String[] { "interviewerFName", "interviewerSName", "employerID" };
		case "ProjectInterviewInterviewers":
			return new String[] { "interviewID", "interviewerID" };
		case "ProjectJobOpening":
			return new String[] { "jobID", "employerID", "jobName", "jobDescription", "datePosted", "city", "salary",
					"yearsExperienceReq", "degreeID" };
		case "ProjectPlacement":
			return new String[] { "applicantID", "jobID", "sDate", "eDate", "statusID" };
		case "ProjectStatus":
			return new String[] { "statusDesc" };

		default:
			return new String[] { "Error occurred, couldn't create column array" };

		}
	}

	public String[] getColumnTypes() {
		switch (getTableName()) {

		case "ProjectApplicant":
			return new String[] { "varchar", "varchar", "date", "varchar", "varchar", "varchar", "varchar", "int",
					"int", "varchar" };
		case "ProjectApplication":
			return new String[] { "int", "int", "date", "tinyint/boolean" };
		case "ProjectDegree":
			return new String[] { "varchar" };
		case "ProjectEmployer":
			return new String[] { "varchar", "varchar", "varchar", "varchar", "varchar" };
		case "ProjectInterview":
			return new String[] { "int", "int", "varchar", "tinyint/boolean", "date" };
		case "ProjectInterviewer":
			return new String[] { "int", "varchar", "varchar", "int" };
		case "ProjectInterviewInterviewers":
			return new String[] { "int", "int" };
		case "ProjectJobOpening":
			return new String[] { "int", "varchar", "varchar", "date", "varchar", "double", "int", "int" };
		case "ProjectPlacement":
			return new String[] { "int", "int", "date", "date", "int" };
		case "ProjectStatus":
			return new String[] { "varchar" };

		default:
			return new String[] { "Error occurred, couldn't create column array" };

		}
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public boolean insertItem() {
		try {
			Connection con = DriverManager.getConnection(urlDB, username, password);
			Statement stmt = con.createStatement();
			String query = "INSERT INTO " + getTableName() + " (";
			for (String column : getColumns()) {
				query += column + ", ";
			}
			query = query.substring(0, query.length() - 2);
			query += ") VALUES ('";

			for (String value : getValues()) {
				query += value + "', '";
			}
			query = query.substring(0, query.length() - 3);
			query += "); ";

			System.out.println(query);
			stmt.executeUpdate(query);
			return true;

		} catch (Exception e) {
			System.out.println("Error: " + e);
			return false;
		}

	}

}
