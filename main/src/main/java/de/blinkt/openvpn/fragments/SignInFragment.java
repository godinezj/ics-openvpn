package de.blinkt.openvpn.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import de.blinkt.openvpn.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment implements View.OnClickListener {
    public static final String LOGIN_MESSAGE = "LOGIN_MESSAGE";
    public static final String ACCOUNT_CREATED_MESSAGE = "ACCOUNT_CREATED_MESSAGE";
    // use IP address not localhost IP
    public static final String URL = "http://192.168.1.26:3000/";
    private static final String TAG = SignInFragment.class.getName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

//    public SignInFragment() {
//        // Required empty public constructor
//    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
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
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    private void goToLogedInScreen(String message) {
//        Intent intent = new Intent(this, LoggedInActivity.class);
//        intent.putExtra(LOGIN_MESSAGE, message);
//        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        System.err.println("AAAAAAAAAAAAAAAA");
        // TODO determine which button was pressed and exec corresponding method
        login(v);
    }

    public void login(View v) {
        EditText email = (EditText)v.findViewById(R.id.loginEmail);
        EditText password = (EditText)v.findViewById(R.id.loginPassword);
        JSONObject obj = new JSONObject();
        try {
            obj.put("email", email.getText().toString());
            obj.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(v.getContext());
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, URL + "login", obj, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                String message = "Logged in!";
                if (response.has("message")) {
                    try {
                        message = (String) response.get("message");
                    } catch (JSONException e) {}
                }
                goToLogedInScreen(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    Log.e(TAG, "Network error: " + new String(error.networkResponse.data));
                }
                String message = "Wrong Login!";
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    message = "Could not reach "+URL;
                }
                Log.e(TAG, message);
                Toast myToast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
                myToast.show();
            }
        });
        queue.add(jor);
    }


    public void gotoSignup(View v) {
//        Intent intent = new Intent(this, SignupActivity.class);
//        startActivity(intent);
    }

    public void forgotPassword(View v) {
//        Intent intent = new Intent(this, ForgotPasswordActivity.class);
//        startActivity(intent);
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
