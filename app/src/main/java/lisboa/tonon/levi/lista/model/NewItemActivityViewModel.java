package lisboa.tonon.levi.lista.model;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

public class NewItemActivityViewModel extends ViewModel {
       //Rferência para a foto selecionada
       Uri selectPhotoLocation = null;
       public Uri getSelectPhotoLocation() {
        return selectPhotoLocation;
       }

        public void setSelectPhotoLocation(Uri selectPhotoLocation) {
        this.selectPhotoLocation = selectPhotoLocation;
        }
}

