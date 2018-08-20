package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import database.TableSchema.Column;


public class TableData 
{
	private DbAccess db;

	/**
	 * Inizializzazione
	 * @param db specifica un databsae
	 */
	public TableData(DbAccess db) {
		this.db = db;
	}

	/**
	 * Restituisce una lista di Tuple (o esempi) presenti nella tabella specificata come parametro
	 * @param table nome della tabella da cui ricavare gli esempi
	 * @return lista di tuple (o esempi)
	 * @throws SQLException
	 */
	public List<Example> getTransazioni(String table) throws SQLException{
		LinkedList<Example> transSet = new LinkedList<Example>();
		Statement statement;
		//ricavo la struttura della tabella desiderata
		TableSchema tSchema=new TableSchema(db,table);


		String query="select ";
		//per ogni attributo (colonna) della tabella, aggiungo il relativo nome alla query
		for(int i=0;i<tSchema.getNumberOfAttributes();i++){
			Column c=tSchema.getColumn(i);
			if(i>0)
				query+=",";
			query += c.getColumnName();
		}
		if(tSchema.getNumberOfAttributes()==0)
			throw new SQLException("Tabella vuota o non presente del database");
		query += (" FROM "+table);
		//accedo al db
		statement = db.getConnection().createStatement();
		//eseguo la query, e tramite il ResultSet (quale funge quasi da iteratore) scandisco i risultati ricavati dalla query
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			//definisco la variabile quale definirà una tupla, ottenuta come risultato della query
			Example currentTuple=new Example();
			//per ogni colonna, verifico se sia di tipo Stringa o numerica, così da ricavare il valore in esso definito
			for(int i=0;i<tSchema.getNumberOfAttributes();i++)
				if(tSchema.getColumn(i).isNumber())
					currentTuple.add(rs.getDouble(i+1));
				else
					currentTuple.add(rs.getString(i+1));
			//dopo aver ricavato i dati della tupla, quest'ultima viene depositata in una LinkedList, quale sarà l'output del metodo
			transSet.add(currentTuple);
		}
		rs.close();
		statement.close();



		return transSet;

	}

	/**
	 * Formula ed esegue una interrogazione SQL per estrarre il valore aggregato (valore minimo o valore massimo) 
	 * cercato nella colonna di nome column della tabella di nome table
	 * @param table nome della tabella da analizzare
	 * @param column nome della colonna 
	 * @param aggregate tipo di aggregazione(min / max)
	 * @return valore aggregato
	 * @throws SQLException
	 * @throws NoValueException
	 */
	public  Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate) throws SQLException,NoValueException{
		Statement statement;
		Object value=null;
		String aggregateOp="";

		String query="select ";
		if(aggregate==QUERY_TYPE.MAX)
			aggregateOp+="max";
		else
			aggregateOp+="min";
		query+=aggregateOp+"("+column.getColumnName()+ ") FROM "+table;


		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		if (rs.next()) {
			if(column.isNumber())
				value=rs.getDouble(1);
			else
				value=rs.getString(1);

		}
		rs.close();
		statement.close();
		if(value==null)
			throw new NoValueException("No " + aggregateOp+ " on "+ column.getColumnName());

		return value;

	}

	/**
	 * Ottiene un insieme di oggetti rappresentanti le colonne
	 * @param table tabella la quale estrarre le informazioni
	 * @param column colonna da specificare
	 * @return insieme di oggetti
	 * @throws SQLException se la connessione col database fallisce
	 */
	public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException {
		Set<Object> valueSet = new TreeSet<Object>();
		Statement statement;
		//TableSchema tSchema = new TableSchema(db, table);


		String query = "select distinct ";

		query += column.getColumnName();

		query += (" FROM " + table);

		query += (" ORDER BY " + column.getColumnName());


		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			if (column.isNumber())
				valueSet.add(rs.getDouble(1));
			else
				valueSet.add(rs.getString(1));

		}
		rs.close();
		statement.close();

		return valueSet;
	}
}
