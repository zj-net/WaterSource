package com.tristenallen.watersource;

import com.tristenallen.watersource.database.MyDatabase;
import com.tristenallen.watersource.model.AuthHelper;
import com.tristenallen.watersource.model.AuthLevel;
import com.tristenallen.watersource.model.AuthStatus;
import com.tristenallen.watersource.model.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by David (Zhang Xiong) on 4/5/17.
 *
 * This is to test login method in AuthHelper
 * The side effect of setting current user in Model is not tested, because it is static.
 */

public class LoginTest {
    private AuthHelper authHelper;
    private MyDatabase data;
    private final User user = new User("1@1.com", AuthLevel.ADMIN,"Xiong","David");

    @Before
    public void setUp() {
        data = mock(MyDatabase.class);
        when(data.checkEmail("2@2.com")).thenReturn(false);
        when(data.checkEmail("1@1.com")).thenReturn(true);
        when(data.getIDbyEmail("1@1.com")).thenReturn(1);
        when(data.validate("1@1.com","1")).thenReturn(true);
        when(data.validate("1@1.com","2")).thenReturn(false);
        when(data.getUserbyID(1)).thenReturn(user);

        authHelper = new AuthHelper();
    }

    @Test
    // invalid email
    public void InvalidEmail() {
        assertEquals(authHelper.login("2@2.com","2",data).getUser(), null);
        assertEquals(authHelper.login("2@2.com","2",data).getStatus(), AuthStatus.INVALID_NAME);
    }

    @Test
    // valid email, correct passowrd
    public void ValidEmailValidPassword() {
        assertEquals(authHelper.login("1@1.com","1",data).getUser(), user);
        assertEquals(authHelper.login("1@1.com","1",data).getStatus(), AuthStatus.VALID_LOGIN);
    }

    @Test
    // valid email, but wrong password
    public void ValidEmailInvalidPassword() {
        assertEquals(authHelper.login("1@1.com","2",data).getUser(), user);
        assertEquals(authHelper.login("1@1.com","2",data).getStatus(), AuthStatus.INVALID_PASSWORD);
    }

}