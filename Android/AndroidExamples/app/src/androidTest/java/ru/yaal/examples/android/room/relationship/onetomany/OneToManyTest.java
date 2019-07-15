package ru.yaal.examples.android.room.relationship.onetomany;

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
public class OneToManyTest {
    private OneToManyUserDao userDao;
    private OneToManyBookDao bookDao;
    private OneToManyDatabase db;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        db = Room.inMemoryDatabaseBuilder(context, OneToManyDatabase.class).build();
        userDao = db.userDao();
        bookDao = db.bookDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void writeUser() {
        OneToManyUser expUser = new OneToManyUser();
        expUser.uid = 1;
        expUser.firstName = "John";
        expUser.lastName = "Smith";
        userDao.insertAll(expUser);

        OneToManyUser actUser = userDao.findByName(expUser.firstName, expUser.lastName);
        assertThat(actUser, equalTo(expUser));
    }

    @Test
    public void writeBook() {
        OneToManyUser expUser = new OneToManyUser();
        expUser.uid = 2;
        expUser.firstName = "John";
        expUser.lastName = "Smith";
        userDao.insertAll(expUser);

        OneToManyUser actUser = userDao.findByName(expUser.firstName, expUser.lastName);
        assertThat(actUser, equalTo(expUser));

        OneToManyBook expBook = new OneToManyBook();
        expBook.bookId = 1;
        expBook.title = "Android Cookbook";
        expBook.userId = expUser.uid;

        bookDao.insertAll(expBook);

        OneToManyBook actBook = bookDao.findByTitle(expBook.title);
        assertThat(actBook, equalTo(expBook));
    }
}
