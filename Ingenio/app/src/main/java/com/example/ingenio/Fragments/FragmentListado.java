package com.example.ingenio.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingenio.Interfaces.OnDegreeSelected;
import com.example.ingenio.R;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class FragmentListado extends Fragment {
    RecyclerView recyclerView;
    private MyAdapter ingenioAdapter;
    private OnDegreeSelected listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragments_ui, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity == null) return;

        String [] carrerasArray = activity.getResources().getStringArray(R.array.carreras);
        List<String> carreras = Arrays.asList(carrerasArray);
        String [] urlArray = activity.getResources().getStringArray(R.array.URL_Imagenes);
        List<String> uerl = Arrays.asList(urlArray);

        recyclerView = view.findViewById(R.id.myList);
        recyclerView.addItemDecoration (new DividerItemDecoration(view.getContext (), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator (new DefaultItemAnimator());
        recyclerView.setLayoutManager (new GridLayoutManager(getActivity(),2));

        ingenioAdapter = new MyAdapter(view.getContext(),carreras,uerl, this.listener);
        recyclerView.setAdapter(ingenioAdapter);
    }

    public void setOnDegreeSelectedListener(OnDegreeSelected listener) {
        this.listener = listener;
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MovViewHolder>{
    private Context context;
    private List<String> carreer;
    private List<String> url;
    private OnDegreeSelected listener;

    MyAdapter(Context context, List<String> carreer, List<String> url, OnDegreeSelected listener){
        this.context = context;
        this.carreer = carreer;
        this.url = url;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item,parent, false);

        return new MovViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovViewHolder holder, int position) {
        holder.setData(carreer.get(position),url.get(position));
        holder.itemView.setOnClickListener(view -> listener.degreeSelected(position));
    }

    @Override
    public int getItemCount() {
        return carreer.size();
    }

    class MovViewHolder extends RecyclerView.ViewHolder{
        private TextView carrera;
        private ImageView tvImage1;

        MovViewHolder(@NonNull View itemView){
            super(itemView);
            tvImage1 = itemView.findViewById(R.id.ivPicture);
            carrera = itemView.findViewById(R.id.tvcarrera);
        }

        void setData(String data1, @NonNull String imageURL){
            carrera.setText(data1);

            if(imageURL.startsWith("http://")){
                imageURL = imageURL.replaceFirst("http://","https://");
            }

            Picasso.get()
                    .load(imageURL)
                    .resize(200,200)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(tvImage1);
        }

    }

}
