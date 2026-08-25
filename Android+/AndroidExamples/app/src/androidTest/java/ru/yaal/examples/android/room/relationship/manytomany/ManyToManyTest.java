package ru.yaal.examples.android.room.relationship.manytomany;

import android.content.Context;

import androidx.room.Room;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4ClassRunner.class)
public class ManyToManyTest {
    private ManyToManyUserDao userDao;
    private ManyToManyBookDao bookDao;
    private UserBookJoinDao userBookJoinDao;
    private ManyToManyDatabase db;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        db = Room.inMemoryDatabaseBuilder(context, ManyToManyDatabase.class).build();
        userDao = db.userDao();
        bookDao = db.bookDao();
        userBookJoinDao = db.userBookJoinDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void manyToMany() {
        ManyToManyUser expUser1 = new ManyToManyUser();
        expUser1.uid = 1;
        expUser1.firstName = "John";
        expUser1.lastName = "Smith";

        ManyToManyUser expUser2 = new ManyToManyUser();
        expUser2.uid = 2;
        expUser2.firstName = "Bill";
        expUser2.lastName = "Gates";

        userDao.insertAll(expUser1, expUser2);

        ManyToManyBook expBook1 = new ManyToManyBook();
        expBook1.uid = 1;
        expBook1.title = "Android Cookbook";

        ManyToManyBook expBook2 = new ManyToManyBook();
        expBook2.uid = 2;
        expBook2.title = "Android Reference";

        ManyToManyBook expBook3 = new ManyToManyBook();
        expBook3.uid = 3;
        expBook3.title = "Android Guideline";

        bookDao.insertAll(expBook1, expBook2, expBook3);


        UserBookJoin join1 = new UserBookJoin();
        join1.userId = expUser1.uid;
        join1.bookId = expBook1.uid;

        UserBookJoin join2 = new UserBookJoin();
        join2.userId = expUser1.uid;
        join2.bookId = expBook2.uid;

        UserBookJoin join3 = new UserBookJoin();
        join3.userId = expUser2.uid;
        join3.bookId = expBook1.uid;

        UserBookJoin join4 = new UserBookJoin();
        join4.userId = expUser2.uid;
        join4.bookId = expBook3.uid;

        userBookJoinDao.insertAll(join1, join2, join3, join4);


        List<ManyToManyBook> user1Books = userBookJoinDao.getBooksForUser(expUser1.uid);
        assertThat(user1Books, containsInAnyOrder(expBook1, expBook2));

        List<ManyToManyBook> user2Books = userBookJoinDao.getBooksForUser(expUser2.uid);
        assertThat(user2Books, containsInAnyOrder(expBook1, expBook3));

        List<ManyToManyUser> book1Users = userBookJoinDao.getUsersForBook(expBook1.uid);
        assertThat(book1Users, containsInAnyOrder(expUser1, expUser2));

        List<ManyToManyUser> book2Users = userBookJoinDao.getUsersForBook(expBook2.uid);
        assertThat(book2Users, containsInAnyOrder(expUser1));

        List<ManyToManyUser> book3Users = userBookJoinDao.getUsersForBook(expBook3.uid);
        assertThat(book3Users, containsInAnyOrder(expUser2));

    }
}
