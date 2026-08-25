package ru.yaal.examples.android.room.datatypes;

import android.content.Context;

import androidx.room.Room;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4ClassRunner.class)
public class DataTypesDatabaseTest {
    private DataTypesUserDao userDao;
    private DataTypesDatabase db;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        db = Room.inMemoryDatabaseBuilder(context, DataTypesDatabase.class).build();
        userDao = db.userDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() {
        DataTypesUser expUser = new DataTypesUser();
        expUser.id = 1;
        expUser.stringField = "John";
        expUser.intField = 33;
        expUser.dateField = new Date();
        expUser.localDateTimeField = LocalDateTime.now();
        userDao.insertAll(expUser);

        DataTypesUser actUser = userDao.loadById(expUser.id);
        assertThat(actUser, equalTo(expUser));
    }
}
