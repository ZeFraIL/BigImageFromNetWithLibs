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

        // Установите обработчики кнопок
        buttonGlide.setOnClickListener(v -> loadFragment(new GlideFragment()));
        buttonPicasso.setOnClickListener(v -> loadFragment(new PicassoFragment()));
        buttonCoil.setOnClickListener(v -> loadFragment(new CoilFragment()));

        // Регистрируем ресивер
        networkReceiver = new NetworkReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);

        // Загружаем первый фрагмент по умолчанию
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
        // Обновляем состояние кнопок
        buttonGlide.setEnabled(isConnected);
        buttonPicasso.setEnabled(isConnected);
        buttonCoil.setEnabled(isConnected);

        // Показываем сообщение
        String message = isConnected ? "Подключено к Интернету" : "Нет подключения к Интернету";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
