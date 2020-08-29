package arte.programar.tj_base;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

public class MainActivity extends AppCompatActivity implements Connectable, Disconnectable, Bindable {

    private Merlin merlin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        merlin = new Merlin.Builder().withConnectableCallbacks()
                .withDisconnectableCallbacks()
                .withBindableCallbacks()
                .build(this);

        merlin.registerBindable(this);
        merlin.registerConnectable(this);
        merlin.registerDisconnectable(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (merlin != null){
            merlin.bind();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (merlin != null){
            merlin.unbind();
        }
    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (!networkStatus.isAvailable()){
            onDisconnect();
        }
    }

    @Override
    public void onConnect() {
        Toast.makeText(getApplication(), "Con Acceso a Internet :)", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDisconnect() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(getApplication(), "Sin Acceso a Internet", Toast.LENGTH_LONG).show();

            }
        });
    }
}