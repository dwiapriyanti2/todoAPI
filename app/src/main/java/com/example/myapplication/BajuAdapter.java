package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.Baju;

import java.util.List;

public class BajuAdapter extends RecyclerView.Adapter<BajuAdapter.BajuViewHolder> {

    private List<Baju> bajuList;
    private BajuClickListener bajuClickListener;

    public BajuAdapter(List<Baju> bajuList, BajuClickListener bajuClickListener) {
        this.bajuList = bajuList;
        this.bajuClickListener = bajuClickListener;
    }

    @NonNull
    @Override
    public BajuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_baju, parent, false);
        return new BajuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BajuViewHolder holder, int position) {
        Baju baju = bajuList.get(position);
        holder.bind(baju);
    }

    @Override
    public int getItemCount() {
        return bajuList.size();
    }

    public interface BajuClickListener {
        void onBajuClick(int position);
    }

    public class BajuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtNama, txtJenis, txtJumlah;

        public BajuViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNama = itemView.findViewById(R.id.textViewNama);
            txtJenis = itemView.findViewById(R.id.textViewjenis);
            txtJumlah = itemView.findViewById(R.id.textViewjumlah);

            itemView.setOnClickListener(this);
        }

        public void bind(Baju baju) {
            txtNama.setText(baju.getNama());
            txtJenis.setText(baju.getJenis());
            txtJumlah.setText(String.valueOf(baju.getJumlah()));
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                bajuClickListener.onBajuClick(position);
            }
        }
    }
}
