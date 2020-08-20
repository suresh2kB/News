package com.example.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {


    private static final String LOCATION_SEPARATOR = " of ";

    public NewsAdapter(@NonNull Context context, List<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }
        News currentNews = getItem(position);

        TextView authorname = (TextView) listItemView.findViewById(R.id.author);
        authorname.setText(currentNews.getmAuthor());
        TextView headline = (TextView) listItemView.findViewById(R.id.headline);

        headline.setText((currentNews.getmHeadLine()));
        TextView discription = (TextView) listItemView.findViewById(R.id.discription);
        discription.setText(currentNews.getmDiscription());
        return listItemView;
    }

}
