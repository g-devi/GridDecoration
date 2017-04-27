package com.github.g_devi.griddecoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridMarginDecoration extends RecyclerView.ItemDecoration {

    private MarginTarget marginTarget = new MarginTarget();
    private int recyclerSidePadding;
    private int vPadding;
    private int hPadding;

    public static GridMarginDecoration set(RecyclerView recycler, int visibleMargin) {
        GridMarginDecoration decoration = new GridMarginDecoration(visibleMargin, visibleMargin,
                0, 0);
        decoration.setRecyclerAttr(recycler);
        return decoration;
    }

    public static GridMarginDecoration set(RecyclerView recycler, int visibleMargin,
            int contentMargin) {
        GridMarginDecoration decoration = new GridMarginDecoration(visibleMargin, visibleMargin,
                contentMargin, contentMargin);
        decoration.setRecyclerAttr(recycler);
        return decoration;
    }

    public static GridMarginDecoration setVH(RecyclerView recycler, int verticalVisibleMargin,
            int horizontalVisibleMargin) {
        GridMarginDecoration decoration = new GridMarginDecoration(verticalVisibleMargin,
                horizontalVisibleMargin, 0, 0);
        decoration.setRecyclerAttr(recycler);
        return decoration;
    }

    public static GridMarginDecoration setVH(RecyclerView recycler,
            int verticalVisibleMargin, int horizontalVisibleMargin,
            int verticalContentMargin, int horizontalContentMargin) {
        GridMarginDecoration decoration = new GridMarginDecoration(verticalVisibleMargin,
                horizontalVisibleMargin, verticalContentMargin, horizontalContentMargin);
        decoration.setRecyclerAttr(recycler);
        return decoration;
    }

    private void setRecyclerAttr(RecyclerView recycler) {
        recycler.addItemDecoration(this);
        recycler.setClipToPadding(false);
        recycler.setPadding(
                getRecyclerSidePadding(),
                recycler.getPaddingTop(),
                getRecyclerSidePadding(),
                recycler.getPaddingBottom()
        );
    }

    private GridMarginDecoration(
            int verticalVisibleMargin, int horizontalVisibleMargin,
            int verticalContentMargin, int horizontalContentMargin) {
        float vVisibleMarginFloat = (float) verticalVisibleMargin;
        float hVisibleMarginFloat = (float) horizontalVisibleMargin;
        float vContentMarginFloat = (float) verticalContentMargin;
        float hContentMarginFloat = (float) horizontalContentMargin;

        vPadding = (int) (vVisibleMarginFloat - (vContentMarginFloat * 2)) / 2;
        hPadding = (int) (hVisibleMarginFloat - (hContentMarginFloat * 2)) / 2;

        recyclerSidePadding = (int) (hVisibleMarginFloat - hContentMarginFloat - hPadding);
    }

    public int getRecyclerSidePadding() {
        return recyclerSidePadding;
    }

    public void setMarginTarget(MarginTarget marginTarget) {
        this.marginTarget = marginTarget;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
        if (!(parent.getLayoutManager() instanceof GridLayoutManager)) return;

        if (marginTarget == null) return;

        GridLayoutManager lm = (GridLayoutManager) parent.getLayoutManager();
        GridLayoutManager.SpanSizeLookup spanSizeLookup = lm.getSpanSizeLookup();
        if (spanSizeLookup == null) return;

        int adapterPosition = parent.getChildAdapterPosition(view);
        if (RecyclerView.NO_POSITION == adapterPosition) return;

        int spanCount = lm.getSpanCount();

        if (!marginTarget.isTarget(view, parent, spanSizeLookup, adapterPosition, spanCount)) {
            return;
        }

        int left;
        int top;
        int right;
        int bottom;

        if (marginTarget.isFullWidth(view, parent, spanSizeLookup, adapterPosition, spanCount)) {
            left = -1 * recyclerSidePadding;
            right = -1 * recyclerSidePadding;
        } else {
            left = hPadding;
            right = hPadding;
        }

        top = marginTarget.getTopMargin(view, parent, spanSizeLookup, adapterPosition,
                spanCount, vPadding, recyclerSidePadding);

        bottom = vPadding;

        outRect.set(left, top, right, bottom);
    }

    public class MarginTarget {

        public boolean isTarget(
                View view, RecyclerView parent,
                GridLayoutManager.SpanSizeLookup spanSizeLookup,
                int adapterPosition, int spanCount) {
            return true;
        }

        public boolean isFullWidth(
                View view, RecyclerView parent,
                GridLayoutManager.SpanSizeLookup spanSizeLookup,
                int adapterPosition, int spanCount) {
            return spanSizeLookup.getSpanSize(adapterPosition) == spanCount;
        }

        public int getTopMargin(
                View view, RecyclerView parent,
                GridLayoutManager.SpanSizeLookup spanSizeLookup,
                int adapterPosition, int spanCount, int defValue, int recyclerSidePadding) {
            if (adapterPosition == 0) {
                if (isFullWidth(view, parent, spanSizeLookup, adapterPosition, spanCount)) {
                    return 0;
                } else {
                    return defValue + recyclerSidePadding;
                }
            } else {
                return defValue;
            }
        }
    }
}
