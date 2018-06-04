package Break_Journey.BreakJourney;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;

import static Break_Journey.BreakJourney.R.id.destid;
import static Break_Journey.BreakJourney.R.id.srcid;


public class MainActivity extends AppCompatActivity {
//    ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.mainlayID);
    AutoCompleteTextView fromField;
    AutoCompleteTextView toField;
    public static final String mainQuery = "com.autofill.MainActivity.mainQuery";
    public static final String mainfrom = "com.autofill.MainActivity.mainFrom";
    public static final String mainto = "com.autofill.MainActivity.mainTo";
    Button getStnbut;
//    ListView StnlistView;
    String baseQuri = "http://sysadmean.pythonanywhere.com/";
//    String regexString = Pattern.quote("\"") + "(.*?)" + Pattern.quote("\"");
//    String[] adpfill = {""};
//    ArrayAdapter resadapter;
//    TextView Best;
//    TextView Worst ;
//    TextView restitle ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setToField();
        setFromField();
        getStnbut = (Button)findViewById(R.id.buttid);
        getStnbut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Buttmeth();
            }
        });

//        StnlistView = (ListView)findViewById(R.id.resultListView);
//        resadapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, adpfill);
//        StnlistView.setAdapter(resadapter);
//        StnlistView.setVisibility(View.GONE);
//        Best = (TextView)findViewById(R.id.best);
//        Worst = (TextView)findViewById(R.id.worst);
//        restitle = (TextView)findViewById(R.id.intermediate) ;
//        Best.setVisibility(View.GONE);
//        Worst.setVisibility(View.GONE);
//        restitle.setVisibility(View.GONE);

        //initiating results queried from main activity
    }

void setFromField(){
    String recvString = getString(R.string.localstnStr);
    String parlessStn = recvString.replaceAll("[()]", " ");
    String[] stations = parlessStn.split("-");
        fromField = (AutoCompleteTextView) findViewById(srcid);
        ArrayAdapter adapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, stations);
        fromField.setAdapter(adapter);
        fromField.setThreshold(1);
        fromField.setSelectAllOnFocus(true);
        fromField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View autoCompleteTextView1, boolean b) {
                if (!b) {
                    // on focus off
                    String str = fromField.getText().toString();

                    ListAdapter listAdapter = fromField.getAdapter();
                    for (int i = 0; i < listAdapter.getCount(); i++) {
                        String temp = listAdapter.getItem(i).toString();
                        if (str.compareTo(temp) == 0) {
                            return;
                        }
                    }

                    fromField.setText(R.string.forceSug);

                }
            }
        });
    fromField.requestFocus();
    }
    void setToField(){
        String recvString = getString(R.string.localstnStr);
        String parlessStn = recvString.replaceAll("[()]", " ");
        String[] stations = parlessStn.split("-");
        toField = (AutoCompleteTextView) findViewById(destid);
        ArrayAdapter adapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, stations);
        toField.setAdapter(adapter);
        toField.setThreshold(1);
        toField.setSelectAllOnFocus(true);
        toField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View autoCompleteTextView2, boolean b) {
                if (!b) {
                    // on focus off
                    String str = toField.getText().toString();

                    ListAdapter listAdapter = toField.getAdapter();
                    for (int i = 0; i < listAdapter.getCount(); i++) {
                        String temp = listAdapter.getItem(i).toString();
                        if (str.compareTo(temp) == 0) {
                            return;
                        }
                    }
                    toField.setText(R.string.forceSug);
                }
            }
        });
    }


//class getStnAsync extends AsyncTask<String,Void,String> {
//        @Override
//        protected String doInBackground(String... urlString) {
//            try {
//                URL url = new URL(urlString[0]);
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(line);
//                }
//                bufferedReader.close();
//                urlConnection.disconnect();
//                return stringBuilder.toString();
//            }
//            catch(Exception e) {
//                Log.d("curlError","getting error message");
//                Log.e("ERROR", e.getMessage(), e);
//                String stnfromloc = getString(R.string.localstnStr);
//                return stnfromloc;
//            }
//
//        }
//
////        protected void onProgressUpdate(Integer... progress) {
////            setProgressPercent(progress[0]);
////        }
//
//        protected void onPostExecute(String recvString ) {
//            Log.d("recstr",recvString);
//            String parlessStn = recvString.replaceAll("[()]", " ");
//            String[] parlessArr = parlessStn.split("-");
//            setFromField(parlessArr);
//            setToField(parlessArr);
//        }
//    }

    boolean validatefield(AutoCompleteTextView actvfield){
        String str = actvfield.getText().toString();

        ListAdapter listAdapter = actvfield.getAdapter();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            String temp = listAdapter.getItem(i).toString();
            if (str.compareTo(temp) == 0) {
                Log.d("validation Error","validation failed");
                return false;
            }
            else if( str.length()<=0 ){
                Log.d("validation Error","empty fields");
            }
        }

        fromField.setText(R.string.forceSug);
        Log.d("validation result:","validation passed");
        return true;
    }

    void Buttmeth(){
        if(validatefield(fromField) || validatefield(toField) ){
            return;

        }
        String[] fromChoice = fromField.getText().toString().split(" ");
        String[] toChoice = toField.getText().toString().split(" ");
        String Quri = baseQuri+fromChoice[fromChoice.length - 1]+"-"+toChoice[toChoice.length - 1];
        Intent intent = new Intent(this, Break_Journey.BreakJourney.childActivity.class);
        intent.putExtra(mainQuery, Quri);
        intent.putExtra(mainfrom,fromField.getText().toString());
        intent.putExtra(mainto,toField.getText().toString());
        startActivity(intent);
    }
//    void showResults(String[] StnResArr){
//        resadapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, StnResArr);
//        StnlistView.setAdapter(resadapter);
//        StnlistView.setVisibility(View.VISIBLE);
//        Best.setVisibility(View.VISIBLE);
//        Worst.setVisibility(View.VISIBLE);
//        restitle.setVisibility(View.VISIBLE);
//    }
//
//    class getResultAsy extends AsyncTask<String,Void,String> {
//        @Override
//        protected void onPreExecute(){
//            getStnbut.setBackgroundColor(Color.LTGRAY);
//            getStnbut.setText("Please Wait...");
//        }
//        @Override
//        protected String doInBackground(String... urlString) {
//            try {
//                URL url = new URL(urlString[0]);
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(line);
//                }
//                bufferedReader.close();
//                urlConnection.disconnect();
//                return stringBuilder.toString();
//            }
//            catch(Exception e) {
//                Log.d("curlStnError","stn res error");
//                Log.e("StnERROR", e.getMessage(), e);
//                return "\"Error:Check \"\" internet connection \"\" and restart app\"";
//            }
//        }
////        protected void onProgressUpdate(Integer... progress) {
////            setProgressPercent(progress[0]);
////        }
//
//        protected void onPostExecute(String recvString ) {
//            getStnbut.setBackgroundColor(Color.rgb(75,165,218));
//            getStnbut.setText("Find Routes");
//            Log.d("recstr",recvString);
//
//            Pattern pattern = Pattern.compile(regexString);
//            Matcher matcher = pattern.matcher(recvString);
//            StringBuilder sbuild = new StringBuilder();
//            while (matcher.find()) {
//                sbuild.append(matcher.group(1));
//                sbuild.append("-");
//            }
//            String[] StnResAsy = sbuild.toString().split("-");
//            showResults(StnResAsy);
//        }
//    }


} //MAIN ACTIVITY ENDS ..PHEW!
