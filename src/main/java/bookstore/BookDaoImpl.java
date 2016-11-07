package bookstore;

import dao.DAOException;
import dao.DAOFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ulysse on 06/11/2016.
 */
public class BookDaoImpl implements BookDao {


    private DAOFactory daoFactory;


    private static final String SQL_SELECT_BY_ID = "SELECT id, title, author, number_of_pages FROM books WHERE id = ?;";
    private static final String SQL_SELECT_ALL = "SELECT id, title, author, number_of_pages FROM books;";
    private static final String SQL_INSERT = "INSERT INTO books (title, author, number_of_pages) VALUES (?, ?, ?)";


    public BookDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public Book insert(Book book) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
        /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();

            preparedStatement = initRequest(
                    connexion,
                    SQL_INSERT,
                    true,
                    book.getTitle(),
                    book.getAuthor(),
                    book.getNumberOfPages()
            );

            int statut = preparedStatement.executeUpdate();

            /* Analyse du statut retourné par la requête d'insertion */
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
            }

            /* Récupération de l'id auto-généré par la requête d'insertion */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
                book.setId(valeursAutoGenerees.getInt(1));
            } else {
                throw new DAOException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }

        return book;
    }


    @Override
    public Book find(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Book book = null;
        try {
            /* Récupération d'une connexion depuis la Factory */
            connection = daoFactory.getConnection();
            preparedStatement = initRequest(connection, SQL_SELECT_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
            if (resultSet.next()) {
                book = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connection );
        }
        return book;
    }


    @Override
    public Collection<Book> find() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Book> books = new ArrayList<Book>();
        try {
            /* Récupération d'une connexion depuis la Factory */
            connection = daoFactory.getConnection();
            preparedStatement = initRequest(connection, SQL_SELECT_ALL, false);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(map(resultSet));
            }
            System.out.println(books);
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connection );
        }
        return books;
    }


    /*
     * Initialise la requête préparée basée sur la connexion passée en argument,
     * avec la requête SQL et les objets donnés.
     */
    public static PreparedStatement initRequest(Connection c, String sql, boolean returnGeneratedKeys, Object... objects )
            throws SQLException {
        PreparedStatement preparedStatement = c.prepareStatement(
                sql,
                returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS
        );
        for ( int i = 0; i < objects.length; i++ ) {
            preparedStatement.setObject( i + 1, objects[i] );
        }
        return preparedStatement;
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des utilisateurs (un
     * ResultSet) et un bean Utilisateur.
     */
    private static Book map( ResultSet resultSet ) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setNumberOfPages( resultSet.getInt("number_of_pages"));
        System.out.println(book);
        return book;
    }

    /* Fermeture silencieuse du resultset */
    public static void fermetureSilencieuse( ResultSet resultSet ) {
        if ( resultSet != null ) {
            try {
                resultSet.close();
            } catch ( SQLException e ) {
                System.out.println( "Échec de la fermeture du ResultSet : " + e.getMessage() );
            }
        }
    }

    /* Fermeture silencieuse du statement */
    public static void fermetureSilencieuse( Statement statement ) {
        if ( statement != null ) {
            try {
                statement.close();
            } catch ( SQLException e ) {
                System.out.println( "Échec de la fermeture du Statement : " + e.getMessage() );
            }
        }
    }

    /* Fermeture silencieuse de la connexion */
    public static void fermetureSilencieuse( Connection connexion ) {
        if ( connexion != null ) {
            try {
                connexion.close();
            } catch ( SQLException e ) {
                System.out.println( "Échec de la fermeture de la connexion : " + e.getMessage() );
            }
        }
    }

    /* Fermetures silencieuses du statement et de la connexion */
    public static void fermeturesSilencieuses( Statement statement, Connection connexion ) {
        fermetureSilencieuse(statement);
        fermetureSilencieuse(connexion);
    }

    /* Fermetures silencieuses du resultset, du statement et de la connexion */
    public static void fermeturesSilencieuses( ResultSet resultSet, Statement statement, Connection connexion ) {
        fermetureSilencieuse(resultSet);
        fermetureSilencieuse(statement);
        fermetureSilencieuse(connexion);
    }
}
