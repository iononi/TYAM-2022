package com.example.lorempicsum;

import android.media.Image;
import android.os.Bundle;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragments_ui,container,false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        FragmentActivity activity = getActivity ();
        if (activity == null) return;

        recyclerView = view.findViewById(R.id.rvElements);
        recyclerView.addItemDecoration (new DividerItemDecoration(view.getContext (), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator (new DefaultItemAnimator());
        recyclerView.setLayoutManager (new LinearLayoutManager(view.getContext ()));
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext (),
         //       2, GridLayoutManager.VERTICAL, false));

        SWApiService service = Utils.createService();

        service.getLorem().enqueue(new Callback<LoremHeader>() {
            @Override
            public void onResponse(@NonNull Call<LoremHeader> call,@NonNull Response<LoremHeader> response) {
                if(response.body()==null) return;

                List<Lorem> results = response.body().results;
                recyclerView.setAdapter(new LoremAdapter(results));
            }

            @Override
            public void onFailure(Call<LoremHeader> call, Throwable t) {
                Toast.makeText (view.getContext (), t.getMessage (), Toast.LENGTH_LONG).show ();
            }
        });
    }
}

class LoremAdapter extends RecyclerView.Adapter<LoremAdapter.LoremViewHolder>{
    private final List<Lorem> data;

    LoremAdapter(List<Lorem> d){data = d;}

    @NonNull
    @Override
    public LoremViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
        View view = inflater.inflate (R.layout.list_item, parent, false);

        return new LoremViewHolder(view);
    }

    public void onBindViewHolder(@NonNull LoremViewHolder holder, int position){
        Lorem lorem = data.get(position);
        holder.setData(lorem.Author, lorem.URL, lorem.UrlDownload);
    }

    @Override
    public int getItemCount(){return data.size();}

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