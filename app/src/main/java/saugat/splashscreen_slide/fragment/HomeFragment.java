package saugat.splashscreen_slide.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import saugat.splashscreen_slide.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    private Integer[] images = {R.drawable.annapurna, R.drawable.bardiya_national_park, R.drawable.boudhanath,
            R.drawable.central_zoo, R.drawable.chitwan_national_park,R.drawable.dashinkali_temple,
            R.drawable.bhaktapur_durbar_square, R.drawable.gosaikunda, R.drawable.hanuman_dhoka,
            R.drawable.kopan_monastery, R.drawable.ktm_durbar_square, R.drawable.langtang_national_park,
            R.drawable.lhotse, R.drawable.machapuchare, R.drawable.muktinath_temple, R.drawable.narayanhity_palace,
            R.drawable.old_freak_street, R.drawable.pashupatinath, R.drawable.sagarmatha_national_park, R.drawable.swayambhu_nath,
            R.drawable.thamel_bazar};

    private Integer[] name = {R.string.annapurna, R.string.bardiya_national_park, R.string.boudhanath,
            R.string.central_zoo, R.string.chitwan_national_park, R.string.dashinkali_temple,
            R.string.durbar_square, R.string.gosaikunda, R.string.hanuman_dhoka, R.string.kopan_monastry,
            R.string.kathmandu_durbar_square, R.string.langtang_national_park, R.string.Lhotse, R.string.machapuchare,
            R.string.muktinath, R.string.narayanhity_palace, R.string.old_freak_street,
            R.string.pashupati_nath_valley, R.string.sagarmatha_national_park, R.string.swayambunath, R.string.thamel};


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        return view;
    }

        class CustomAdapter extends BaseAdapter{

            @Override
            public int getCount() {
                return name.length;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view = getActivity().getLayoutInflater().inflate(R.layout.custom_list_layout,null);

                ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
                TextView textView_name = (TextView) view.findViewById(R.id.textView_name);

                imageView.setImageResource(images[i]);
                textView_name.setText(name[i]);


                return view;
            }
        }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
