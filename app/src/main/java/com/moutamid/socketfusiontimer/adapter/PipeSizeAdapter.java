package com.moutamid.socketfusiontimer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.moutamid.socketfusiontimer.R;
import com.moutamid.socketfusiontimer.model.PipeSize;
import java.util.List;

public class PipeSizeAdapter extends ArrayAdapter<PipeSize> {

    public PipeSizeAdapter(Context context, List<PipeSize> pipeSizes) {
        super(context, 0, pipeSizes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    private View createView(int position, View convertView, ViewGroup parent) {
        PipeSize pipeSize = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        View viewColor = convertView.findViewById(R.id.viewColor);
        TextView tvPipeSize = convertView.findViewById(R.id.tvPipeSize);
        if (pipeSize != null) {

            viewColor.setBackgroundColor(Color.parseColor(pipeSize.getColor()));
            tvPipeSize.setText(pipeSize.getName());
        }

        return convertView;
    }
}

