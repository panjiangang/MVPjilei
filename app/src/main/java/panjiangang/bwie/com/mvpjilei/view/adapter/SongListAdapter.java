package panjiangang.bwie.com.mvpjilei.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import panjiangang.bwie.com.mvpjilei.R;
import panjiangang.bwie.com.mvpjilei.model.bean.TestBean;

/**
 * Created by lenovo on 2017/12/27.
 */

public class SongListAdapter extends RecyclerView.Adapter {
    private Context mcontext;
    private ArrayList<TestBean.SongListBean> listBeans = new ArrayList<>();

    public SongListAdapter(Context context) {
        this.mcontext = context;
    }

    public void setListData(List<TestBean.SongListBean> song_list, boolean needClear) {
        if (song_list != null) {
            if (needClear) {
                listBeans.clear();
            }
            listBeans.addAll(song_list);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_song, parent, false);
        return new SongListHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SongListHolder songListHolder = (SongListHolder) holder;

        songListHolder.item_song_te.setText(listBeans.get(position).getTitle());
        songListHolder.item_song_te1.setText(listBeans.get(position).getAlbum_title());

        String pic_big = listBeans.get(position).getPic_big();
        ImageLoader instance = ImageLoader.getInstance();
        instance.displayImage(pic_big, songListHolder.item_song_img);
    }

    public class SongListHolder extends RecyclerView.ViewHolder {
        TextView item_song_te;
        TextView item_song_te1;
        ImageView item_song_img;

        public SongListHolder(View itemView) {
            super(itemView);
            item_song_te = itemView.findViewById(R.id.item_song_te);
            item_song_te1 = itemView.findViewById(R.id.item_song_te1);
            item_song_img = itemView.findViewById(R.id.item_song_img);
        }
    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }

}
