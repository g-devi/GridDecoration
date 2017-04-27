package com.github.g_devi.griddecoration.sample;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.g_devi.griddecoration.GridMarginDecoration;
import com.github.g_devi.griddecoration.sample.databinding.ActivityMainBinding;
import com.github.g_devi.griddecoration.sample.databinding.ViewRecyclerCardItemBinding;
import com.github.g_devi.griddecoration.sample.databinding.ViewRecyclerFullWidthItemBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int spanCount;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        spanCount = getResources().getInteger(R.integer.span_count);

        adapter = new Adapter(this);
        MySpanSizeLookup ssl = new MySpanSizeLookup(adapter);
        GridLayoutManager lm = new GridLayoutManager(this, spanCount);
        lm.setSpanSizeLookup(ssl);

        binding.recycler.setLayoutManager(lm);
        binding.recycler.setAdapter(adapter);

        int visibleMargin = getResources().getDimensionPixelSize(R.dimen.recycler_grid_margin);
        int contentMargin = getResources().getDimensionPixelSize(R.dimen.card_margin);
        GridMarginDecoration.set(binding.recycler, visibleMargin, contentMargin);

        adapter.addAllNotify(createData());
    }

    private List<Data> createData() {
        List<Data> list = new ArrayList<>();
        list.add(new Data(spanCount));
        for (int i = 0; i < spanCount; i++) {
            list.add(new Data());
        }
        for (int i = 0; i < spanCount; i++) {
            list.add(new Data());
        }
        for (int i = 0; i < 2; i++) {
            list.add(new Data());
        }
        list.add(new Data(spanCount));
        return list;
    }

    public static class Data {

        private final int spanSize;
        private String text;
        private int viewType;

        public Data(int spanSize) {
            this.spanSize = spanSize;
            viewType = spanSize;
        }

        public Data() {
            this(1);
        }

        public int getSpanSize() {
            return spanSize;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getViewType() {
            return viewType;
        }
    }

    static abstract class HolderBase extends RecyclerView.ViewHolder {

        public HolderBase(View itemView) {
            super(itemView);
        }

        public abstract void setData(Data data);
    }

    static class CardHolder extends HolderBase {

        private final ViewRecyclerCardItemBinding binding;

        public static CardHolder newInstance(Context context, ViewGroup parent) {
            return new CardHolder(ViewRecyclerCardItemBinding.inflate(
                    LayoutInflater.from(context), parent, false)
                    .getRoot());
        }

        private CardHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        @Override
        public void setData(Data data) {
            binding.setData(data);
            binding.executePendingBindings();
        }
    }

    static class FullHolder extends HolderBase {

        private final ViewRecyclerFullWidthItemBinding binding;

        public static FullHolder newInstance(Context context, ViewGroup parent) {
            return new FullHolder(
                    ViewRecyclerFullWidthItemBinding.inflate(LayoutInflater.from(context), parent,
                            false).getRoot());
        }

        private FullHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        @Override
        public void setData(Data data) {
            binding.setData(data);
            binding.executePendingBindings();
        }
    }

    static class Adapter extends RecyclerView.Adapter<HolderBase> {

        private final List<Data> list;
        private Context context;

        public Adapter(Context context, List<Data> list) {
            this.context = context;
            this.list = list;
        }

        public Adapter(Context context) {
            this(context, new ArrayList<Data>());
        }

        @Override
        public HolderBase onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case 4:
                    return FullHolder.newInstance(context, parent);
                default:
                    return CardHolder.newInstance(context, parent);
            }
        }

        @Override
        public void onBindViewHolder(HolderBase holder, int position) {
            Data data = getItem(position);
            data.setText("pos(" + position + ")");
            holder.setData(data);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).getViewType();
        }

        public Data getItem(int position) {
            return list.get(position);
        }

        public int getSpanSize(int position) {
            return getItem(position).getSpanSize();
        }

        public void addNotify(Data data) {
            int position = list.size();
            list.add(data);
            notifyItemInserted(position);
        }

        public void addAllNotify(Collection<Data> collection) {
            int positionStart = list.size();
            int itemCount = collection.size();
            list.addAll(collection);
            notifyItemRangeInserted(positionStart, itemCount);
        }
    }

    static class MySpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

        private final Adapter adapter;

        public MySpanSizeLookup(Adapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getSpanSize(int position) {
            return adapter.getSpanSize(position);
        }
    }
}
