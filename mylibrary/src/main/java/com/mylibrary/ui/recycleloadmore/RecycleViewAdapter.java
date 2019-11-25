package com.mylibrary.ui.recycleloadmore;

import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by An on 10/14/2015.
 */
public abstract class RecycleViewAdapter<T> extends RecyclerView.Adapter {

    private static final String TAG = "RecycleViewAdapter";

    /**
     * data set
     */
    public List<T> mData;
    /**
     * headers
     */
    public List<Object> mHeaders;
    /**
     * footers
     */
    public List<Object> mFooters;
    /**
     * recycler view mode
     */
    public int mMode;

    /**
     * tool bar height
     */
    //because our toobar is ThemeOverlay, so we should minus toolbar height,
    //you can use AppBarLayout.getMeasuredHeight method to get toobar height.
    protected int mToolBarHeight;

    private RecycleViewInterface.OnItemClickListener mOnItemClickListener;
    private RecycleViewInterface.OnItemLongClickListener mOnItemLongClickListener;
    private RecycleViewInterface.OnEmptyViewClickListener mOnEmptyViewClickListener;
    private RecycleViewInterface.OnErrorViewClickListener mOnErrorViewClickListener;
    private RecycleViewInterface.OnHeaderViewClickListener mOnHeaderViewClickListener;
    private RecycleViewInterface.OnFooterViewClickListener mOnFooterViewClickListener;

    public RecycleViewAdapter(List<T> data) {
        this(data, RecycleViewMode.MODE_DATA, 0);
    }

    public RecycleViewAdapter(List<T> data, int mode) {
        this(data, mode, 0);
    }

    public RecycleViewAdapter(List<T> data, int mode, int toolBarHeight) {
        this(data, null, null, mode, toolBarHeight);
    }

    public RecycleViewAdapter(List<T> data, List<Object> headers, List<Object> footers, int mode, int toolBarHeight) {
        this.mData = null == data ? new ArrayList<T>() : data;
        this.mHeaders = null == headers ? new ArrayList<Object>() : headers;
        this.mFooters = null == footers ? new ArrayList<Object>() : footers;
        this.mMode = mData.isEmpty() ? RecycleViewMode.MODE_EMPTY : mode;
        this.mToolBarHeight = toolBarHeight;
    }

    public void setData(List<T> data) {
        setData(data, RecycleViewMode.MODE_DATA);
    }

    public void setData(List<T> data, int mode) {
        this.mData = null == data ? new ArrayList<T>() : data;
        this.mMode = mData.isEmpty() ? RecycleViewMode.MODE_EMPTY : mode;
        this.notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        if (null == data || data.isEmpty()) {
            return;
        }
        int startPosition = mData.size() + mHeaders.size();
        this.mData.addAll(data);
        this.notifyItemRangeInserted(startPosition, data.size());
    }

    public List<Object> getHeaders() {
        return mHeaders;
    }

    public List<Object> getFooters() {
        return mFooters;
    }

