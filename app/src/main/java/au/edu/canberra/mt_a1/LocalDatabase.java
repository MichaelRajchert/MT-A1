package au.edu.canberra.mt_a1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LocalDatabase extends AppCompatActivity {
    public ListView listView;
    static List<String> listData = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_database);

        listView = (ListView) findViewById(R.id.ListView_ListView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                listData
        );
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditData.editText = String.valueOf(parent.getItemAtPosition(position));
                EditData.itemPosition = position;
                listData.remove(parent.getItemAtPosition(position));
                gotoEditData(view);
            }
        });
    }
    public void gotoEditData(View view){
        startActivity(new Intent(this, EditData.class));
    }
    public void gotoAddItemActivity(View view){startActivity(new Intent(this, AddItem.class));}
}
