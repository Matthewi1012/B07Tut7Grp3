package com.example.b07tut7grp3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.admintoolsUTSC.Admin;
import com.example.admintoolsUTSC.Admin_Login;
import com.example.admintoolsUTSC.admin_model;
import com.example.admintoolsUTSC.admin_presenter;
import com.example.b07tut7grp3.ui.login.Model;
import com.example.b07tut7grp3.ui.login.Presenter;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    MainActivity view;

    @Mock
    Model model;

    @Test
    public void testPresenterEmptyUser(){
        when(view.getUsername()).thenReturn("");
        when(model.isFound("")).thenReturn(false);
        Presenter presenter = new Presenter(model,view);
        presenter.checkUsername();
        verify(view,times(1)).displayMessage("user email cannot be empty");
    }
    @Test
    public void testPresenterValidUser(){
        when(view.getUsername()).thenReturn("sampleUserEmail114514");
        when(model.isFound("sampleUserEmail114514")).thenReturn(true);
        Presenter presenter = new Presenter(model,view);
        presenter.checkUsername();
        verify(view,times(1)).displayMessage("user email found");
    }
    @Test
    public void testPresenterNotAUser(){
        when(view.getUsername()).thenReturn("sampleUserEmail1919");
        when(model.isFound("sampleUserEmail1919")).thenReturn(false);
        Presenter presenter = new Presenter(model,view);
        presenter.checkUsername();
        verify(view).displayMessage("user email not found");
    }
    @Test
    public void testPresenterShortPassword(){
        when(view.getPassword()).thenReturn("12345678");
        when(model.goodPassword("12345678")).thenReturn(false);
        Presenter presenter = new Presenter(model,view);
        presenter.checkPassword();
        verify(view,times(1)).displayMessage("short password, bad password");
    }
    @Test
    public void testPresenterLongPassword(){
        when(view.getPassword()).thenReturn("123456789");
        when(model.goodPassword("123456789")).thenReturn(true);
        Presenter presenter = new Presenter(model,view);
        presenter.checkPassword();
        verify(view,times(1)).displayMessage("long password, safe password");
    }

}