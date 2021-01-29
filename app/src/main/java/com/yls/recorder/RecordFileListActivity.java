package com.yls.recorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yls.Player;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecordFileListActivity extends AppCompatActivity {

    private static final String TAG = "RecordFileListActivity";
    private List<File> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_file_list);

        initData();

        initViews();
    }

    private void initData() {
        File[] files = Utils.getRecorderFileFoler(this).listFiles();
        if (files != null && files.length > 0) {
            lists = Arrays.asList(files);
            Log.i(TAG, "lists size:" + lists.size());
        } // TODO empty view
    }

    private void initViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecycleViewAdapter adapter = new RecycleViewAdapter(this, lists);
        //设置分割线使用的divider
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    // TODO why static
    class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

        private List<File> lists;
        private Context context;

        public RecycleViewAdapter(Context context, List<File> lists) {
            this.context = context;
            this.lists = lists;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.record_file_item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Log.i(TAG, "set text:" + lists.get(position).getName());
            holder.textView.setText(lists.get(position).getName());
            holder.itemView.setOnClickListener(v -> {
                // TODO static
                RecordFileListActivity.this.gotoPlayActivity(lists.get(position));
            });
        }

        @Override
        public int getItemCount() {
            return lists.size();
        }

        // TODO why static
        public class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView textView;
            public final View itemView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                textView = (TextView) itemView.findViewById(R.id.item_name);
            }
        }
    }

    private void gotoPlayActivity(File file) {
        Log.i("mr", "gotoPlayActivity, file:" + file.getAbsolutePath());
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra(PlayActivity.EXTRA_KEY_FILE, file);
        startActivity(intent);
    }
}