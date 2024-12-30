package zeev.fraiman.bigimagefromnetwithlibs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import coil.Coil;
import coil.request.ImageRequest;

public class CoilFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        String imageUrl = "https://storage.googleapis.com/search-ar-edu/periodic-table/element_001_hydrogen/element_001_hydrogen_srp_th.png";

        ImageRequest request = new ImageRequest.Builder(getContext())
                .data(imageUrl)
                .size(400, 400)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .target(imageView)
                .build();

        Coil.imageLoader(getContext()).enqueue(request);

        return view;
    }
}
