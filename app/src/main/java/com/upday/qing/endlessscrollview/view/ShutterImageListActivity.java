package com.upday.qing.endlessscrollview.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.upday.qing.endlessscrollview.R;
import com.upday.qing.endlessscrollview.databinding.ListLayoutBinding;
import com.upday.qing.endlessscrollview.model.Item;
import com.upday.qing.endlessscrollview.rxadapter.AdapterData;
import com.upday.qing.endlessscrollview.viewmodel.ShutterListModel;

import java.util.List;

/**
 * @Author Qing Guo
 * @Date 2018/7/10
 * @Description Recyclerview main activity.
 */
public class ShutterImageListActivity extends AppCompatActivity implements ShutterListModel.ShutterImageListener {
    private ShutterListModel viewModel;
    private AdapterData dataSource;
    private ListLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        binding = DataBindingUtil.setContentView(this, R.layout
                .list_layout);
        viewModel = new ShutterListModel(this, this);
        binding.setViewModel(viewModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    public void onUpdate(List<Item> data) {
        dataSource.updateDataSource(data).setmAdapter();
    }

    @Override
    public void onInit(List<Item> data) {
        dataSource = new AdapterData(this, data);
        setRecyclerView();
    }

    @Override
    public void onReachEnd() {
        Toast.makeText(this, getString(R.string.fetch_end), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String header, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(ShutterImageListActivity.this).create();
        alertDialog.setTitle(header);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.xml_confirm),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onNext() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRecyclerView();
    }

    /***
     * Setup the Recyclerview
     */
    private void setRecyclerView() {
        dataSource
                .bindRecyclerView(binding.recyclerView)
                .setScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (viewModel.isHasMore() && dataSource.isScrolledEnd()) {
                            viewModel.loadShutterImages();
                        }
                    }
                });
    }
}
