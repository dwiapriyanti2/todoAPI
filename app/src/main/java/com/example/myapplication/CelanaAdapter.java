package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.Celana;

import java.util.List;

public class CelanaAdapter extends RecyclerView.Adapter<CelanaAdapter.CelanaViewHolder> {

    private List<Celana> celanaList;
    private CelanaClickListener celanaClickListener;

    public CelanaAdapter(List<Celana> celanaList, CelanaClickListener celanaClickListener) {
        this.celanaList = celanaList;
        this.celanaClickListener = celanaClickListener;
    }

    @NonNull
    @Override
    public CelanaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_celana, parent, false);
        return new CelanaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CelanaViewHolder holder, int position) {
        Celana celana = celanaList.get(position);
        holder.bind(celana);
    }

    @Override
    public int getItemCount() {
        return celanaList.size();
    }

    public interface CelanaClickListener {
        void onCelanaClick(int position);
    }

    public class CelanaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtNama, txtJenis, txtJumlah;

        public CelanaViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNama = itemView.findViewById(R.id.textViewNama);
            txtJenis = itemView.findViewById(R.id.textViewjenis);
            txtJumlah = itemView.findViewById(R.id.textViewjumlah);

            itemView.setOnClickListener(this);
        }

        public void bind(Celana celana) {
            txtNama.setText(celana.getNama());
            txtJenis.setText(celana.getJenis());
            txtJumlah.setText(String.valueOf(celana.getJumlah()));
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                celanaClickListener.onCelanaClick(position);
            }
        }
    }
}
