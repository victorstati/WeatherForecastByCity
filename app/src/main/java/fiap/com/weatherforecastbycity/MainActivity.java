package fiap.com.weatherforecastbycity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText locationeditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationeditText = findViewById(R.id.locationEditText);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener((v) -> {
            String cidade = locationeditText.getEditableText().toString();
            String endereco = getString(
                    R.string.web_service_url,
                    cidade,
                    getString(R.string.api_key));
            obtemPrevisoesV4(endereco);
        });
    }

    public void obtemPrevisoesV4(String endereco){
        new ObtemPrevisoes().execute(endereco);
    }

    class ObtemPrevisoes extends AsyncTask <String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder("");
                String linha = null;
                while((linha = reader.readLine()) != null)
                    sb.append(linha);
                return sb.toString();
            }
            catch(IOException e){
                e.printStackTrace();
                return  null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }


    public void obtemPrevisoesV3(String endereco){
        new Thread(() -> {
            try{
                URL url = new URL(endereco);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder("");
                String linha = null;
                while((linha = reader.readLine()) != null)
                    sb.append(linha);
                runOnUiThread(() -> {
                    Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
                });
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }).start();
    }

    public void obtemPrevisoesV2(String endereco){
        new Thread(() -> {
            try{
                URL url = new URL(endereco);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder("");
                String linha = null;
                while((linha = reader.readLine()) != null)
                    sb.append(linha);
                    Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }).start();
    }

    public void obtemPrevisoesV1(String endereco){
        try{
            URL url = new URL(endereco);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder("");
            String linha = null;
            while((linha = reader.readLine()) != null)
                sb.append(linha);
            Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
