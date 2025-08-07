package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import logic.API;
import main.ATN;
import model.Jobs;
import model.Ticket;
import model.UnidadConductor;
import model.cliente;
import util.DateTimeUtil;
import util.POSTWS;

public class DataConnectionMYSQL {

	private static Connection con;

//	private static String IP = "10.1.0.10";
//	private static String USER = "webresource2";
//	private static String PASSWORD = "PIrIxCPp6#ss";
//	private static String DATABASE = "dbAsignacionConductor";
//	private static String PORT = "3306";

	private static String IP = "40.121.205.224";
	private static String USER = "u_cymex";
	private static String PASSWORD = "kLLoTAA0PHZCk2wlma0G#";
	private static String DATABASE = "dbAsignacionConductor";
	private static String PORT = "3306";

	public static Connection Connection() {
		con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE + "?autoReconnect=false", USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	



}
