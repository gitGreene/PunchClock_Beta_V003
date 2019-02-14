package co.codemaestro.punchclock_beta_v003.Fragments;


import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import co.codemaestro.punchclock_beta_v003.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryCalendarFragment extends Fragment {
    private ImageView square1, square2, square3, square4, square5, square6, square7;

    public CategoryCalendarFragment() {
        // Required empty public constructor
    }

    public static CategoryCalendarFragment newInstance(int position) {
        return new CategoryCalendarFragment();
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_category_calendar, container, false);

        square1 = view.findViewById(R.id.imageView1);
        square2 = view.findViewById(R.id.imageView2);
        square3 = view.findViewById(R.id.imageView3);
        square4 = view.findViewById(R.id.imageView4);
        square5 = view.findViewById(R.id.imageView5);
        square6 = view.findViewById(R.id.imageView6);
        square7 = view.findViewById(R.id.imageView7);


        TypedArray squares = getResources().obtainTypedArray(R.array.calendar_squares_array);

        square1.setImageResource(squares.getResourceId(0, 1));
        square2.setImageResource(squares.getResourceId(1, 1));
        square3.setImageResource(squares.getResourceId(2, 1));
        square4.setImageResource(squares.getResourceId(3, 1));
        square5.setImageResource(squares.getResourceId(4, 1));
        square6.setImageResource(squares.getResourceId(5, 1));
        square7.setImageResource(squares.getResourceId(6, 1));

        squares.recycle();


        // Inflate the layout for this fragment
        return view;
    }

}
