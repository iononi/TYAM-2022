package com.example.lorempicsum;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoremFragment extends Fragment {
    ArrayList<Lorem> foo = new ArrayList<>();
    RecyclerView recyclerView;
    private LoremAdapter loremAdapter;
    private List<Lorem> dataArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragments_ui,container,false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        dataArrayList = new ArrayList<>();

        FragmentActivity activity = getActivity ();
        if (activity == null) return;

        recyclerView = view.findViewById(R.id.rvElements);
        recyclerView.addItemDecoration (new DividerItemDecoration(view.getContext (), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator (new DefaultItemAnimator());
        recyclerView.setLayoutManager (new LinearLayoutManager(view.getContext ()));
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext (),
         //       2, GridLayoutManager.VERTICAL, false));

        SWApiService service = Utils.createService();

        //Call<List<Lorem>> call = service.getLorem();
        service.getLorem().enqueue(new Callback<List<Lorem>>() {
            @Override
            public void onResponse(@NonNull Call<List<Lorem>> call,@NonNull Response<List<Lorem>> response) {
                if(response.body()==null) return;

                dataArrayList = response.body();
                //List<Lorem> results = response.body().results;
                loremAdapter=new LoremAdapter(view.getContext(),dataArrayList);
                recyclerView.setAdapter(loremAdapter);
            }

            @Override
            public void onFailure(Call<List<Lorem>> call, Throwable t) {
                Toast.makeText (view.getContext (), t.getMessage (), Toast.LENGTH_LONG).show ();
                Log.e("ERROR",t.getMessage());
            }
        });
    }
}

class LoremAdapter extends RecyclerView.Adapter<LoremAdapter.LoremViewHolder>{
    private Context context;
    private List<Lorem> dataList;
    //private final List<Lorem> data;

    //LoremAdapter(List<Lorem> d){data = d;}
    public LoremAdapter(Context context, List<Lorem> dataList){
        this.context=context;
        this.dataList=dataList;
    }

    @NonNull
    @Override
    public LoremViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
        View view = inflater.inflate (R.layout.list_item, parent, false);

        return new LoremViewHolder(view);
    }

    public void onBindViewHolder(@NonNull LoremViewHolder holder, int position){
        Lorem lorem = dataList.get(position);
        holder.setData(lorem.getAuthor(), lorem.getUrl(), lorem.getDownload_url());
    }

    @Override
    public int getItemCount(){return dataList.size();}

    static class LoremViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPicture;
        TextView tvUrl, tvAuthor;

        LoremViewHolder(View itemView){
            super(itemView);

            ivPicture = itemView.findViewById(R.id.ivPicture);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvUrl = itemView.findViewById(R.id.tvUrl);
        }

        void setData (String data1, String data2, String imageURL){
            tvUrl.setText(data1);
            tvAuthor.setText(data2);

            if(imageURL.startsWith("http://")){
                imageURL = imageURL.replaceFirst("http://", "https://");
            }

            Picasso.get ()
                    .load (imageURL)
                    .resize (200, 200)
                    .centerCrop ()
                    .placeholder (R.drawable.placeholder)
                    .into (ivPicture);

        }

    }
}