package dao;

import bookstore.BookDao;
import bookstore.BookDaoImpl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by ulysse on 06/11/2016.
 */
public class DAOFactory {

    private static final String FILE_PROPERTIES = "dao.properties";
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_USER = "user";
    private static final String PROPERTY_PASS = "pass";

    private String url;
    private String user;
    private String pass;

    DAOFactory(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public static DAOFactory getInstance() throws DAOConfigurationException {

        Properties properties = new Properties();

        String url;
        String driver;
        String user;
        String pass;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propFile = classLoader.getResourceAsStream(FILE_PROPERTIES);

        if (propFile == null) {
            throw new DAOConfigurationException("Le fichier des propriété est introuvable");
        }

        try {
            properties.load(propFile);
            url = properties.getProperty(PROPERTY_URL);
            driver = properties.getProperty(PROPERTY_DRIVER);
            user = properties.getProperty(PROPERTY_USER);
            pass = properties.getProperty(PROPERTY_PASS);
        } catch ( IOException e ) {
            throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FILE_PROPERTIES, e );
        }

        try {
            Class.forName(driver);
        } catch ( ClassNotFoundException e ) {
            throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
        }

        DAOFactory instance = new DAOFactory(url, user, pass);
        return instance;
    }

    /* Méthode chargée de fournir une connexion à la base de données */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection( url, user, pass);
    }


    public BookDao getBookDao() {
        return new BookDaoImpl(this);
    }
}
