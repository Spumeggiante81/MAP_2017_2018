package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import database.DatabaseConnectionException;

/**
 * Si occupa della gestione della comunicazione tra il server ed il database
 */
public class DbAccess {
    private final String DBMS = "jdbc:mysql";
    private final String SERVER = "localhost"; //Contiene l'identificativo del server su cui risiede la base di dati
    private final String DATABASE = "MapDB"; //Contiene il nome della base di dati
    private final int PORT = 3306; //Indica la porta su cui il DBMS MySQL accetta le connessioni 
    private final String USER_ID = "MapUser"; //Contiene il nome dell'utente per l'accesso alla base di dati
    private final String PASSWORD = "map"; //contiene la psw di autenticazione pe l'utente identificato da USER_ID
    private String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
    private Connection conn; //Gestisce una connessione
    private Statement stat;

    /**
     * Carica ed inizializza la connessione
     * @throws DatabaseConnectionException in caso di fallimento nella connessione al server
     */
    public void initConnection() throws DatabaseConnectionException {
        try {
            //Class.forName(DRIVER_CLASS_NAME);
            String url = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE;
            conn = DriverManager.getConnection(url, USER_ID, PASSWORD);
            stat = conn.createStatement();
        } /*catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/ catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException(e.getMessage());
        }

    }

    public Connection getConnection() 
    {
        return conn;
    }
    
    public Statement getStatement ()
    {
    	return stat;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
