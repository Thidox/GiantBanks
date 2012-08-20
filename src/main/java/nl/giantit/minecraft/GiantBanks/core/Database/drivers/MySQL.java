package nl.giantit.minecraft.GiantBanks.core.Database.drivers;

import nl.giantit.minecraft.GiantBanks.GiantBanks;
import nl.giantit.minecraft.GiantBanks.core.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author Giant
 */
public class MySQL implements iDriver {
	
	private static HashMap<String, MySQL> instance = new HashMap<String, MySQL>();
	private GiantBanks plugin;
	
	private ArrayList<HashMap<String, String>> sql = new ArrayList<HashMap<String, String>>();
	private ArrayList<ResultSet> query = new ArrayList<ResultSet>();
	private int execs = 0;
	
	private String cur, db, host, port, user, pass, prefix;
	private Connection con = null;
	private config conf = null;
	
	private void connect() {
		String dbPath = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.db + "?user=" + this.user + "&password=" + this.pass;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			this.con = DriverManager.getConnection(dbPath);
		}catch(SQLException e) {
			GiantBanks.log.log(Level.SEVERE, "[" + this.plugin.getName() + "] Failed to connect to database: SQL error!");
			if(conf.getBoolean(plugin.getName() + ".global.debug")) {
				GiantBanks.log.log(Level.INFO, e.getMessage());
			}
		}catch(ClassNotFoundException e) {
			GiantBanks.log.log(Level.SEVERE, "[" + this.plugin.getName() + "] Failed to connect to database: MySQL library not found!");
			if(conf.getBoolean(plugin.getName() + ".global.debug")) {
				GiantBanks.log.log(Level.INFO, e.getMessage());
			}
		}
	}
	
	private MySQL(HashMap<String, String> c) {
		this.plugin = GiantBanks.getPlugin();
		
		this.conf = config.Obtain();
		
		this.db = c.get("database");
		this.host = c.get("host");
		this.port = String.valueOf(c.get("port"));
		this.user = c.get("user");
		this.pass = c.get("password");
		this.prefix = c.get("prefix");
		this.connect();
	}
	
	@Override
	public void close() {
		try {
			if(!con.isClosed() || !con.isValid(0))
				return;
			
			this.con.close();
		}catch(SQLException e) {
			//ignore
		}
	}
	
	@Override
	public boolean tableExists(String table) {
		ResultSet res = null;
		table = table.replace("#__", prefix);
		try {
			DatabaseMetaData data = this.con.getMetaData();
			res = data.getTables(null, null, table, null);

			return res.next();
		}catch (NullPointerException e) {
			GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "]: Could not load table " + table);
			if(conf.getBoolean(plugin.getName() + ".global.debug")) {
				GiantBanks.log.log(Level.INFO, e.getMessage());
			}
            return false;
		}catch (SQLException e) {
			GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "]: Could not load table " + table);
			if(conf.getBoolean(plugin.getName() + ".global.debug")) {
				GiantBanks.log.log(Level.INFO, e.getMessage());
			}
            return false;
		} finally {
			try {
				if(res != null) {
					res.close();
				}
			}catch (Exception e) {
				GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "]: Could not close result connection to database");
				if(conf.getBoolean(plugin.getName() + ".global.debug")) {
					GiantBanks.log.log(Level.INFO, e.getMessage());
				}
				return false;
			}
		}
	}
	
	@Override
	public void buildQuery(String string) {
		buildQuery(string, false, false, false);
		return;
	}
	
	@Override
	public void buildQuery(String string, Boolean add) {
		buildQuery(string, add, false, false);
		return;
	}
	
	@Override
	public void buildQuery(String string, Boolean add, Boolean finalize) {
		buildQuery(string, add, finalize, false);
		return;
	}
	
	
	@Override
	public void buildQuery(String string, Boolean add, Boolean finalize, Boolean debug) {
		buildQuery(string, add, finalize, debug, false);
	}
	
	@Override
	public void buildQuery(String string, Boolean add, Boolean finalize, Boolean debug, Boolean table) {
		int last = sql.size();
		if(table)
			string = string.replace("#__", prefix);
		
		if(false == add) {
			HashMap<String, String> ad = new HashMap<String, String>();
			ad.put("sql", string);
			sql.add(ad);
			
			if(debug == true)
				GiantBanks.log.log(Level.INFO, "[" + plugin.getName() + "] " + sql.get(last).get("sql"));
		}else{
			last = sql.size() - 1;
			try {
				HashMap<String, String> SQL = sql.get(last);
				if(SQL.containsKey("sql")) {
					if(SQL.containsKey("finalize")) {
						if(true == debug)
							GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] SQL syntax is finalized!");
						return;
					}else{
						SQL.put("sql", SQL.get("sql") + string);

						if(true == finalize)
							SQL.put("finalize", "true");

						sql.add(last, SQL);
					}
				}else
					if(true == debug)
						GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] " + last + " is not a valid SQL query!");
				
				if(debug == true)
					GiantBanks.log.log(Level.INFO, "[" + plugin.getName() + "] " + sql.get(last).get("sql"));
			}catch(NullPointerException e) {
				if(true == debug)
					GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] " + last + " is not a valid SQL query!");
			}
		}
			
		return;
	}
	
	@Override
	public void buildQuery(String string, Integer add) {
		buildQuery(string, add, false, false);
		return;
	}
	
	@Override
	public void buildQuery(String string, Integer add, Boolean finalize) {
		buildQuery(string, add, finalize, false);
		return;
	}
	
	@Override
	public void buildQuery(String string, Integer add, Boolean finalize, Boolean debug) {
		int last = sql.size();
		string = string.replace("#__", prefix);
		
		try {
			HashMap<String, String> SQL = sql.get(add);
			if(SQL.containsKey("sql")) {
				if(SQL.containsKey("finalize")) {
					if(true == debug)
						GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] SQL syntax is finalized!");
					return;
				}else{
					SQL.put("sql", SQL.get("sql") + string);
					
					if(true == finalize)
						SQL.put("finalize", "true");

					sql.add(add, SQL);
				}
			}else
				if(true == debug)
					GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] " + add.toString() + " is not a valid SQL query!");
		
			if(debug == true)
				GiantBanks.log.log(Level.INFO, "[" + plugin.getName() + "] " + sql.get(last).get("sql"));
		}catch(NullPointerException e) {
			if(true == debug)
				GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] " + add.toString() + " is not a valid SQL query!");
		}
		
		return;
	}
	
	@Override
	public ArrayList<HashMap<String, String>> execQuery() {
		Integer queryID = ((sql.size() - 1 > 0) ? (sql.size() - 1) : 0);
		/*Statement st = null;
		
		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		try {
			HashMap<String, String> SQL = sql.get(queryID);
			if(SQL.containsKey("sql")) {
				try {
					st = con.createStatement();
					//query.add(queryID, st.executeQuery(SQL.get("sql")));
					ResultSet res = st.executeQuery(SQL.get("sql"));
					while(res.next()) {
						HashMap<String, String> row = new HashMap<String, String>();

						ResultSetMetaData rsmd = res.getMetaData();
						int columns = rsmd.getColumnCount();
						for(int i = 1; i < columns + 1; i++) {
							row.put(rsmd.getColumnName(i), res.getString(i));
						}
						data.add(row);
					}
				}catch (SQLException e) {
					GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not execute query!");
					if(conf.getBoolean(plugin.getName() + ".global.debug")) {
						GiantBanks.log.log(Level.INFO, e.getMessage());
					}
				} finally {
					try {
						if(st != null) {
							st.close();
						}
					}catch (Exception e) {
						GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not close database connection");
						if(conf.getBoolean(plugin.getName() + ".global.debug")) {
							GiantBanks.log.log(Level.INFO, e.getMessage());
						}
					}
				}
			}
		}catch(NullPointerException e) {
			GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] " + queryID.toString() + " is not a valid SQL query!");
			if(conf.getBoolean(plugin.getName() + ".global.debug")) {
				GiantBanks.log.log(Level.INFO, e.getMessage());
			}
		}*/
		
		return this.execQuery(queryID);
	}
	
	@Override
	public ArrayList<HashMap<String, String>> execQuery(Integer queryID) {
		Statement st = null;
		
		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		try {
			HashMap<String, String> SQL = sql.get(queryID);
			if(SQL.containsKey("sql")) {
				try {
					if(con.isClosed() || !con.isValid(0))
						this.connect();
					
					st = con.createStatement();
					//query.add(queryID, st.executeQuery(SQL.get("sql")));
					ResultSet res = st.executeQuery(SQL.get("sql"));
					while(res.next()) {
						HashMap<String, String> row = new HashMap<String, String>();

						ResultSetMetaData rsmd = res.getMetaData();
						int columns = rsmd.getColumnCount();
						for(int i = 1; i < columns + 1; i++) {
							row.put(rsmd.getColumnName(i), res.getString(i));
						}
						data.add(row);
					}
				}catch (SQLException e) {
					GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not execute query!");
					if(conf.getBoolean(plugin.getName() + ".global.debug")) {
						GiantBanks.log.log(Level.INFO, e.getMessage());
					}
				} finally {
					try {
						if(st != null) {
							st.close();
						}
					}catch (Exception e) {
						GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not close database connection");
						if(conf.getBoolean(plugin.getName() + ".global.debug")) {
							GiantBanks.log.log(Level.INFO, e.getMessage());
						}
					}
				}
			}
		}catch(NullPointerException e) {
			GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] " + queryID.toString() + " is not a valid SQL query!");
		}
		
		return data;
	}
	
	@Override
	public void updateQuery() {
		Integer queryID = ((sql.size() - 1 > 0) ? (sql.size() - 1) : 0);
		/*Statement st = null;
		
		try {
			HashMap<String, String> SQL = sql.get(queryID);
			if(SQL.containsKey("sql")) {
				try {
					st = con.createStatement();
					st.executeUpdate(SQL.get("sql"));
				}catch (SQLException e) {
					GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not execute query!");
					if(conf.getBoolean(plugin.getName() + ".global.debug")) {
						GiantBanks.log.log(Level.INFO, e.getMessage());
					}
				} finally {
					try {
						if(st != null) {
							st.close();
						}
					}catch (Exception e) {
						GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not close database connection");
						if(conf.getBoolean(plugin.getName() + ".global.debug")) {
							GiantBanks.log.log(Level.INFO, e.getMessage());
						}
					}
				}
			}
		}catch(NullPointerException e) {
			GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] " + queryID.toString() + " is not a valid SQL query!");
		}*/
		this.updateQuery(queryID);
	}
	
	@Override
	public void updateQuery(Integer queryID) {
		Statement st = null;
		
		try {
			HashMap<String, String> SQL = sql.get(queryID);
			if(SQL.containsKey("sql")) {
				try {
					st = con.createStatement();
					st.executeUpdate(SQL.get("sql"));
				}catch (SQLException e) {
					GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not execute query!");
					if(conf.getBoolean(plugin.getName() + ".global.debug")) {
						GiantBanks.log.log(Level.INFO, e.getMessage());
					}
				} finally {
					try {
						if(st != null) {
							st.close();
						}
					}catch (Exception e) {
						GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not close database connection");
						if(conf.getBoolean(plugin.getName() + ".global.debug")) {
							GiantBanks.log.log(Level.INFO, e.getMessage());
						}
					}
				}
			}
		}catch(NullPointerException e) {
			GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] " + queryID.toString() + " is not a valid SQL query!");
		}
	}
	
	@Override
	public int countResult() {
		Integer queryID = ((query.size() - 1 > 0) ? (query.size() - 1) : 0);
		
		/*if(query.get(queryID) == null)
			return 0;
		try {
			query.get(queryID).last();
			int row = query.get(queryID).getRow();
			query.get(queryID).first();
		
			return row;
		}catch (Exception e) {
			GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not count rows for query (" + queryID.toString() + ")!");
			if(conf.getBoolean(plugin.getName() + ".global.debug")) {
				GiantBanks.log.log(Level.INFO, e.getMessage());
			}
		}
		
		return 0;*/
		return this.countResult(queryID);
	}
	
	@Override
	public int countResult(Integer queryID) {
		if(query.get(queryID) == null)
			return 0;
		try {
			query.get(queryID).last();
			int row = query.get(queryID).getRow();
			query.get(queryID).first();
		
			return row;
		}catch (Exception e) {
			GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not count rows for query (" + queryID.toString() + ")!");
			if(conf.getBoolean(plugin.getName() + ".global.debug")) {
				GiantBanks.log.log(Level.INFO, e.getMessage());
			}
		}
		
		return 0;
	}
	
	@Override
	public ArrayList<HashMap<String, String>> getResult() {
		Integer queryID = ((query.size() - 1 > 0) ? (query.size() - 1) : 0);
		
		return this.getResult(queryID);
		
		/*ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		try {
			ResultSet res = query.get(queryID);
			while(res.next()) {
				HashMap<String, String> row = new HashMap<String, String>();
				
				ResultSetMetaData rsmd = res.getMetaData();
				int columns = rsmd.getColumnCount();
				for(int i = 0; i < columns; i++) {
					row.put(rsmd.getColumnName(i), res.getString(i));
				}
				data.add(row);
			}
		}catch (SQLException e) {
			GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not grab item data");
			if(conf.getBoolean(plugin.getName() + ".global.debug")) {
				GiantBanks.log.log(Level.INFO, e.getMessage());
			}
		} finally {
			try {
				if(query.get(queryID) != null) {
					query.get(queryID).close();
				}
			}catch (Exception e) {
				GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not close database connection");
				if(conf.getBoolean(plugin.getName() + ".global.debug")) {
					GiantBanks.log.log(Level.INFO, e.getMessage());
				}
			}
		}
		
		return data;*/
	}
	
	@Override
	public ArrayList<HashMap<String, String>> getResult(Integer queryID) {
		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		try {
			ResultSet res = query.get(queryID);
			res.getRow();
		}catch (SQLException e) {
			GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not grab item data");
			if(conf.getBoolean(plugin.getName() + ".global.debug")) {
				GiantBanks.log.log(Level.INFO, e.getMessage());
			}
		} finally {
			try {
				if(query.get(queryID) != null) {
					query.get(queryID).close();
				}
			}catch (Exception e) {
				GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not close database connection");
				if(conf.getBoolean(plugin.getName() + ".global.debug")) {
					GiantBanks.log.log(Level.INFO, e.getMessage());
				}
			}
		}
		
		return data;
	}
	
	@Override
	public HashMap<String, String> getSingleResult() {
		Integer queryID = ((query.size() - 1 > 0) ? (query.size() - 1) : 0);
		return this.getSingleResult(queryID);
		
		/*HashMap<String, String> data = new HashMap<String, String>();
		try {
			ResultSet res = query.get(queryID);
			res.last();
			while(res.next()) {
				ResultSetMetaData rsmd = res.getMetaData();
				int columns = rsmd.getColumnCount();
				for(int i = 0; i < columns; i++) {
					data.put(rsmd.getColumnName(i), res.getString(i));
				}
			}
		}catch (SQLException e) {
			GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not grab item data");
			if(conf.getBoolean(plugin.getName() + ".global.debug")) {
				GiantBanks.log.log(Level.INFO, e.getMessage());
			}
		} finally {
			try {
				if(query.get(queryID) != null) {
					query.get(queryID).close();
				}
			}catch (Exception e) {
				GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not close database connection");
				if(conf.getBoolean(plugin.getName() + ".global.debug")) {
					GiantBanks.log.log(Level.INFO, e.getMessage());
				}
			}
		}
		
		return data;*/
	}
	
	@Override
	public HashMap<String, String> getSingleResult(Integer queryID) {
		HashMap<String, String> data = new HashMap<String, String>();
		try {
			ResultSet res = query.get(queryID);
			res.last();
			while(res.next()) {
				ResultSetMetaData rsmd = res.getMetaData();
				int columns = rsmd.getColumnCount();
				for(int i = 0; i < columns; i++) {
					data.put(rsmd.getColumnName(i), res.getString(i));
				}
			}
		}catch (SQLException e) {
			GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not grab item data");
			if(conf.getBoolean(plugin.getName() + ".global.debug")) {
				GiantBanks.log.log(Level.INFO, e.getMessage());
			}
		} finally {
			try {
				if(query.get(queryID) != null) {
					query.get(queryID).close();
				}
			}catch (Exception e) {
				GiantBanks.log.log(Level.SEVERE, "[" + plugin.getName() + "] Could not close database connection");
				if(conf.getBoolean(plugin.getName() + ".global.debug")) {
					GiantBanks.log.log(Level.INFO, e.getMessage());
				}
			}
		}
		
		return data;
	}
	
	@Override
	public iDriver select(String field) {
		ArrayList<String> fields = new ArrayList<String>();
		fields.add(field);
		return this.select(fields);
	}
	
	@Override
	public iDriver select(String... fields) {
		ArrayList<String> f = new ArrayList<String>();
		if(fields.length > 0) {
			for(String field : fields) { 
				f.add(field);
			}
		}

		return this.select(f);
	}
	
	@Override
	public iDriver select(ArrayList<String> fields) {
		if(fields.size() > 0) {
			String SQL = "SELECT ";
			int i = 0;
			for(String field : fields) {
				if(i > 0)
					SQL += ", ";
				
				SQL += field;
				i++;
			}
			
			this.buildQuery(SQL + " \n", false, false, false);
		}
		
		return this;
	}
	
	@Override
	public iDriver select(HashMap<String, String> fields) {
		if(fields.size() > 0) {
			String SQL = "SELECT ";
			int i = 0;
			for(Map.Entry<String, String> field : fields.entrySet()) {
				if(i > 0)
					SQL += ", ";
				
				SQL += field.getKey() + " AS " + field.getValue();
				i++;
			}
			
			this.buildQuery(SQL + " \n", false, false, false);
		}
		
		return this;
	}
	
	@Override
	public iDriver from(String table) {
		table = table.replace("#__", prefix);
		this.buildQuery("FROM " + table + " \n", true, false, false);
		
		return this;
	}
	
	@Override
	public iDriver where(HashMap<String, String> fields) {
		if(fields.size() > 0) {
			String SQL = "WHERE ";
			int i = 0;
			
			for(Map.Entry<String, String> field : fields.entrySet()) {
				if(i > 0)
					SQL += " AND ";
				
				SQL += field.getKey() + "='" + field.getValue() + "'";
				i++;
			}
			
			this.buildQuery(SQL + " \n", true, false, false);
		}
		
		return this;
	}
	
	@Override
	public iDriver where(HashMap<String, HashMap<String, String>> fields, Boolean shite) {
		if(fields.size() > 0) {
			String SQL = "WHERE ";
			int i = 0;
			
			for(Map.Entry<String, HashMap<String, String>> field : fields.entrySet()) {
				String type = (field.getValue().containsKey("type") && field.getValue().get("type").equalsIgnoreCase("OR")) ? "OR" : "AND";
				if(i > 0)
					SQL += " " + type + " ";
				
				if(field.getValue().containsKey("kind") && field.getValue().get("kind").equals("int")) {
					SQL += field.getKey() + "=" + field.getValue().get("data");
				}else if(field.getValue().containsKey("kind") && field.getValue().get("kind").equalsIgnoreCase("NULL")) {
					SQL += field.getKey() + " IS NULL";
				}else if(field.getValue().containsKey("kind") && field.getValue().get("kind").equalsIgnoreCase("NOTNULL")) {
					SQL += field.getKey() + " IS NOT NULL";
				}else if(field.getValue().containsKey("kind") && field.getValue().get("kind").equalsIgnoreCase("NOT")) {
					SQL += field.getKey() + "!='" + field.getValue().get("data")+"'";
				}else
					SQL += field.getKey() + "='" + field.getValue().get("data")+"'";
				
				i++;
			}
			
			this.buildQuery(SQL + " \n", true, false, false);
		}
		
		return this;
	}
	
	@Override
	public iDriver orderBy(HashMap<String, String> fields) {
		if(fields.size() > 0) {
			String SQL = "ORDER BY ";
			int i = 0;
			
			for(Map.Entry<String, String> field : fields.entrySet()) {
				if(i > 0)
					SQL += ", ";
				
				SQL += field.getKey() + " " + ((field.getValue().equalsIgnoreCase("ASC")) ? "ASC" : "DESC");
				i++;
			}
			
			this.buildQuery(SQL + " \n", true, false, false);
		}
		
		return this;
	}
	
	@Override
	public iDriver limit(int limit) {
		return this.limit(limit, null);
	}
	
	@Override
	public iDriver limit(int limit, Integer start) {
		this.buildQuery("LIMIT " + ((start != null) ? start + ", " + limit : limit) + " \n", true, false, false);
		return this;
	}
	
	@Override
	public iDriver insert(String table, ArrayList<String> fields, HashMap<Integer, HashMap<String, String>> values) {
		ArrayList<HashMap<Integer, HashMap<String, String>>> t = new ArrayList<HashMap<Integer, HashMap<String, String>>>();
		t.add(values);
		this.insert(table, fields, t);
		
		return this;
	}
	
	@Override
	public iDriver insert(String table, ArrayList<String> fields, ArrayList<HashMap<Integer, HashMap<String, String>>> values) {
		table = table.replace("#__", prefix);
		this.buildQuery("INSERT INTO " + table + " \n", false, false, false);
		
		if(fields.size() > 0) {
			String insFields = "(";
			int i = 0;
			for(String field : fields) {
				if(i > 0)
					insFields += ", ";
				
				insFields += field;
				i++;
			}
			
			insFields += ") \n";
			this.buildQuery(insFields, true, false, false);
		}
		
		this.buildQuery(" VALUES \n", true, false, false);
		String insValues = "";
		int i = 0;
		for(HashMap<Integer, HashMap<String, String>> value : values) {
			insValues += "(";
			int a = 0;
			for(Map.Entry<Integer, HashMap<String, String>> val : value.entrySet()) {
				if(a > 0)
					insValues += ", ";
				
				a++;
				if(val.getValue().containsKey("kind") && val.getValue().get("kind").equalsIgnoreCase("INT")) {
					insValues += val.getValue().get("data");
				}else
					insValues += "'" + val.getValue().get("data") + "'";
			}
			
			i++;
			insValues += (i < values.size()) ? "), \n" : ");";
		}
		this.buildQuery(insValues, true, false, false);
		
		return this;
	}
	
	@Override
	public iDriver update(String table) {
		table = table.replace("#__", prefix);
		this.buildQuery("UPDATE " + table + " \n", false, false, false);
		
		return this;
	}
	
	@Override
	public iDriver set(HashMap<String, String> fields) {
		if(fields.size() > 0) {
			String SQL = "SET ";
			int i = 0;
			
			for(Map.Entry<String, String> field : fields.entrySet()) {
				if(i > 0)
					SQL += ", ";
				
				SQL += field.getKey() + "='" + field.getValue() + "'";
				i++;
			}
			
			this.buildQuery(SQL + " \n", true, false, false);
		}
		
		return this;
	}
	
	@Override
	public iDriver set(HashMap<String, HashMap<String, String>> fields, Boolean shite) {
		if(fields.size() > 0) {
			String SQL = "SET ";
			int i = 0;
			
			for(Map.Entry<String, HashMap<String, String>> field : fields.entrySet()) {
				if(i > 0)
					SQL += ", ";
				
				if(field.getValue().containsKey("kind") && field.getValue().get("kind").equalsIgnoreCase("INT")) {
					SQL += field.getKey() + "=" + field.getValue().get("data");
				}else
					SQL += field.getKey() + "='" + field.getValue().get("data") + "'";
				
				i++;
			}
			
			this.buildQuery(SQL + " \n", true, false, false);
		}
		
		return this;
	}
	
	@Override
	public iDriver delete(String table) {
		table = table.replace("#__", prefix);
		this.buildQuery("DELETE FROM " + table + " \n", false, false, false);
		
		return this;
	}
	
	@Override
	public iDriver Truncate(String table) {
		table = table.replace("#__", prefix);
		this.buildQuery("TRUNCATE TABLE " + table + ";", false, false, false);
		
		return this;
	}

	@Override
	public iDriver create(String table) {
		table = table.replace("#__", prefix);
		this.buildQuery("CREATE TABLE " + table + "\n", false, false, false);
		
		return this;
	}
	
	@Override
	public iDriver fields(HashMap<String, HashMap<String, String>> fields) {
		String P_KEY = "";
		this.buildQuery("(", true, false, false);
		
		int i = 0;
		for(Map.Entry<String, HashMap<String, String>> entry : fields.entrySet()) {
			i++;
			HashMap<String, String> data = entry.getValue();
			
			String field = entry.getKey();
			String type = "VARCHAR";
			Integer length = 100;
			Boolean NULL = false;
			String def = "";
			Boolean aincr = false;
			
			if(data.containsKey("TYPE")) {
				type = data.get("TYPE");
			}
			
			if(data.containsKey("LENGTH")) {
				if(null != data.get("LENGTH")) {
					try{
						length = Integer.parseInt(data.get("LENGTH"));
						length = length < 0 ? 100 : length;
					}catch(NumberFormatException e) {}
				}else
					length = null;
			}
			
			if(data.containsKey("NULL")) {
				NULL = Boolean.parseBoolean(data.get("NULL"));
			}
			
			if(data.containsKey("DEFAULT")) {
				def = data.get("DEFAULT");
			}
			
			if(data.containsKey("A_INCR")) {
				aincr = Boolean.parseBoolean(data.get("A_INCR"));
			}
			
			if(data.containsKey("P_KEY")) {
				if(Boolean.parseBoolean(data.get("P_KEY"))) {
					P_KEY = field;
				}
			}
			
			if(length != null)
				type += "(" + length + ")";
			
			String n = (!NULL) ? " NOT NULL" : " DEFAULT NULL";
			String d = (!def.equalsIgnoreCase("")) ? " DEFAULT " + def : ""; 
			String a = (aincr) ? " AUTO_INCREMENT" : "";
			String c = (i < fields.size()) ? ",\n" : ""; 
			
			this.buildQuery(field + " " + type + n + d + a + c, true);
		}
		
		if(!P_KEY.equalsIgnoreCase(""))
			this.buildQuery("\n, PRIMARY KEY(" + P_KEY + ")", true, false, false);
		
		this.buildQuery(") ENGINE=InnoDB DEFAULT CHARSET=latin1;", true, false, false);
		
		return this;
	}

	@Override
	public iDriver alter(String table) {
		table = table.replace("#__", prefix);
		this.buildQuery("ALTER TABLE " + table + "\n", false, false, false);
		
		return this;
	}
	
	@Override
	public iDriver add(HashMap<String, HashMap<String, String>> fields) {
		int i = 0;
		for(Map.Entry<String, HashMap<String, String>> entry : fields.entrySet()) {
			i++;
			HashMap<String, String> data = entry.getValue();
			
			String field = entry.getKey();
			String type = "VARCHAR";
			Integer length = 100;
			Boolean NULL = false;
			String def = "";
			
			if(data.containsKey("TYPE")) {
				type = data.get("TYPE");
			}
			
			if(data.containsKey("LENGTH")) {
				if(null != data.get("LENGTH")) {
					try{
						length = Integer.parseInt(data.get("LENGTH"));
						length = length < 0 ? 100 : length;
					}catch(NumberFormatException e) {}
				}else
					length = null;
			}
			
			if(data.containsKey("NULL")) {
				NULL = Boolean.parseBoolean(data.get("NULL"));
			}
			
			if(data.containsKey("DEFAULT")) {
				def = data.get("DEFAULT");
			}
			
			if(length != null)
				type += "(" + length + ")";
			
			String n = (!NULL) ? " NOT NULL" : " DEFAULT NULL";
			String d = (!def.equalsIgnoreCase("")) ? " DEFAULT " + def : "";
			String c = (i < fields.size()) ? ",\n" : ""; 
			
			this.buildQuery("ADD " + field + " " + type + n + d + c, true);
		}
		
		return this;
	}
	
	@Override
	public iDriver debug(Boolean dbg) {
		this.buildQuery("", true, false, dbg);
		return this;
	}
	
	@Override
	public iDriver Finalize() {
		this.buildQuery("", true, true, false);
		return this;
	}
	
	@Override
	public iDriver debugFinalize(Boolean dbg) {
		this.buildQuery("", true, true, dbg);
		return this;
	}
	
	public static MySQL Obtain(HashMap<String, String> conf, String instance) {
		if(!MySQL.instance.containsKey(instance))
			MySQL.instance.put(instance, new MySQL(conf));
		
		return MySQL.instance.get(instance);
	}
}