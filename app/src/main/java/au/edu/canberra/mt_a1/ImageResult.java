package au.edu.canberra.mt_a1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.LanguageCodes;
import com.microsoft.projectoxford.vision.contract.Line;
import com.microsoft.projectoxford.vision.contract.OCR;
import com.microsoft.projectoxford.vision.contract.Region;
import com.microsoft.projectoxford.vision.contract.Word;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class ImageResult extends AppCompatActivity {

    static Bitmap imageResultImage;
    static String imageResultText;

    private EditText mEditText;

    static String textToPush;

    private VisionServiceClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_result);

        ImageView ImageResult_ImageView = (ImageView) findViewById(R.id.ImageView_LoadedImage);
        ImageResult_ImageView.setImageBitmap(imageResultImage);

        EditText editText = (EditText) findViewById(R.id.EditText_ReportedText);
        editText.setText(imageResultText);

        if (client==null){
            client = new VisionServiceRestClient("688faed9db86427db982300b336b68f8");
        }
        mEditText = (EditText) findViewById(R.id.EditText_ReportedText);

        runOCR();
    }
    private class doRequest extends AsyncTask<String, String, String> {
        // Store error message
        private Exception e = null;

        public doRequest() {
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                return process();
            } catch (Exception e) {
                this.e = e;    // Store error
            }

            return null;
        }
        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            // Display based on error existence
            if (e != null) {
                mEditText.setText("Error: " + e.getMessage());
                this.e = null;
            } else {
                Gson gson = new Gson();
                OCR r = gson.fromJson(data, OCR.class);

                String result = "";
                for (Region reg : r.regions) {
                    for (Line line : reg.lines) {
                        for (Word word : line.words) {
                            result += word.text + " ";
                        }
                        result += "\n";
                    }
                    result += "\n\n";
                }

                mEditText.setText(result);
            }
        }
    }
    public void runOCR () {
        try {
            new doRequest().execute();
        } catch (Exception e) {
            mEditText.setText("Error encountered. Exception is: " + e.toString());
        }
    }
    private String process() throws VisionServiceException, IOException {
        Gson gson = new Gson();

        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        imageResultImage.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        OCR ocr;
        ocr = this.client.recognizeText(inputStream, LanguageCodes.AutoDetect, true);

        String result = gson.toJson(ocr);
        Log.d("result", result);

        return result;
    }

    public void gotoAddItem(View view){startActivity(new Intent(this, AddItem.class));} //Retake button
    public void gotoLocalDatabase(View view){ //Confirm button
        EditText textEdit = (EditText) findViewById(R.id.EditText_ReportedText);
        textToPush = textEdit.getText().toString();

        LocalDatabase.listData.add(textToPush);

        startActivity(new Intent(this, LocalDatabase.class));
    }
}
