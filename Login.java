package com.example.intromapsvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator; // animation for the process bar
import android.animation.AnimatorListenerAdapter; // animation for the process bar
import android.annotation.TargetApi; // animation for the process bar
import android.content.Intent;
import android.os.Build; // animation for the process bar
import android.os.Bundle; // animation for the process bar
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    // capturing all the texts, buttons and other features I made in the
    // XML "activity_logon" design file
    EditText etprivateName, etlastName, etphoneNumber, etEmail, etPassword;
    Button btnEnter;
    TextView tvForgotPassword;

    // Making arrays for logging in
    // Visitors will be added to this array, its static because I need to access it from other class
    //static ArrayList <Visitor> visitorArrayList = new ArrayList<>();
    // Managers will be added to this array
    //static ArrayList <Manager> managerArrayList = new ArrayList<>();
    // Admins will be added to this array
    //static ArrayList <Admin> adminArrayList = new ArrayList<>();

    //Creating managers, visitors and admins
    Visitor visitor = new Visitor("111111111","Bruce", "Wayne", "0505111111", "BM@gmail.com","111111111", 32.563063, 34.939922); // in zic2
    Visitor visitor2 = new Visitor("222222222", "Peter", "Parker", "0505222222", "SM@gmail.com","222222222", 32.565448, 34.940921); // in the squre
    Visitor visitor3 = new Visitor("300965563", "IdanVisitor", "Gadker", "0509243088", "idan@gmail.com","300965563", 32.565212, 34.940314);// Mizpe gal
    Visitor visitor4 = new Visitor("333333333", "Harvey", "Dent", "0505333333", "HD@gmail.com","333333333", 32.653869, 35.293044); // in the building 5 YVC
    Visitor visitor5 = new Visitor("444444444", "Mr e", "nigma", "0509444444", "EN@gmail.com","444444444", 32.652610, 35.292730);// in the building 7 YVC

    Manager manager = new Manager("300965563", "IdanManager", "Gadker", "0509243088", "idan@gmail.com","300965563", "1234", true);
    Manager manager2 = new Manager("555555555", "Steve", "Rogers", "0509555555", "ca@gmail.com", "555555555","1", true);
    Manager manager3 = new Manager("204163471", "Ido", "Gold", "0547622406", "ido@gmail.com","204163471", "1234", true);

    Admin admin = new Admin( "300965563", "IdanAdmin","Gadker","0509243088", "idan@gmail.com","300965563", "1234",true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);

        // connecting the animation features when this page is created
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        // connecting all the features
        etprivateName = findViewById(R.id.etprivateName);
        etlastName = findViewById(R.id.etlastName);
        etphoneNumber = findViewById(R.id.etphoneNumber);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnEnter = findViewById(R.id.btnEnter);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        // Adding the visitors, managers and admins

