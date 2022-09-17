package com.example.intromapsvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class managerPage extends AppCompatActivity {

    // Creating all the buttons I added in the "activity_manager_page.xml" file
    Button btnshowVisitorMap, btnaddVisitor, btndeleteVisitor, btnclassifyVisitor,
            btnaddpolygon,btndeletepolygon ,btncreatereport;

    EditText etmanagerNameDisplay;
    TextView tvmanagername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_page);

        // Linking all the buttons
        btnshowVisitorMap = findViewById(R.id.btnshowVisitorMap);
        btnaddVisitor = findViewById(R.id.btnaddVisitor);
        btndeleteVisitor = findViewById(R.id.btndeleteVisitor);
        btnclassifyVisitor = findViewById(R.id.btnclassifyVisitor);
        btnaddpolygon = findViewById(R.id.btnaddpolygon);
        btncreatereport = findViewById(R.id.btncreatereport);
        btndeletepolygon = findViewById(R.id.btndeletepolygon);

        // name of manager to be at the top
        tvmanagername = findViewById(R.id.tvmanagername);
        tvmanagername.setText(setManagerName()); // setting the right manager name

        btnshowVisitorMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(managerPage.this,MapsActivity.class));
            }
        });

        btnaddVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(managerPage.this, "כפתור לא פעיל", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(managerPage.this,addAVisitor.class));
            }
        });

        btndeleteVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(managerPage.this,deleteVisitor.class));
            }
        });

        btnclassifyVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(managerPage.this,visitorClassification.class));
            }
        });

        btncreatereport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Date reportDate = new Date(System.currentTimeMillis());
                Set <Event> TodayEvents = new HashSet<>();
                for (Event e: Database.events)
                {
                    if(e.getDate().equals(reportDate))
                    {
                        TodayEvents.add(e);
                    }
                }
                if(TodayEvents.size()==0)
                {
                    Toast.makeText(managerPage.this, "לא היו אירועים חריגים היום", Toast.LENGTH_SHORT).show();
                }
                // create a file and send it to the manager email
            }
        });

        btnaddpolygon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(managerPage.this,addPolygon.class));
            }
        });

        btndeletepolygon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(managerPage.this,deletePolygon.class));
            }
        });

    }

    public String setManagerName() {
        String managerName = "Idan"; // initilizing a manager name
        boolean found = false;
        for (Manager m: Database.managers){
            if(m.getIsLogged()){
                managerName = m.getFirstName()+" "+m.getLastName();
                found=true;
            }
        }
        if(!found){
            return "Manager not found";
        }else
            return managerName;
    }
}