package com.example.trabalho_pdm.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalho_pdm.R;
import com.example.trabalho_pdm.dao.RegistroDao;
import com.example.trabalho_pdm.model.Rastreamento;

public class TelaPrincipalActivity extends AppCompatActivity {

    private final RegistroDao dao = new RegistroDao();


    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_tela_principal);
        final String TITULO_APPBAR = "Olá, " + getIntent().getStringExtra("NOME");
        setTitle(TITULO_APPBAR);
        final int idUsuario = getIntent().getIntExtra("ID_USUARIO", 0);

        Rastreamento testeRegistro1 = new Rastreamento(idUsuario, "Teste", "testado");
        dao.salva(testeRegistro1);
        Rastreamento testeRegistro2 = new Rastreamento(idUsuario, "Teste2", "testado2");
        dao.salva(testeRegistro2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ListView listaDeRegistros = findViewById(R.id.activity_principal_lista_registros);
        final ArrayAdapter adapter = new ArrayAdapter<>(
          this,
          android.R.layout.simple_list_item_1,
           dao.todos()
        );
        listaDeRegistros.setAdapter(adapter);
        listaDeRegistros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(TelaPrincipalActivity.this, "" + adapter.getItem(i), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