    /**
     * change recycler view display mode
     *
     * @param mode mode
     */
    public void changeMode(int mode) {
        if (mode < 0 || mode > RecycleViewMode.MODE_EMPTY) {
            mode = 0;
        }
        mMode = mode;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == RecycleViewMode.MODE_LOADING) {
            RecyclerView.ViewHolder loadingViewHolder = onCreateLoadingViewHolder(parent);
            //because our toobar is ThemeOverlay, so we should minus toolbar height
            loadingViewHolder.itemView.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight() - mToolBarHeight)
            );
            return loadingViewHolder;
        }
        if (viewType == RecycleViewMode.MODE_ERROR) {
            RecyclerView.ViewHolder errorViewHolder = onCreateErrorViewHolder(parent);
            //because our toobar is ThemeOverlay, so we should minus toolbar height
            errorViewHolder.itemView.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight() - mToolBarHeight)
            );
            errorViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (null != mOnErrorViewClickListener) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mOnErrorViewClickListener.onErrorViewClick(v);
                            }
                        }, 200);
                    }
                }
            });
            return errorViewHolder;
        }
        if (viewType == RecycleViewMode.MODE_EMPTY) {
            RecyclerView.ViewHolder emptyViewHolder = onCreateEmptyViewHolder(parent);
            //because our toobar is ThemeOverlay, so we should minus toolbar height
            emptyViewHolder.itemView.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight() - mToolBarHeight)
            );
            emptyViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (null != mOnEmptyViewClickListener) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mOnEmptyViewClickListener.onEmptyViewClick(v);
                            }
                        }, 200);
                    }
                }
            });
            return emptyViewHolder;
        }
        if (viewType == RecycleViewMode.MODE_HEADER_VIEW) {
            RecyclerView.ViewHolder headerViewHolder = onCreateHeaderViewHolder(parent);
            headerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (null != mOnHeaderViewClickListener) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mOnHeaderViewClickListener.onHeaderViewClick(v, v.getTag());
                            }
                        }, 200);
                    }
                }
            });
            return headerViewHolder;
        }
        if (viewType == RecycleViewMode.MODE_FOOTER_VIEW) {
            RecyclerView.ViewHolder footerViewHolder = onCreateFooterViewHolder(parent);
            footerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (null != mOnFooterViewClickListener) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mOnFooterViewClickListener.onFooterViewClick(v, v.getTag());
                            }
                        }, 200);
                    }
                }
            });
            return footerViewHolder;
        }
        RecyclerView.ViewHolder dataViewHolder = onCreateDataViewHolder(parent);
        dataViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (null != mOnItemClickListener) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mOnItemClickListener.onItemClick(v, v.getTag());
                        }
                    }, 200);
                }
            }
        });
        dataViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                if (null != mOnItemLongClickListener) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mOnItemLongClickListener.onItemLongClick(v, v.getTag());
                        }
                    }, 200);
                    return true;
                }
                return false;
            }
        });
        return dataViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mMode == RecycleViewMode.MODE_LOADING) {
            onBindLoadingViewHolder(holder, position);
        } else if (mMode == RecycleViewMode.MODE_ERROR) {
            onBindErrorViewHolder(holder, position);
        } else if (mMode == RecycleViewMode.MODE_EMPTY) {
            onBindEmptyViewHolder(holder, position);
        } else {
            if (position < mHeaders.size()) {
                if (mHeaders.size() > 0) {
                    onBindHeaderViewHolder(holder, position);
                }
            } else if (position >= mHeaders.size() + mData.size()) {
                if (mFooters.size() > 0) {
                    onBindFooterViewHolder(holder, position - mHeaders.size() - mData.size());
                }
            } else {
                onBindDataViewHolder(holder, position - mHeaders.size());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mMode == RecycleViewMode.MODE_DATA) {
            return mData.size() + mHeaders.size() + mFooters.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mMode == RecycleViewMode.MODE_LOADING) {
            return RecycleViewMode.MODE_LOADING;
        }
        if (mMode == RecycleViewMode.MODE_ERROR) {
            return RecycleViewMode.MODE_ERROR;
        }
        if (mMode == RecycleViewMode.MODE_EMPTY) {
            return RecycleViewMode.MODE_EMPTY;
        }
        //check what type our position is, based on the assumption that the order is headers > items > footers
        if (position < mHeaders.size()) {
            return RecycleViewMode.MODE_HEADER_VIEW;
        } else if (position >= mHeaders.size() + mData.size()) {
            return RecycleViewMode.MODE_FOOTER_VIEW;
        }
        return RecycleViewMode.MODE_DATA;
    }

    public abstract RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent);

    public RecyclerView.ViewHolder onCreateLoadingViewHolder(ViewGroup parent) {
        return null;
    }

    public RecyclerView.ViewHolder onCreateErrorViewHolder(ViewGroup parent) {
        return null;
    }

    public RecyclerView.ViewHolder onCreateEmptyViewHolder(ViewGroup parent) {
        return null;
    }

    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent) {
        return null;
    }

    public abstract void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position);

    public void onBindLoadingViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    public void onBindErrorViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    public void onBindEmptyViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    public void setOnFooterViewClickListener(RecycleViewInterface.OnFooterViewClickListener onFooterViewClickListener) {
        mOnFooterViewClickListener = onFooterViewClickListener;
    }

    public void setOnItemClickListener(RecycleViewInterface.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(RecycleViewInterface.OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public void setOnEmptyViewClickListener(RecycleViewInterface.OnEmptyViewClickListener onEmptyViewClickListener) {
        mOnEmptyViewClickListener = onEmptyViewClickListener;
    }

    public void setOnErrorViewClickListener(RecycleViewInterface.OnErrorViewClickListener onErrorViewClickListener) {
        mOnErrorViewClickListener = onErrorViewClickListener;
    }

    public void setOnHeaderViewClickListener(RecycleViewInterface.OnHeaderViewClickListener onHeaderViewClickListener) {
        mOnHeaderViewClickListener = onHeaderViewClickListener;
    }

    public void setToolBarHeight(int toolBarHeight) {
        mToolBarHeight = toolBarHeight;
        this.notifyDataSetChanged();
    }


    public int getFooterCount() {
        return mFooters.size();
    }

    public int getHeaderCount() {
        return mHeaders.size();
    }

    /**
     * add a header to the adapter
     *
     * @param header header
     */
    public void addHeader(Object header) {
        if (mMode != RecycleViewMode.MODE_DATA) {
            Log.e(TAG, "error: you can not add header or footer while you are not in data mode");
            return;
        }
        if (!mHeaders.contains(header)) {
            mHeaders.add(header);
            //animate
            notifyItemInserted(mHeaders.size() - 1);
        }
    }

    /**
     * remove a header from the adapter
     *
     * @param header header
     */
    public void removeHeader(Object header) {
        if (mHeaders.contains(header)) {
            int position = mHeaders.indexOf(header);
            mHeaders.remove(position);
            //animate
            notifyItemRemoved(position);
        }
    }

    /**
     * remove a header from the adapter
     *
     * @param position position
     */
    public void removeHeader(int position) {
        if (mHeaders.size() > 0 && position < mHeaders.size()) {
            mHeaders.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * remove all headers
     */
    public void removeAllHeader() {
        if (mHeaders.size() > 0) {
            notifyItemRangeRemoved(0, mHeaders.size());
            mHeaders.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * add a footer to the adapter
     *
     * @param footer footer
     */
    public void addFooter(Object footer) {
        if (mMode != RecycleViewMode.MODE_DATA) {
            Log.e(TAG, "error: you can not add header or footer while you are not in data mode");
            return;
        }
        if (!mFooters.contains(footer)) {
            mFooters.add(footer);
            //animate
            notifyItemInserted(mHeaders.size() + mData.size() + mFooters.size() - 1);
        }
    }

    /**
     * remove a footer from the adapter
     *
     * @param footer footer view
     */
    public void removeFooter(Object footer) {
        if (mFooters.contains(footer)) {
            int position = mFooters.indexOf(footer);
            mFooters.remove(position);
            //animate
            notifyItemRemoved(mHeaders.size() + mData.size() + position);
        }
    }

    /**
     * remove a footer from the adapter
     *
     * @param position position
     */
    public void removeFooter(int position) {
        if (mFooters.size() > 0 && position < mFooters.size()) {
            mFooters.remove(position);
            //animate
            notifyItemRemoved(mHeaders.size() + mData.size() + position);
        }
    }

    /**
     * remove all footers
     */
    public void removeAllFooters() {
        if (mFooters.size() > 0) {
            notifyItemRangeChanged(0, mFooters.size());
            mFooters.clear();
            notifyDataSetChanged();
        }
    }

}
