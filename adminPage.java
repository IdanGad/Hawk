package com.example.intromapsvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class adminPage extends AppCompatActivity {

    Button btnAddManager,btnDeleteManager;
    TextView etAddManagerFirstName, etAddManagerLastName, etAddManagerId,
            etAddManagerPassword, etAddManagerPhonenumber, etAddManagerEmail, txadminname;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        txadminname = findViewById(R.id.txadminname);
        txadminname.setText(setAdminName());


        // Add Manager button
        btnAddManager = findViewById(R.id.btnAddManager);
        btnDeleteManager = findViewById(R.id.btnDeleteManager);

        // Text capturing
        etAddManagerFirstName = findViewById(R.id.etAddManagerName);
        etAddManagerLastName = findViewById(R.id.etAddManagerLastName);
        etAddManagerId = findViewById(R.id.etAddManagerId);
        etAddManagerPassword = findViewById(R.id.etAddManagerPassword);
        etAddManagerPhonenumber = findViewById(R.id.etAddManagerPhonenumber);
        etAddManagerEmail = findViewById(R.id.etAddManagerEmail);


        btnAddManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String managerId = etAddManagerId.getText().toString();
                String managerFirstName = etAddManagerFirstName.getText().toString();
                String managerLastName = etAddManagerLastName.getText().toString();
                String managerPhoneNumber = etAddManagerPhonenumber.getText().toString();
                String managerEmail = etAddManagerEmail.getText().toString();

                Boolean dataOk = verifyData(managerId,managerFirstName,managerLastName,managerPhoneNumber,managerEmail);


                if(!dataOk)
                {
                    Toast.makeText(adminPage.this, "הזן מידע תקין בבקשה", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Manager newManager = new Manager(
                            etAddManagerId.getText().toString(),
                            etAddManagerFirstName.getText().toString(),
                            etAddManagerLastName.getText().toString(),
                            etAddManagerPhonenumber.getText().toString(),
                            etAddManagerEmail.getText().toString(),
                            etAddManagerId.getText().toString(),
                            etAddManagerPassword.getText().toString(),
                            true);
                    Database.managers.add(newManager);
                    Toast.makeText(adminPage.this, "המנהל "+ etAddManagerId.getText().toString() +" הוסף בהצלחה", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnDeleteManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // For deletion io don't need to verify the data because if
                // the data is not correct i will not find the manager in the DB
                if(etAddManagerId.getText().toString().equals("") ||
                        etAddManagerFirstName.getText().toString().equals("") ||
                        etAddManagerLastName.getText().toString().equals("") ||
                        etAddManagerPhonenumber.getText().toString().equals("") ||
                        etAddManagerEmail.getText().toString().equals(""))
                {
                    Toast.makeText(adminPage.this, "מלא את כל השדות בבקשה", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int index = 1;
                    for (Manager m : Database.managers)
                    {
                          if(m.getManagerId().equals(etAddManagerId.getText().toString()))
                          {
                              Database.managers.remove(m);
                              Toast.makeText(adminPage.this,"המנהל "+m.getManagerId()+" נמחק בהצלחה", Toast.LENGTH_SHORT).show();
                              break;
                          }
                          if(index==Database.managers.size())
                          {
                              Toast.makeText(adminPage.this,"המנהל לא קיים במאגר ", Toast.LENGTH_SHORT).show();
                              break;
                          }
                          index++;
                    }
                    // No need to make "index" equal to zero
                }
            }
        });
        // Manager(String id,String firstName, String lastName, String phoneNumber, String email, String managerId, String managerPassword,boolean isFirst )
        // This is how the constructor is build
    }

    /*
     * This method will verify the data
     */
    public boolean verifyData(String managerId,
                              String managerFirstName,
                              String managerLastName,
                              String managerPhoneNumber,
                              String managerEmail)
    {
        // Patterns for all the data
        String idPattern = "^[0-9]{9}$";
        String namePattern = "^[A-Za-z]+$";
        String phonePattern = "^[0-9]{10}$";
        String emailPatrretn = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

        if (managerId.matches(idPattern) &&
                managerFirstName.matches(namePattern) &&
                managerLastName.matches(namePattern) &&
                managerPhoneNumber.matches(phonePattern) &&
                managerEmail.matches(emailPatrretn))
        {
            return true;
        }
        else
            return false;
    }


    public String setAdminName()
    {
        String adminName = "Idan"; // initilizing a manager name
        boolean found = false;
        for (Admin a: Database.admins)
        {
            if(a.getIsLogged())
            {
                adminName = a.getFirstName()+" "+a.getLastName();
                found=true;
            }
        }
        if(!found){
            return "Admin not found";
        }else
            return adminName;
    }


}