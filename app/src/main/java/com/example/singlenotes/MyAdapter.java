package com.example.singlenotes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public MyAdapter(Context context, RealmResults<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    Context context;
    RealmResults<Note> notesList;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Note note = notesList.get(position);
        holder.title_output.setText(note.getTitle());
        holder.description_output.setText(note.getDescription());
        String formatedTime = DateFormat.getDateTimeInstance().format(note.createdTime);
        holder.time_output.setText(formatedTime);
        holder.title_output.setSelected(true);
        holder.time_output.setSelected(true);
        int color_code = getRandomColor();
        holder.simple_card.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));
        holder.simple_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Update.class);
                intent.putExtra("titleName", note.getTitle());
                intent.putExtra("description", note.getDescription());

                context.startActivity(intent);

            }
        });
        holder.simple_card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu menu = new PopupMenu(context, v);
                menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.delete:
                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                note.deleteFromRealm();
                                realm.commitTransaction();


                            case R.id.copy:

                                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData data = (ClipData) ClipData.newPlainText("text", notesList.get(position).getDescription());
                                clipboardManager.setPrimaryClip(data);
                                Toast.makeText(context, "Copy ", Toast.LENGTH_SHORT).show();

                                return true;

                            case R.id.share:
                                Intent share = new Intent(Intent.ACTION_SEND);
                                share.setType("text/plane");
                                share.putExtra(Intent.EXTRA_TEXT, notesList.get(position).getDescription());
                                context.startActivity(share);
                                return true;

                        }

                        return true;
                    }
                });
                menu.show();

                return false;
            }
        });
//        holder.simple_card.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                PopupMenu menu =new PopupMenu(context,v);
//                menu.getMenuInflater().inflate(R.menu.popup_menu,menu.getMenu());
//                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                         switch (item.getItemId()){
//                             case R.id.delete:
//                                 Realm realm =Realm.getDefaultInstance();
//                                 realm.beginTransaction();
//                                 note.deleteFromRealm();
//                                 realm.commitTransaction();
//                                 Toast.makeText(context,"Note deleted",Toast.LENGTH_SHORT).show(); }
//
//
//
//                        return true;
//                    }
//                });
//                menu.show();
//
//                return true;
//            }
//        });

    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
         colorCode.add(R.color.color5);
//        colorCode.add(R.color.color6);
//        colorCode.add(R.color.color7);
//        colorCode.add(R.color.color8);
//        colorCode.add(R.color.color9);
//        colorCode.add(R.color.color10);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title_output;
        TextView description_output;
        TextView time_output;
        CardView simple_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_output = itemView.findViewById(R.id.txt_title);
            description_output = itemView.findViewById(R.id.txt_description);
            time_output = itemView.findViewById(R.id.time_date);
            simple_card = itemView.findViewById(R.id.simple_card);

        }
    }
}
