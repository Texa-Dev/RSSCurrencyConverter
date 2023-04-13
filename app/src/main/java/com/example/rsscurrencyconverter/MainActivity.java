package com.example.rsscurrencyconverter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.widget.Toast;

import com.example.rsscurrencyconverter.data.Currency;
import com.example.rsscurrencyconverter.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Currency>>
        , Loader.OnLoadCanceledListener<List<Currency>> {
    public static final String TAG = "FF";
    private ActivityMainBinding binding;
    private static final String ADR = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";
    public static final int LOADER_ID = 1;
    public static final String OTHER_KEY = "adr_key";
    private Loader<List<Currency>> loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //1 var
       /* loader = LoaderManager.getInstance(this)
                .initLoader(LOADER_ID,null,this);*/

        //2var
        Bundle bundle = new Bundle();
        bundle.putString(OTHER_KEY, ADR);
        loader = LoaderManager.getInstance(this)
                .initLoader(LOADER_ID, bundle, this);

        //
        loader.registerOnLoadCanceledListener(this);
        //
        loader.forceLoad();
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<List<Currency>> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == LOADER_ID) {
            Log.d(TAG, "LOADERonCreate: ");
            return new AsyncTaskLoader<List<Currency>>(this) {
                @Nullable
                @Override
                public List<Currency> loadInBackground() {
                    //load
                    try {
                        TimeUnit.SECONDS.sleep(10);
                        URL url = new URL(args.getString(OTHER_KEY));
                        return new Gson().
                                fromJson(new JsonReader(new InputStreamReader(url.openStream())),
                                        new TypeToken<ArrayList<Currency>>() {
                                        });
                    } catch (Exception e) {
                        Log.d(TAG, "Blia" + e);
                    }
                    return null;
                }
            };
        }
       throw new RuntimeException("Wrong loader id");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Currency>> loader, List<Currency> data) {
        Log.d(TAG, "finish: " + data);
        Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Currency>> loader) {

    }

    @Override
    public void onLoadCanceled(@NonNull Loader<List<Currency>> loader) {

    }
}