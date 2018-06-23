package dev.eder.padilla.sunmip1androidprinter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import dev.eder.padilla.sunmip1androidprinter.utils.AidlUtil;
import dev.eder.padilla.sunmip1androidprinter.utils.BluetoothUtil;
import dev.eder.padilla.sunmip1androidprinter.utils.ESCUtil;

public class MainActivity extends BaseActivity {

    String pornHub ="https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/styles/main_element/public/media/image/2018/04/dragon-ball-super-pelicula_1.jpg?itok=ezm73Nm3";
    ImageView imageView;
    private Bitmap bitmap;

    @SuppressLint("CheckResult")
    @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       imageView = findViewById(R.id.imageView);
       AidlUtil.getInstance().connectPrinterService(this);
       Glide.with(this).load(pornHub).into(imageView);
        Glide.with(getApplicationContext())
                .asBitmap().
                load(pornHub)
                .apply(new RequestOptions().override(100, 100))
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Log.e("TAG","failed");
                        e.printStackTrace();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        // resource is your loaded Bitmap
                        Log.e("TAG","Ready");
                        bitmap = resource;
                        return true;
                    }
                }).submit();
       //AidlUtil.getInstance().initPrinter();
       //AidlUtil.getInstance().printText("Hola mundo",12,true,false);
   }

   public void print(View view){
       if (baseApp.isAidl()) {
           Log.e("TAG","entra a AIDL");
           AidlUtil.getInstance().printText("Hello World!", 52, true, false);
           Log.e("TAG","bitmap "+bitmap);
           byte[] send;
           send = ESCUtil.alignCenter();
           AidlUtil.getInstance().sendRawData(send);
           AidlUtil.getInstance().printBitmap(bitmap, 2);

           AidlUtil.getInstance().print3Line();
       } else {
           Log.e("TAG","entra a Bluetooth");
           //printByBluTooth(content);
       }
   }
   public void checkConnection(View view){
       Log.e("TAG","conectado "+AidlUtil.getInstance().isConnect());
   }
    private void printByBluTooth(String content) {
       //try {
       //    if (isBold) {
       //        BluetoothUtil.sendData(ESCUtil.boldOn());
       //    } else {
       //        BluetoothUtil.sendData(ESCUtil.boldOff());
       //    }

       //    if (isUnderLine) {
       //        BluetoothUtil.sendData(ESCUtil.underlineWithOneDotWidthOn());
       //    } else {
       //        BluetoothUtil.sendData(ESCUtil.underlineOff());
       //    }

       //    if (record < 17) {
       //        BluetoothUtil.sendData(ESCUtil.singleByte());
       //        BluetoothUtil.sendData(ESCUtil.setCodeSystemSingle(codeParse(record)));
       //    } else {
       //        BluetoothUtil.sendData(ESCUtil.singleByteOff());
       //        BluetoothUtil.sendData(ESCUtil.setCodeSystem(codeParse(record)));
       //    }

       //    BluetoothUtil.sendData(content.getBytes(mStrings[record]));
       //    BluetoothUtil.sendData(ESCUtil.nextLine(3));
       //} catch (IOException e) {
       //    e.printStackTrace();
       //}
    }
}
