package sg.edu.rp.c346.id20041877.oursingapore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tvName, tvDescription, tvSquareKm;
    RatingBar rbStars;

    ListView lv;
    Button btnInsertIsland;
    ArrayList<Island> islandList;

    int requestCode = 9;
    Button btn5Stars;
    CustomAdapter ca;

    //ArrayList<String> stars;
    Spinner spinner;
    ArrayAdapter spinnerAdapter;

    AlertDialog.Builder alert, alert1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) this.findViewById(R.id.lv);
        btn5Stars = (Button) this.findViewById(R.id.btnShow5Stars);
        spinner = (Spinner) this.findViewById(R.id.sStars);

        DBHelper dbh = new DBHelper(this);
        islandList = dbh.getAllIslands();
        //tvSquareKm = dbh.getSq();
        dbh.close();

        ca = new CustomAdapter(this,R.layout.row,islandList);
        lv.setAdapter(ca);

        btnInsertIsland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View viewDialog = inflater.inflate(R.layout.insert, null);

                final EditText etName = viewDialog.findViewById(R.id.etName);
                final EditText etDescription = viewDialog.findViewById(R.id.etDescription);
                final EditText etSquareKm = viewDialog.findViewById(R.id.etSquareKm);
                final RatingBar stars = viewDialog.findViewById(R.id.rbStars);

                alert1 = new AlertDialog.Builder(MainActivity.this);
                alert1.setView(viewDialog).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        if (etName.getText().toString().isEmpty() || etDescription.getText().toString().isEmpty() || etSquareKm.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Empty inputs", Toast.LENGTH_SHORT).show();
                        } else {
                            String name = etName.getText().toString().trim();
                            String description = etDescription.getText().toString().trim();
                            if (name.length() == 0 || description.length() == 0){
                                Toast.makeText(MainActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            String squareKm_str = etSquareKm.getText().toString().trim();
                            int squareKm = Integer.valueOf(squareKm_str);
                            int stars = stars.getRating();

                            DBHelper dbh = new DBHelper(MainActivity.this);
                            long result = dbh.insertIsland(name, description, squareKm, stars);

                            if (result != -1) {
                                Toast.makeText(MainActivity.this, "Island inserted", Toast.LENGTH_LONG).show();
                                etName.setText("");
                                etDescription.setText("");
                                etSquareKm.setText("");
                            } else {
                                Toast.makeText(MainActivity.this, "Insert failed", Toast.LENGTH_LONG).show();
                            }
                            ca.notifyDataSetChanged();
                        }
                    }
                });
                alert1.show();
                ca.notifyDataSetChanged();
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                View textEntryView = factory.inflate(R.layout.update, null);

                final EditText etName = textEntryView.findViewById(R.id.etName);
                final EditText etDescription = textEntryView.findViewById(R.id.etDescription);
                final EditText etSquareKm = textEntryView.findViewById(R.id.etSquareKm);
                final RatingBar stars = textEntryView.findViewById(R.id.rbStars);
                alert = new AlertDialog.Builder(MainActivity.this);
                final Island currentIsland = islandList.get(position);
                alert.setView(textEntryView).setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        if (etName.getText().toString().isEmpty() || etDescription.getText().toString().isEmpty() || etSquareKm.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Empty inputs", Toast.LENGTH_SHORT).show();
                        } else {

                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Are you sure ?")
                                    .setMessage("Do you want to update this item")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            DBHelper dbh = new DBHelper(MainActivity.this);
                                            currentIsland.setName(etName.getText().toString().trim());
                                            currentIsland.setDescription(etDescription.getText().toString().trim());
                                            int squareKm = 0;
                                            try {
                                                squareKm = Integer.valueOf(etSquareKm.getText().toString().trim());
                                            } catch (Exception e) {
                                                Toast.makeText(MainActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            currentIsland.setSquareKm(squareKm);
                                            currentIsland.setStars((int) stars.getRating());
                                            int result = dbh.updateSong(currentIsland);
                                            if (result > 0) {
                                                Toast.makeText(MainActivity.this, "Song updated", Toast.LENGTH_SHORT).show();
                                                setResult(RESULT_OK);
                                                finish();
                                            } else {
                                                Toast.makeText(MainActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                                            }

                                            ca.notifyDataSetChanged();
                                        }
                                    })
                                    .setNegativeButton("No",null)
                                    .show();

                            ca.notifyDataSetChanged();
                        }
                    }
                });
                alert.setView(textEntryView).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Are you sure ?")
                                .setMessage("Do you want to update this item")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DBHelper dbh = new DBHelper(MainActivity.this);
                                        int result = dbh.deleteSong(currentIsland.getId());
                                        if (result>0){
                                            Toast.makeText(MainActivity.this, "Island deleted", Toast.LENGTH_SHORT).show();
                                            setResult(RESULT_OK);
                                            finish();
                                        } else {
                                            Toast.makeText(MainActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .setNegativeButton("No",null)
                                .show();

                        ca.notifyDataSetChanged();
                    }
                });
                alert.show();
                //return true;
            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                islandList.clear();
                islandList.addAll(dbh.getAllIslandByStar(5));
                ca.notifyDataSetChanged();
            }
        });


        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stars);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                islandList.clear();
                islandList.addAll(dbh.getAllIslandByStar(5));
                ca.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == this.requestCode && resultCode == RESULT_OK){
            DBHelper dbh = new DBHelper(this);
            islandList.clear();
            islandList.addAll(dbh.getAllIslands());
            dbh.close();
            ca.notifyDataSetChanged();

        }
        super.onActivityResult(requestCode, resultCode, data);

    }


}