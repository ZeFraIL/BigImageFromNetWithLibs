package zeev.fraiman.bigimagefromnetwithlibs;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
    implements NetworkReceiver.NetworkStateListener{

    Context context;
    private Button buttonGlide, buttonPicasso, buttonCoil;
    private NetworkReceiver networkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;

        buttonGlide = findViewById(R.id.button_glide);
        buttonPicasso = findViewById(R.id.button_picasso);
        buttonCoil = findViewById(R.id.button_coil);

        buttonGlide.setOnClickListener(v -> loadFragment(new GlideFragment()));
        buttonPicasso.setOnClickListener(v -> loadFragment(new PicassoFragment()));
        buttonCoil.setOnClickListener(v -> loadFragment(new CoilFragment()));

        networkReceiver = new NetworkReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);

        //loadFragment(new GlideFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkReceiver); // Удаляем ресивер при уничтожении активности
    }

    @Override
    public void onNetworkStateChanged(boolean isConnected) {
        buttonGlide.setEnabled(isConnected);
        buttonPicasso.setEnabled(isConnected);
        buttonCoil.setEnabled(isConnected);

        String message = isConnected ? "Connection OK" : "Not found connection";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
