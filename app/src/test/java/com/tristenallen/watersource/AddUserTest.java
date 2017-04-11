package com.tristenallen.watersource;

import com.tristenallen.watersource.database.MyDatabase;
import com.tristenallen.watersource.model.AuthLevel;
import com.tristenallen.watersource.model.User;
import com.tristenallen.watersource.model.UserHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//import static org.junit.Assert.*;

/**
 * Created by Nelson on 4/5/2017.
 */


public class AddUserTest {
    UserHelper userHelper;
    MyDatabase stuff;
    User user = new User("q@q.com", AuthLevel.MANAGER, "Taylor", "David");
    User badU = new User(null, AuthLevel.USER, null, null);

    @Before
    public void setUp() {
        stuff = mock(MyDatabase.class);
        when(stuff.checkEmail("r@r.com")).thenReturn(true);
        when(stuff.checkEmail("q@q.com")).thenReturn(false);
    }

    @Test
    /*
    test if addUser worked
    if there does not exist a user with the same email then YOU ADD USER
    */
    public void ValidAddUser() {
        assertEquals(userHelper.addUser(new User("r@r.com", AuthLevel.WORKER, "Guy", "man"),"q@q.com","sus",stuff), user);
    }

    @Test
    /*
    test if addUser failed
    if there is a user with the same email then YOU DO NOT ADD USER
    */
    public void InvalidAddUser() {
        assertEquals(userHelper.addUser(new User("q@q.com", AuthLevel.USER, "Dude", "Bro"),"q@q.com","sus",stuff), user);
    }
}
