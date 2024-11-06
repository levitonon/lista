package lisboa.tonon.levi.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import lisboa.tonon.levi.lista.R;
import lisboa.tonon.levi.lista.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {

    // Código para identificar a requisição de seleção de imagem
    static int PHOTO_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Obtém URI dentro de ViewModel
        NewItemActivityViewModel vm = new ViewModelProvider( this ).get(
                NewItemActivityViewModel.class );

         Uri selectPhotoLocation = vm.getSelectPhotoLocation();
         if(selectPhotoLocation != null) {
             ImageView imvfotoPreview = findViewById(R.id.imvPhotoPreview);
            imvfotoPreview.setImageURI(selectPhotoLocation);
             }
        // Configuração do botão de selecionar uma foto
        ImageButton imgCI = findViewById(R.id.imbCl);
        imgCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent que abre os documentos para selecionar imagem
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*"); // Define o tipo de arquivo como imagem
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST);
            }
        });

        // Configuração do botão para adicionar
        Button btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri photoSelected = vm.getSelectPhotoLocation();
                // Verifica se uma foto foi selecionada
                if (photoSelected == null) {
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem!", Toast.LENGTH_LONG).show();
                    return;
                }
                // Captura o título do item
                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();
                // Verifica se o título foi preenchido
                if (title.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título", Toast.LENGTH_LONG).show();
                    return;
                }
                // Captura a descrição
                EditText etDesc = findViewById(R.id.etDesc);
                String description = etDesc.getText().toString();
                // Verifica se a descrição foi preenchida
                if (description.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir uma descrição", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent();
                i.setData(photoSelected); // Adiciona a URI da imagem
                i.putExtra("title", title); // Adiciona o título
                i.putExtra("description", description); // Adiciona a descrição
                
                // Após sucesso, finaliza a Activity
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Verifica se o resultado é do seletor de imagem
        if (requestCode == PHOTO_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                // Armazena a URI da imagem
                Uri photoSelected = data.getData();
                // Exibe a imagem selecionada em uma ImageView como pré-visualização
                ImageView imvFotoPreview = findViewById(R.id.imvPhotoPreview);
                imvFotoPreview.setImageURI(photoSelected);
                //Escolhe imagem e armazena
                NewItemActivityViewModel vm = new ViewModelProvider( this
                ).get( NewItemActivityViewModel.class );
                vm.setSelectPhotoLocation(photoSelected);
            }
        }
    }
}
