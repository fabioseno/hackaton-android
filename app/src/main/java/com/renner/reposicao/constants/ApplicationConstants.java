package com.renner.reposicao.constants;

import android.app.Activity;

import com.renner.reposicao.models.User;

/**
 * Created by Dico
 * This class works as a Session Constants Persistence Unit
 */
public class ApplicationConstants {

    public static final String LOGIN_URL = "https://maratona.herokuapp.com/login";
    public static User currentUser;
    public static Activity currentActivity;
}
