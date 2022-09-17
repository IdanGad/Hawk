package com.example.intromapsvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class visitorClassification extends AppCompatActivity
{
    TextView tvVisitorClassificationManName, tvClassificatiovResult;

    EditText etClssifyVAge, etClssifyVFamilyCondition,etClssifyVLocation,etClssifyVIncome;

    Button btnClassifyVisitor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_classification);

        // Connecting and setting the right manager name
        tvVisitorClassificationManName = findViewById(R.id.tvVisitorClassificationManName);
        tvVisitorClassificationManName.setText(setManagerName());

        // Connecting the result Text
        tvClassificatiovResult = findViewById(R.id.tvClassificatiovResult);

        // Connecting the data entered by the user
        etClssifyVAge = findViewById(R.id.etClssifyVAge);
        etClssifyVFamilyCondition = findViewById(R.id.etClssifyVFamilyCondition);
        etClssifyVLocation = findViewById(R.id.etClssifyVLocation);
        etClssifyVIncome = findViewById(R.id.etClssifyVIncome);

        // Connecting the button
        btnClassifyVisitor = findViewById(R.id.btnClassifyVisitor);

        btnClassifyVisitor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Catching all the data for verification
                String ageStr = etClssifyVAge.getText().toString();
                String familyConditionStr = etClssifyVFamilyCondition.getText().toString();
                String locationStr = etClssifyVLocation.getText().toString();
                String incomeStr = etClssifyVIncome.getText().toString();

                boolean dataOk = verifyInfo(ageStr,familyConditionStr,locationStr,incomeStr); // verification

                if(dataOk)// if all the data is ok
                {
                    // Start building the dataset
                    double [] arrAges = {18.0, 25.0,27.0,30.0,45.0,0.0}; // DataSet - ages
                    int age = Integer.parseInt(etClssifyVAge.getText().toString()); // converting the age to int
                    arrAges[arrAges.length-1] = age;// adding the age to the end of "arrAges" for normalization

                    String [] arrarrFamilyCondition = {"Single","Married","Married","Married","Devorced",""}; // dataSet - familyCondition
                    arrarrFamilyCondition[arrarrFamilyCondition.length-1] = etClssifyVFamilyCondition.getText().toString(); // adding to teh end of the array

                    String [] arrLocation = {"Askelon","Tel Aviv","Ramat Gan","Zichron","Jerusalem",""}; // dataSet - location
                    arrLocation[arrLocation.length-1] = etClssifyVLocation.getText().toString(); // ...

                    double [] arrIncome = {8000.0, 10000.0, 12000.0, 8500.0, 7000.0, 0.0}; // DataSet - ages
                    int income = Integer.parseInt(etClssifyVIncome.getText().toString()); // ...
                    arrIncome[arrIncome.length-1] = income;// ...

                    String [] arrClassification = {"Yes","No","No","Yes","Yes"}; // dataSet - classification
                    // End of building the dataset

                    // Normalizing age
                    double maxAge = 0;
                    double minAge = Integer.MAX_VALUE;
                    for (Double currAge : arrAges) { // get min and max values for the age
                        if(currAge>maxAge)
                            maxAge = currAge;
                        if(currAge<minAge)
                            minAge = currAge;
                    }

                    for(int i = 0; i<arrAges.length; i++) // age's normalization
                    {
                        arrAges[i]=(arrAges[i]-minAge)/(maxAge-minAge);
                    }

                    // Normalizing income
                    double maxIncome = 0;
                    double minIncome = Integer.MAX_VALUE;
                    for (Double currIncome : arrIncome) { // get min and max values for the income
                        if(currIncome>maxIncome)
                            maxIncome = currIncome;
                        if(currIncome<minIncome)
                            minIncome = currIncome;
                    }
                    for(int i = 0; i<arrIncome.length; i++)	{ // Income's normalization
                        arrIncome[i]=(arrIncome[i]-minIncome)/(maxIncome-minIncome);
                    }

                    // calculate distance
                    double [] distances = new double [5];
                    for(int i = 0; i<distances.length; i++)
                    {
                        double ageDist = Math.pow(arrAges[i]-arrAges[arrAges.length-1], 2);
                        double familyconditionDist = hamingDistance(arrarrFamilyCondition[i],arrarrFamilyCondition[arrarrFamilyCondition.length-1]);
                        double locationDist = hamingDistance(arrLocation[i],arrLocation[arrLocation.length-1]);
                        double incomeDist = Math.pow(arrIncome[i]-arrIncome[arrIncome.length-1], 2);
                        double totalDist = Math.sqrt(ageDist+familyconditionDist+locationDist+incomeDist);
                        distances[i]=totalDist;
                    }

                    int yesCounter = 0;
                    int noCounter = 0;
                    double minDist = Integer.MAX_VALUE; // init the minimum distance to the max value
                    int closestindex = 0; // this will be the closest every time
                    for (int i = 0; i<3; i++) // for 3nn
                    {
                        for (int j = 0; j<distances.length; j++) // search in  all the distances
                          {
                            if(distances[j]<minDist) // this will be the closest for this loop run
                            {
                                minDist = distances[j];
                                closestindex = j;
                            }
                        }
                        // once found the closest one
                        distances[closestindex] = Integer.MAX_VALUE; // making the closest to be max value so i don't catch it again
                        String closestClassification = arrClassification[closestindex]; // getting the closest classification
                        if(closestClassification.equals("Yes")) // if the answer is "yes" - count it
                            yesCounter+=1;
                        else
                            noCounter+=1; // else - count "no"
                    }

                    String ans = "";
                    if(yesCounter>noCounter)
                        ans = "The Visitor is dangerous";
                    else
                        ans = "The Visitor is NOT dangerous";

                    tvClassificatiovResult.setText(ans);
                }
                else // if the data is not correct
                {
                    Toast.makeText(visitorClassification.this, "Please enter correct data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean verifyInfo(String age, String familyCondition, String location, String income)
    {
        String agePattern = "^[0-9]{2}$";
        String familyConditionPattern = "^(Married)|(Single)|(Divorced)|(Widow)$";
        String locationPattern = "^([A-Za-z]+)|([A-Za-z]+ [A-za-z]+)$";
        String incomePattern = "^[\\d]+$";
        if (age.matches(agePattern) &&
                familyCondition.matches(familyConditionPattern) &&
                location.matches(locationPattern) &&
                income.matches(incomePattern))
            return true;
        else
            return false;
    }


    public String setManagerName(){
        String managerName = "Idan"; // initializing a manager name
        boolean found = false;
        for (Manager m: Database.managers) {
            if(m.getIsLogged()) {
                managerName = m.getFirstName()+" "+m.getLastName();
                found=true;
            }
        }
        if(!found) {
            return "Manager not found";
        } else
            return managerName;
    }

    public int hamingDistance(String str1, String str2) // helping method for Knn algorithm
    {
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
        if(str1.equals(str2))
            return 0;
        else
            return 1;
    }

}