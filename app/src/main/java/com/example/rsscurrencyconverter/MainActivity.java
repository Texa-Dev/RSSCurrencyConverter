package com.example.rsscurrencyconverter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.rsscurrencyconverter.data.Currency;
import com.example.rsscurrencyconverter.data.CurrencyConverter;
import com.example.rsscurrencyconverter.databinding.ActivityMainBinding;
import com.example.rsscurrencyconverter.menu.MenuManager;
import com.google.gson.Gson;
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
    public static final String ADR_KEY = "adr_key";
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
        bundle.putString(ADR_KEY, ADR);
        loader = LoaderManager.getInstance(this)
                .initLoader(LOADER_ID, bundle, this);

        //
        loader.registerOnLoadCanceledListener(this);
        //
        loader.forceLoad();
        binding.srcCurrency.setTag(Currency.BASE);
        binding.destCurrency.setTag(Currency.BASE);

        binding.convertBtn.setOnClickListener(view -> {
            binding.destValue.setText(CurrencyConverter.convert(
                    binding.srcValue.getText().toString(),
                    ((Currency)binding.srcCurrency.getTag()).getBuy(),
                    binding.destValue.getText().toString(),
                    ((Currency)binding.destCurrency.getTag()).getBuy()));
        });
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
                        URL url = new URL(args.getString(ADR_KEY));
                        return new Gson().
                                fromJson(new JsonReader(new InputStreamReader(url.openStream())),
                                        new TypeToken<ArrayList<Currency>>() {
                                        });
                    } catch (Exception e) {
                        Log.d(TAG, "Some error: " + e);
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

        binding.srcCurrency.setOnClickListener(v -> {
            MenuManager.currencyPopUp(this,data,binding.srcCurrency,binding.srcValue);
        });
        binding.destCurrency.setOnClickListener(v -> {
            MenuManager.currencyPopUp(this,data,binding.destCurrency,binding.destValue);
        });
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Currency>> loader) {

    }

    @Override
    public void onLoadCanceled(@NonNull Loader<List<Currency>> loader) {

    }
}