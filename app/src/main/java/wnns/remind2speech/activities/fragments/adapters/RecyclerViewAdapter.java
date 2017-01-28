package wnns.remind2speech.activities.fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import wnns.remind2speech.R;
import wnns.remind2speech.RemindAlarmManager;

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    ArrayList<RecyclerViewItem> recyclerViewItems = new ArrayList<>();
    RecyclerView recyclerView;

    private class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView description;
        public TextView date;

        public Button remove;

        public ViewHolder(View itemView) {

            super(itemView);

            title = (TextView)itemView.findViewById(R.id.tvTitle);
            description = (TextView)itemView.findViewById(R.id.tvDesc);
            date = (TextView)itemView.findViewById(R.id.tvDate);
            remove = (Button)itemView.findViewById(R.id.buttonMainAlarmRemove);
        }
    }

    public RecyclerViewAdapter(ArrayList<RecyclerViewItem> _arrayList, RecyclerView _recyclerView){

        recyclerViewItems = _arrayList;
        recyclerView = _recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_cardview, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final RecyclerViewItem recyclerViewItem = recyclerViewItems.get(position);
        ((ViewHolder) holder).title.setText(recyclerViewItem.getTitle());
        ((ViewHolder) holder).description.setText(recyclerViewItem.getDescription());

        String alarmDateText = recyclerViewItem.getDate();
        ((ViewHolder) holder).date.setText(alarmDateText.substring(0, alarmDateText.length() -3));

        String alarmDateString = recyclerViewItem.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date alarmDate = null;
        Date dateNow = new Date();

        try {
            alarmDate = simpleDateFormat.parse(alarmDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dateNow.after(alarmDate)){

            ((ViewHolder) holder).title.setAlpha(0.5f);
            ((ViewHolder) holder).description.setAlpha(0.5f);
            ((ViewHolder) holder).date.setAlpha(0.5f);
        }
        else{

            ((ViewHolder) holder).title.setAlpha(1f);
            ((ViewHolder) holder).description.setAlpha(1f);
            ((ViewHolder) holder).date.setAlpha(1f);
        }

        ((ViewHolder) holder).remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                RemindAlarmManager remindAlarmManager = new RemindAlarmManager(context);
                remindAlarmManager.removeAlarm(recyclerViewItem.getID());

                recyclerViewItems.remove(position);
                recyclerView.getAdapter().notifyItemRemoved(position);
                recyclerView.getAdapter().notifyItemRangeChanged(0, getItemCount());
            }
        });

    }

    @Override
    public int getItemCount() {

        return recyclerViewItems.size();
    }
}
