package playoassignment.com.playoassignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import playoassignment.com.playoassignment.model.ModelApi;
import playoassignment.com.playoassignment.util.AppConstant;
import playoassignment.com.playoassignment.util.CallCompleted;
import playoassignment.com.playoassignment.util.Common;
import playoassignment.com.playoassignment.util.HttpCalls;

public class ActivityHome extends AppCompatActivity implements CallCompleted {

    private RecyclerView recyclerView;
    private ModelApi model;
    private boolean loading = false;
    private AdapterApi adapterApi;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Common.setToolbar(toolbar, getString(R.string.app_name), this, false);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutmanager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = layoutmanager.getItemCount();
                int lastVisibleItem = layoutmanager.findLastVisibleItemPosition();

                // pagignation handling
                if (!loading && totalItemCount <= (lastVisibleItem + 1)) {
                    try {

                        int currentPage = model.getPage() + 1;
                        if (model.getNbPages() > currentPage) {
                            loading = true;
                            progressBar.setVisibility(View.VISIBLE);
                            HttpCalls.getInstance(ActivityHome.this).handleHttpCalls(AppConstant.API_URL +"&page="+currentPage, ActivityHome.this, AppConstant.REQUEST_CODE);
                        }
                    } catch (Exception exp) {
                        exp.printStackTrace();
                    }
                }

            }
        });

        if(Common.isNetworkAvailable(this)){
            progressBar.setVisibility(View.VISIBLE);
            HttpCalls.getInstance(this).handleHttpCalls(AppConstant.API_URL+"&page=0", this, AppConstant.REQUEST_CODE);
        }else{
            Toast.makeText(this, R.string.no_network, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCallComplete(String response, int requestCode, int status) {

        progressBar.setVisibility(View.GONE);
        if(status == AppConstant.API_STATUS_SUCCESS){
            loading = false;
            try{
                ModelApi newModel = new Gson().fromJson(response, ModelApi.class);

                if(newModel.getPage() == 0){
                    model = newModel;
                    adapterApi = new AdapterApi(ActivityHome.this, model);
                    recyclerView.setAdapter(adapterApi);
                }else{
                    model.setPage(newModel.getPage());
                    model.getHits().addAll(newModel.getHits());
                    adapterApi.notifyDataSetChanged();
                }
            }catch (Exception exp){
                Toast.makeText(this, R.string.some_error_occured, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        }
    }
}
