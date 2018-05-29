package com.sacombank.consumers.views.loginregister.register;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sacombank.consumers.R;
import com.sacombank.consumers.views.loginregister.LoginListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    SignUp1Fragment signUp1Fragment;
    private LoginListener listener;
    FragmentManager fragmentManager;
    SignUp1Fragment.SignUpListener signUpListener = new SignUp1Fragment.SignUpListener() {

        @Override
        public void removeFragment() {
            fragmentManager.beginTransaction().
                    remove(fragmentManager.findFragmentById(R.id.frame_signup)).commit();
        }

        @Override
        public void addFragment() {
            signUp1Fragment = new SignUp1Fragment();
            signUp1Fragment.setListener(listener);
            signUp1Fragment.setSignUpListener(signUpListener);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frame_signup, signUp1Fragment);
            transaction.commit();
        }
    };

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentManager = getChildFragmentManager();
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        signUp1Fragment = new SignUp1Fragment();
        signUp1Fragment.setListener(listener);
        signUp1Fragment.setSignUpListener(signUpListener);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_signup, signUp1Fragment);
        transaction.commit();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setListener(LoginListener listener) {
        this.listener = listener;
    }

    public void onNextPage2() {
        FragmentTransaction trans = fragmentManager.beginTransaction();
        SignUp2Fragment fragment = new SignUp2Fragment();
        fragment.setListener(listener);
        fragment.setSignUpListener(signUpListener);
//        Bundle bundle = new Bundle();
//        bundle.putInt(KEY, QRCodeDynamicInputFragment);
//        fragment.setArguments(bundle);
        trans.replace(R.id.frame_signup, fragment);
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trans.commit();
    }
}
