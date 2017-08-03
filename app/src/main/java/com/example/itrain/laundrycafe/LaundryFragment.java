package com.example.itrain.laundrycafe;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.itrain.laundrycafe.R.array.picture;
import static com.example.itrain.laundrycafe.R.id.image;

public class LaundryFragment extends Fragment {

    String url ="https://sheetsu.com/apis/v1.0/e25e61445b37";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container,false);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        final ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Request a string response from the provided URL.

        JsonArrayRequest objRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++){
                               adapter.addJsonObject(response.getJSONObject(i));
                            }
                        adapter.notifyDataSetChanged();
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               Log.d("error", error.toString());
            }
        });
// Add the request to the RequestQueue.
        queue.add(objRequest);
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView picture;
        public TextView name;
        public TextView description;



        public ViewHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.fragment_laundry, parent, false));


            picture = (ImageView)itemView.findViewById(R.id.card_image);

            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        Context context;
        private final List<JSONObject> jsonArray = new ArrayList<>();
        public ContentAdapter(Context context) {
            this.context= context;


        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            try {

                holder.description.setText(jsonArray.get(position).getString("description"));
                holder.name.setText(jsonArray.get(position).getString("name"));
                Picasso.with(context).load(jsonArray.get(position).getString("image")).into(holder.picture);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void addJsonObject(JSONObject obj){
            jsonArray.add(obj);
        }
        @Override
        public int getItemCount() {
            return jsonArray.size();
        }
    }

}
