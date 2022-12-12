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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingenio.R;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class DetallesFragment extends Fragment {
    private int position;
    private MiAdaptador DetallesAdapter;
    RecyclerView recyclerView;

    public DetallesFragment() { super(); }

    public DetallesFragment(int position){
        super();
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_detalles,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentActivity activity = getActivity();
        if (activity == null) return;


        String [] carrerasArray = activity.getResources().getStringArray(R.array.carrerasMod);

        //Con esto sabemos de que carrera estamos hablando
        String Carrera = carrerasArray[position];

        String [] TituloCarreArray;
        List<String> TituloCarre;
        String [] tituloArray;
        List<String> titulo;
        String [] descripcionArray;
        List<String> descripcion;

        if(Carrera.equals("Ing.Informatica")){
            TituloCarreArray = activity.getResources().getStringArray(R.array.Titulo_Informatica);
            TituloCarre = Arrays.asList(TituloCarreArray);
            tituloArray = activity.getResources().getStringArray(R.array.Ing_Informatica);
            titulo = Arrays.asList(tituloArray);
            descripcionArray = activity.getResources().getStringArray(R.array.Ing_Informatica1);
            descripcion = Arrays.asList(descripcionArray);

            recyclerView = view.findViewById(R.id.miLista);
            recyclerView.addItemDecoration (new DividerItemDecoration(view.getContext (), DividerItemDecoration.VERTICAL));
            recyclerView.setItemAnimator (new DefaultItemAnimator());
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext ()));

            DetallesAdapter = new MiAdaptador(view.getContext(), titulo, descripcion, TituloCarre);
            recyclerView.setAdapter(DetallesAdapter);
        }
        if(Carrera.equals("Ing.Industrial")){
            TituloCarreArray = activity.getResources().getStringArray(R.array.Titulo_Industrial);
            TituloCarre = Arrays.asList(TituloCarreArray);
            tituloArray = activity.getResources().getStringArray(R.array.Ing_Industrial);
            titulo = Arrays.asList(tituloArray);
            descripcionArray = activity.getResources().getStringArray(R.array.Ing_Industrial1);
            descripcion = Arrays.asList(descripcionArray);

            recyclerView = view.findViewById(R.id.miLista);
            recyclerView.addItemDecoration (new DividerItemDecoration(view.getContext (), DividerItemDecoration.VERTICAL));
            recyclerView.setItemAnimator (new DefaultItemAnimator());
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext ()));

            DetallesAdapter = new MiAdaptador(view.getContext(), titulo, descripcion, TituloCarre);
            recyclerView.setAdapter(DetallesAdapter);
        }
        if(Carrera.equals("Ing.Quimica")){
            TituloCarreArray = activity.getResources().getStringArray(R.array.Titulo_Quimica);
            TituloCarre = Arrays.asList(TituloCarreArray);
            tituloArray = activity.getResources().getStringArray(R.array.Ing_Quimica);
            titulo = Arrays.asList(tituloArray);
            descripcionArray = activity.getResources().getStringArray(R.array.Ing_Quimica1);
            descripcion = Arrays.asList(descripcionArray);

            recyclerView = view.findViewById(R.id.miLista);
            recyclerView.addItemDecoration (new DividerItemDecoration(view.getContext (), DividerItemDecoration.VERTICAL));
            recyclerView.setItemAnimator (new DefaultItemAnimator());
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext ()));

            DetallesAdapter = new MiAdaptador(view.getContext(), titulo, descripcion, TituloCarre);
            recyclerView.setAdapter(DetallesAdapter);
        }
        if(Carrera.equals("Ing.ElectricayElectronica")){
            TituloCarreArray = activity.getResources().getStringArray(R.array.Titulo_ElectricayElectronicca);
            TituloCarre = Arrays.asList(TituloCarreArray);
            tituloArray = activity.getResources().getStringArray(R.array.Ing_ElectricayElectronica);
            titulo = Arrays.asList(tituloArray);
            descripcionArray = activity.getResources().getStringArray(R.array.Ing_ElectricayElectronica1);
            descripcion = Arrays.asList(descripcionArray);

            recyclerView = view.findViewById(R.id.miLista);
            recyclerView.addItemDecoration (new DividerItemDecoration(view.getContext (), DividerItemDecoration.VERTICAL));
            recyclerView.setItemAnimator (new DefaultItemAnimator());
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext ()));

            DetallesAdapter = new MiAdaptador(view.getContext(), titulo, descripcion, TituloCarre);
            recyclerView.setAdapter(DetallesAdapter);
        }
        if(Carrera.equals("Ing.Mecatronica")){
            TituloCarreArray = activity.getResources().getStringArray(R.array.Titulo_Mecatronica);
            TituloCarre = Arrays.asList(TituloCarreArray);
            tituloArray = activity.getResources().getStringArray(R.array.Ing_Mecatronica);
            titulo = Arrays.asList(tituloArray);
            descripcionArray = activity.getResources().getStringArray(R.array.Ing_Mecatronica1);
            descripcion = Arrays.asList(descripcionArray);

            recyclerView = view.findViewById(R.id.miLista);
            recyclerView.addItemDecoration (new DividerItemDecoration(view.getContext (), DividerItemDecoration.VERTICAL));
            recyclerView.setItemAnimator (new DefaultItemAnimator());
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext ()));

            DetallesAdapter = new MiAdaptador(view.getContext(), titulo, descripcion, TituloCarre);
            recyclerView.setAdapter(DetallesAdapter);
        }
        if(Carrera.equals("Ing.Civil")){
            TituloCarreArray = activity.getResources().getStringArray(R.array.Titulo_Civil);
            TituloCarre = Arrays.asList(TituloCarreArray);
            tituloArray = activity.getResources().getStringArray(R.array.Ing_Civil);
            titulo = Arrays.asList(tituloArray);
            descripcionArray = activity.getResources().getStringArray(R.array.Ing_Civil1);
            descripcion = Arrays.asList(descripcionArray);

            recyclerView = view.findViewById(R.id.miLista);
            recyclerView.addItemDecoration (new DividerItemDecoration(view.getContext (), DividerItemDecoration.VERTICAL));
            recyclerView.setItemAnimator (new DefaultItemAnimator());
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext ()));

            DetallesAdapter = new MiAdaptador(view.getContext(), titulo, descripcion, TituloCarre);
            recyclerView.setAdapter(DetallesAdapter);
        }




        /*TextView tvTitle = activity.findViewById(R.id.tvTitle);
        if (tvTitle != null) tvTitle.setText(TituloCarrera [position]);

        ImageView tvImage = activity.findViewById(R.id.image1);
        if (tvImage != null) {
            Picasso.get()
                    .load(Imagen[position])
                    .resize(200,200)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(tvImage);
        }

        TextView tvDetalle = activity.findViewById(R.id.tvDetalle);
        if (tvDetalle != null) tvDetalle.setText(CarreraDetalle [position]);*/

    }
}


class MiAdaptador extends RecyclerView.Adapter<MiAdaptador.MiViewHolder>{
    private Context context;
    private List<String> TituloCarre;
    private List<String> titulo;
    private List<String> descripcion;

    MiAdaptador(Context context, List<String> titulo, List<String> descripcion, List<String> TituloCarre){
        this.context = context;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.TituloCarre = TituloCarre;
    }

    @NonNull
    @Override
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.info_content_ingenio, parent, false);

        return new MiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position) {
        holder.setData(titulo.get(position), descripcion.get(position), TituloCarre.get(position));
    }

    @Override
    public int getItemCount() {
        return titulo.size();
    }

    class MiViewHolder extends RecyclerView.ViewHolder{
        private TextView titulo;
        private TextView descripcion;
        private TextView TituloCarre;

        MiViewHolder(@NonNull View itemView){
            super(itemView);
            titulo = itemView.findViewById(R.id.tvTitle);
            descripcion = itemView.findViewById(R.id.tvDescription);
            TituloCarre = itemView.findViewById(R.id.tvTitulo);
        }

        void setData(String data1, String data2, String data3){
            titulo.setText(data1);
            descripcion.setText(data2);
            TituloCarre.setText(data3);
        }
    }
}
