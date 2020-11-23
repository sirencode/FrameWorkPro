package com.syh.framework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syh.framework.util.BaseActivity;
import com.syh.framework.view.ScrollMenu;

import java.util.ArrayList;
import java.util.List;

public class ScrollMenuActivity extends BaseActivity {
    private RecyclerView rvVertical, rvHorizontal;
    private ScrollView scrollView;
    private List<String> datas;
    private ScrollMenu scrollMenu;
    private MyAdapter adapter;
    private CheckedTextView ctvH, ctvV;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_menu);
        init();
        inflateData();
        addListener();
    }


    private void init() {
        scrollMenu = (ScrollMenu) findViewById(R.id.scrollMenu);
        rvVertical = (RecyclerView) findViewById(R.id.rvVertical);
        rvHorizontal = (RecyclerView) findViewById(R.id.rvHorizontal);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        ctvH = (CheckedTextView) findViewById(R.id.ctvH);
        ctvV = (CheckedTextView) findViewById(R.id.ctvV);
        rvVertical.setLayoutManager(new LinearLayoutManager(this));
        rvHorizontal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void inflateData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(index + "-item : " + i);
        }
        adapter = new MyAdapter(datas);
        rvHorizontal.setAdapter(adapter);
        rvVertical.setAdapter(adapter);
    }

    private int index = 0;
    private void changeData(boolean isTop) {
        datas.clear();
        index += isTop ? -1 : 1;
        for (int i = 0; i < 20; i++) {
            datas.add(index + "-tem : " + i);
        }
        adapter.notifyDataSetChanged();
    }

    private void addListener() {
        scrollMenu.setOnScrollCompleteListener(new ScrollMenu.OnScrollCompleteListener() {
            @Override
            public void completeTop() {
                Toast.makeText(ScrollMenuActivity.this, "↑↑上滑切换↑↑", Toast.LENGTH_SHORT).show();
                changeData(true);
            }

            @Override
            public void completeBottom() {
                Toast.makeText(ScrollMenuActivity.this, "↓↓下滑切换↓↓", Toast.LENGTH_SHORT).show();
                changeData(false);
            }
        });

        adapter.setOnItemClick(new MyAdapter.OnItemClick() {
            @Override
            public void onPos(int position) {
                Toast.makeText(ScrollMenuActivity.this, "click item = " + position, Toast.LENGTH_SHORT).show();
            }
        });

        ctvH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctvH.toggle();
                ctvH.setText(ctvH.isChecked() ? "横向滑动开" : "横向滑动关");
                scrollMenu.setOpenHorizontalSlide(ctvH.isChecked());
            }
        });

        ctvV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctvV.toggle();
                ctvV.setText(ctvV.isChecked() ? "纵向滑动开" : "纵向滑动关");
                scrollMenu.setOpenVerticalSlide(ctvV.isChecked());
            }
        });
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
        private List<String> datas;
        public MyAdapter(List<String> datas) {
            this.datas = datas;
        }
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, final int position) {
            holder.tv.setText(datas.get(position));
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick != null) {
                        onItemClick.onPos(position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public static class MyHolder extends RecyclerView.ViewHolder {
            TextView tv;
            public MyHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView;
            }
        }

        public interface OnItemClick {
            void onPos(int position);
        }

        private OnItemClick onItemClick;
        public void setOnItemClick(OnItemClick onItemClick) {
            this.onItemClick = onItemClick;
        }
    }

}
