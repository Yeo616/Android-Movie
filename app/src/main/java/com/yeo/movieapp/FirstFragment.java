package com.yeo.movieapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yeo.movieapp.adapter.MovieAdapter;
import com.yeo.movieapp.model.Movie;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    RecyclerView recyclerView;
    MovieAdapter adapter;
    ArrayList<Movie> movieList = new ArrayList<>();

    ProgressBar progressBar;

    private int count = 0;
    private int offset = 0;
    private int limit = 25;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_first, container, false);

        progressBar = rootView.findViewById(R.id.progressBar);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getNetworkData();

        return rootView;
    }

    // ???????????? ????????? ????????????
    void getNetworkData(){

        movieList.clear();
        count = 0;
        offset = 0;

        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(getContext());
        MovieApi api = retrofit.create(MovieApi.class);

//        SharedPreferences sp = getContext().getSharedPreferences(Config.PREFERENCES_NAME, getContext().MODE_PRIVATE);
        String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTY1ODg5NDcyNywianRpIjoiNWQ2Yjg1MmYtMmYxYS00NGY0LWIxYTctYTRhZDA2NzkwZDJiIiwidHlwZSI6ImFjY2VzcyIsInN1YiI6MSwibmJmIjoxNjU4ODk0NzI3fQ._Kuqg9BK2TL86ahfU8LHkR6THv8FDr31tBHGIf3omaI";

        Call<MovieList> call = api.getMovieList("Bearer " + accessToken,
                offset,
                limit);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){

                    count = response.body().getCount();
                    movieList.addAll( response.body().getItems() );
                    offset = offset + count;
                    adapter = new MovieAdapter(getActivity(), movieList);
                    recyclerView.setAdapter(adapter);

                }else{
                    Toast.makeText(getContext(),"?????? ????????? ??????",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }