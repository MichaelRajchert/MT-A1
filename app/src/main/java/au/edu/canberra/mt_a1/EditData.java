package au.edu.canberra.mt_a1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import static au.edu.canberra.mt_a1.R.styleable.View;

public class EditData extends AppCompatActivity {
    static String editText;
    static int itemPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        EditText textEditor = (EditText) findViewById(R.id.EditData_EditText_EditText);
        textEditor.setText(editText, TextView.BufferType.EDITABLE);
    }

    public void confirm(View view){
        EditText textEditor = (EditText) findViewById(R.id.EditData_EditText_EditText);

        LocalDatabase.listData.add(itemPosition, textEditor.getText().toString());
        startActivity(new Intent(this, LocalDatabase.class));
    }
    public void cancel(View view){ startActivity(new Intent(this, LocalDatabase.class)); }
}
