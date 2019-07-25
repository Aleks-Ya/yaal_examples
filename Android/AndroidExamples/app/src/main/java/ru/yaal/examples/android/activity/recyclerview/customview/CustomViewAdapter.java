package ru.yaal.examples.android.activity.recyclerview.customview;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.yaal.examples.android.R;

import static java.lang.String.format;

public class CustomViewAdapter extends RecyclerView.Adapter<CustomViewAdapter.CustomViewViewHolder> {
    private String[] dataSet;

    static class CustomViewViewHolder extends RecyclerView.ViewHolder {
        CustomViewViewHolder(View v) {
            super(v);
        }
    }

    CustomViewAdapter(String[] myDataset) {
        dataSet = myDataset;
    }

    @NonNull
    @Override
    public CustomViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflated = inflater.inflate(R.layout.view_my, parent, false);
        return new CustomViewViewHolder(inflated);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewViewHolder holder, int position) {
        ViewGroup viewGroup = (ViewGroup) holder.itemView;


        Resources resources = viewGroup.getContext().getResources();
        String hiText = resources.getString(R.string.custom_view_recycler_view_hi);
        String byeText = resources.getString(R.string.custom_view_recycler_view_bye);

        String name = dataSet[position];

        TextView textView1 = viewGroup.findViewById(R.id.textView1);
        textView1.setText(format(hiText, name));

        TextView textView2 = viewGroup.findViewById(R.id.textView2);
        textView2.setText(format(byeText, name));
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }
}
