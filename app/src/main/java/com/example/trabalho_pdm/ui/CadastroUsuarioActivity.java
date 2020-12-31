package com.example.trabalho_pdm.ui;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.trabalho_pdm.R;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class CadastroUsuarioActivity extends AppCompatActivity {
    public static final String TITULO_APPBAR = "Novo Usuario";
    private EditText txtEmail;
    private EditText txtSenha;
    private EditText txtNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        setTitle(TITULO_APPBAR);
        txtEmail = findViewById(R.id.activity_cadastro_email);
        txtSenha = findViewById(R.id.activity_cadastro_senha);
        txtNome = findViewById(R.id.activity_cadastro_nome);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }



    public void cadastrarClick(View view) {
        HttpAsyncTask task = new HttpAsyncTask();
        task.execute("https://trab-pdm.000webhostapp.com/insert-usuario.php"); //link do serviço que faz insert no banco
    }

    public class HttpAsyncTask extends AsyncTask<String, Void, String> {

        ProgressDialog dialog;
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]); //link do serviço vem aqui
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setRequestMethod("POST");
                conexao.setDoInput(true);
                conexao.setDoOutput(true);
                ContentValues dto = new ContentValues();
                dto.put("nome", "teste estatico"); //aqui teoricamente colocaria passaria pro POST
                dto.put("email", "email estatico"); //to forçando "teste estatico"
                dto.put("senha", "123");

                OutputStream out = conexao.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                writer.write(getFormData(dto));
                writer.flush();

                int status = conexao.getResponseCode();

                if (status == 200) {
                    InputStream stream = new BufferedInputStream(conexao.getInputStream());
                    BufferedReader buff = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder builder = new StringBuilder();
                    String str = "";
                    while ((str = buff.readLine()) != null) {
                        builder.append(str);
                    }
                    conexao.disconnect();

                    return builder.toString();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(CadastroUsuarioActivity.this);
            dialog.show();
        }

        @Override
        protected void onPostExecute(String dto) {
            super.onPostExecute(dto);
            dialog.dismiss();
            finish();
        }
    }

    private String getFormData(ContentValues dto) {
        try {
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, Object> entry : dto.valueSet()) {
                if (first) {
                    first = false;
                } else {
                    sb.append("&");
                }

                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
