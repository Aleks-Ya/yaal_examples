package ru.yaal.examples.android.room;

import android.content.Context;

import androidx.room.Room;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4ClassRunner.class)
public class AppDatabaseTest {
    private UserDao userDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userDao = db.userDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() {
        User expUser = new User();
        expUser.uid = 1;
        expUser.firstName = "John";
        expUser.lastName = "Smith";
        userDao.insertAll(expUser);

        User actUser = userDao.findByName(expUser.firstName, expUser.lastName);
        assertThat(actUser, equalTo(expUser));
    }
}
