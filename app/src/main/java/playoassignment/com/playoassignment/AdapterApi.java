package playoassignment.com.playoassignment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import playoassignment.com.playoassignment.model.ModelApi;

/**
 * Created by Anuj on 10/10/17.
 */

public class AdapterApi extends RecyclerView.Adapter<AdapterApi.ApiListViewHolder> {

    private Activity activity;
    private ModelApi modelApi;

    AdapterApi(Activity activity, ModelApi modelApi) {
        this.activity = activity;
        this.modelApi = modelApi;
    }

    class ApiListViewHolder extends RecyclerView.ViewHolder{

        TextView author;
        TextView title;
        CardView rootlayout;

        ApiListViewHolder(View convertView) {
            super(convertView);

            author = convertView.findViewById(R.id.authorText);
            title = convertView.findViewById(R.id.titleText);
            rootlayout = convertView.findViewById(R.id.rootLayout);
        }
    }

    @Override
    public ApiListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_api, parent, false);

        return new ApiListViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(final ApiListViewHolder holder, final int position) {

        if(modelApi.getHits().get(position).getAuthor() != null){
            holder.author.setText(modelApi.getHits().get(position).getAuthor());
        }else{
            holder.author.setText("NA");
        }

        if(modelApi.getHits().get(position).getTitle() != null){
            holder.title.setText(modelApi.getHits().get(position).getTitle());
        }else{
            holder.title.setText("NA");
        }

        holder.rootlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(modelApi.getHits().get(position).getUrl() != null){
                    Intent intent = new Intent(activity, ActivityContentViewer.class);
                    intent.putExtra("url", modelApi.getHits().get(position).getUrl());
                    activity.startActivity(intent);
                }else{
                    Toast.makeText(activity, R.string.url_no_available, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelApi.getHits().size();
    }


}