//        visitorArrayList.add(visitor);
//        visitorArrayList.add(visitor2);
//        visitorArrayList.add(visitor3);
//        visitorArrayList.add(visitor4);
//        visitorArrayList.add(visitor5);
//
//        managerArrayList.add(manager2);
//        managerArrayList.add(manager); // adding me second to see if I get an error msg when login
//        managerArrayList.add(manager3);
//        adminArrayList.add(admin);


        Database.visitors.add(visitor);
        Database.visitors.add(visitor2);
        Database.visitors.add(visitor3);
        Database.visitors.add(visitor4);
        Database.visitors.add(visitor5);

        Database.managers.add(manager2);
        Database.managers.add(manager); // adding me second to see if I get an error msg when login
        Database.managers.add(manager3);

        Database.admins.add(admin);


        // Setting what gonna happen when i click on "btnEnter" button
        btnEnter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                boolean foundVisitor = false;
                boolean foundAdmin = false;
                //If the user didn't fill all the info
                if (etprivateName.getText().toString().isEmpty()||
                    etlastName.getText().toString().isEmpty() ||
                    etphoneNumber.getText().toString().isEmpty() ||
                    etEmail.getText().toString().isEmpty())// ||
                       // etPassword.getText().toString().isEmpty())
                    // There is no enough information to continue
                {
                    Toast.makeText(Login.this, "מלא את כל הפרטים בבקשה", Toast.LENGTH_SHORT).show();
                }
                else
                { // The info is OK
                    for (Visitor v: Database.visitors) // searching the info for a visitor
                    {
                        if(etprivateName.getText().toString().equals(v.getFirstName()) &&
                                etlastName.getText().toString().equals(v.getLastName()) &&
                                etphoneNumber.getText().toString().equals(v.getPhoneNumber()) &&
                                etEmail.getText().toString().equals(v.getEmail()))
                        {
                            Toast.makeText(Login.this, "ברוך הבא "+v.getFirstName(), Toast.LENGTH_SHORT).show();
                            v.setLoggedIn(true);
                            startActivity(new Intent(Login.this,visitorPage.class));
                            foundVisitor=true;
                            break;
                        }
                    }
                    if (!foundVisitor)
                    {
                        for (Admin a: Database.admins) // searching the info for a visitor
                        {
                            if(etprivateName.getText().toString().equals(a.getFirstName()) &&
                                    etlastName.getText().toString().equals(a.getLastName()) &&
                                    etphoneNumber.getText().toString().equals(a.getPhoneNumber()) &&
                                    etEmail.getText().toString().equals(a.getEmail()))
                            {
                                if(a.getIsFirst()){ // Check if the admin logged in for the first time.
                                    a.setIsFirst(false); // The admin is not a new one any more.
                                    a.setIsLogged(true);
                                    Toast.makeText(Login.this, " ברוך הבא "+a.getFirstName(), Toast.LENGTH_SHORT).show();
                                    // ^^ Send the right message to the new admin
                                }
                                else{ // The admin is in the array but he isn't a new one.
                                    Toast.makeText(Login.this, " ברוך שובך "+a.getFirstName(), Toast.LENGTH_SHORT).show();
                                    // ^^ Send a message to the admin that isn't logged in for first time .
                                    a.setIsLogged(true);
                                }
                                startActivity(new Intent(Login.this,adminPage.class));
                                foundAdmin=true;
                                break;
                                /*
                                Toast.makeText(Login.this, " ברוך הבא Admin "+a.getFirstName(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this,adminPage.class));
                                foundAdmin=true;
                                break;*/
                            }
                        }
                        if(!foundAdmin)
                        {
                            int index = 1;
                            for(Manager m : Database.managers){ // searching the manager that wants to log in the manager array.
                                m.setIsLogged(false); // making sure that nobody else will be logged
                                if(etprivateName.getText().toString().equals(m.getFirstName()) &&
                                        etlastName.getText().toString().equals(m.getLastName()) &&
                                        etphoneNumber.getText().toString().equals(m.getPhoneNumber()) &&
                                        etEmail.getText().toString().equals(m.getEmail()) &&
                                        etPassword.getText().toString().equals(m.getManagerPassword()))
                                // the manager is in the manager array.
                                {
                                    if(m.getIsFirst()){ // Check if the manager logged in for the first time.
                                        m.setIsFirst(false); // The manager is not a new one any more.
                                        Toast.makeText(Login.this, " ברוך הבא "+m.getFirstName(), Toast.LENGTH_SHORT).show();
                                        // ^^ Send the right message to the new manager
                                    }
                                    else{ // The manager is in the array but he isn't a new one.
                                        Toast.makeText(Login.this, " ברוך שובך "+m.getFirstName(), Toast.LENGTH_SHORT).show();
                                        // ^^ Send a message to the manager that isn't logged in for first time .
                                    }
                                    m.setIsLogged(true);
                                    startActivity(new Intent(Login.this,managerPage.class)); //Go to the manager page
                                    break;
                                }
                                index++;
                                if(index==Database.managers.size()) { // This info is not suiting to any manager existing.
                                    Toast.makeText(Login.this, "גישה לא מאושרת, הזיהוי נכשל", Toast.LENGTH_SHORT).show();
                                    index=1;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    // Start of the method that is responsible for the animation and login process
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) { // creation of method "showProgress" with a boolean value of "true" (*)
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE); // (1) if (*) is true (which it is) the visibility is "gone"
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE); // same as (1) but with the progress view
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
        // End of the method that is responsible for the animation and login process
    }
}