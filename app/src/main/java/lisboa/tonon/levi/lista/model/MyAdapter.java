package lisboa.tonon.levi.lista.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import lisboa.tonon.levi.lista.R;
import lisboa.tonon.levi.lista.activity.MainActivity;

public class MyAdapter extends RecyclerView.Adapter {

    MainActivity mainActivity;
        List<MyItem> itens;

    public MyAdapter(MainActivity mainActivity, List<MyItem> itens) {
        this.mainActivity = mainActivity;
        this.itens = itens;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        View v = inflater.inflate(R.layout.item_list, parent, false);
                return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Obtém o item atual a partir da lista de itens pela posição
        MyItem myItem = itens.get(position);
                View v = holder.itemView;
        
        // Configura a imagem do item, usando a URI armazenada
        ImageView imvFoto = v.findViewById(R.id.imvPhoto);
        imvFoto.setImageURI(myItem.photo);
        
        // Configura o título
        TextView tvTitle = v.findViewById(R.id.tvTitle);
        tvTitle.setText(myItem.title);
        
        // Configura a descrição 
        TextView tvDesc = v.findViewById(R.id.tvDesc);
        tvDesc.setText(myItem.description);
    }

    // Retorna a quantidade de itens na lista
    @Override
    public int getItemCount() {
        return itens.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
