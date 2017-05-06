package adapters;

/**
 * Created by y.baidiuk on 06/05/2017.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diarize.R;

import java.text.SimpleDateFormat;
import java.util.List;

import entitys.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private List<Item> ItemsList;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, year, text;

        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.dateForItemRow);
            text = (TextView) view.findViewById(R.id.textForItemRow);
        }
    }


    public ItemAdapter(List<Item> ItemsList) {
        this.ItemsList = ItemsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item Item = ItemsList.get(position);

        holder.date.setText(sdf.format(Item.getDate()));
        holder.text.setText(Item.getText());
    }

    @Override
    public int getItemCount() {
        return ItemsList.size();
    }
}