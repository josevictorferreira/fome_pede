package com.example.josevictor.fomepede.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.josevictor.fomepede.Model.Restaurante;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


/**
 * Created by josevictor on 15/11/17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "restaurantes.db";
    private static final int DB_VERSION = 1;

    private static DatabaseHelper instance;
    private Dao<Restaurante, Integer> pessoaDao;

    public static DatabaseHelper getInstance(Context contexto) {
        if (instance == null) {
            instance = new DatabaseHelper(contexto);
        }
        return instance;
    }

    private DatabaseHelper(Context contexto) {
        super(contexto, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Restaurante.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "onCreate", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {

            TableUtils.dropTable(connectionSource, Restaurante.class, true);

            onCreate(sqLiteDatabase, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "onUpgrade", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<Restaurante, Integer> getRestauranteDao() throws SQLException {

        if (pessoaDao == null) {
            pessoaDao = getDao(Restaurante.class);
        }

        return pessoaDao;
    }
}
