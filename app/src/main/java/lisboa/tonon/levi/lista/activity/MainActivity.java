package lisboa.tonon.levi.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lisboa.tonon.levi.lista.R;
import lisboa.tonon.levi.lista.model.MainActivityViewModel;
import lisboa.tonon.levi.lista.model.MyAdapter;
import lisboa.tonon.levi.lista.model.MyItem;
import lisboa.tonon.levi.lista.util.Util;

public class MainActivity extends AppCompatActivity {

    // Constante para definir o código de requisição de um novo item
    static int NEW_ITEM_REQUEST = 1;

    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configuração do botão flutuante
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent para abrir a NewItemActivity ao dedar o botão
                Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                startActivityForResult(i, NEW_ITEM_REQUEST);
            }
        });
        MainActivityViewModel vm = new ViewModelProvider( this ).get(
                MainActivityViewModel.class );
        List<MyItem> itens = vm.getItens();
        // Exibir os itens na tela
        RecyclerView rvItens = findViewById(R.id.rvItens);
        myAdapter = new MyAdapter(this, itens);
        rvItens.setAdapter(myAdapter);

        // Limitar o RecyclerView
        rvItens.setHasFixedSize(true);

        // Exibir em lista vertical
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);
        // Adiciona uma linha divisória entre os itens do RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verifica se o código de requisição corresponde ao de novo item e se o resultado foi bem-sucedido
        if (requestCode == NEW_ITEM_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                // Cria um novo objeto e preenche com os dados recebidos da NewItemActivity
                MyItem myItem = new MyItem();
                myItem.title = data.getStringExtra("title");       
                myItem.description = data.getStringExtra("description");
                Uri selectedPhotoURI = data.getData();
                //Carrega e transforma a imagem em bitmap
                 try {
                 Bitmap photo = Util.getBitmap( MainActivity.this, selectedPhotoURI, 100, 100 );
                    myItem.photo = photo;
                 } catch (FileNotFoundException e) {
                    e.printStackTrace();
                 }
                 //Obtém lista e guarda novo item dentro dela
                MainActivityViewModel vm = new ViewModelProvider( this ).get(
                        MainActivityViewModel.class );
                List<MyItem> itens = vm.getItens();

                // Adiciona o novo item à lista e notifica o adaptador para atualizar o RecyclerView
                itens.add(myItem);
                myAdapter.notifyItemInserted(itens.size() - 1);
            }
        }
    }
}
