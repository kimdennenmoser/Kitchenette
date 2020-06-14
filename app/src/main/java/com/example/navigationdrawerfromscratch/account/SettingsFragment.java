package com.example.navigationdrawerfromscratch.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.navigationdrawerfromscratch.R;
import com.example.navigationdrawerfromscratch.ShoppingListFragment;

public class SettingsFragment extends Fragment {

    View view;
    TextView switchToChangeData;
    TextView switchToDeleteAccount;
    TextView LogOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Aufruf des dazugehörigen Layouts
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        //Initialisieren
        switchToChangeData = (TextView) view.findViewById(R.id.textViewChangeUserData);
        switchToDeleteAccount = (TextView) view.findViewById(R.id.textViewDeleteAccount);
        LogOut = (TextView) view.findViewById(R.id.textViewLogOut);

        switchToChangeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeUserDataFragment changeUserDataFragment = new ChangeUserDataFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, changeUserDataFragment, changeUserDataFragment.getTag()).addToBackStack(null).commit();

            }
        });

        switchToDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    DeleteAccountPopUpFragment deleteAccountPopUpFragment = new DeleteAccountPopUpFragment();
                    deleteAccountPopUpFragment.show(getActivity().getSupportFragmentManager(), "DeleteAccountPopUpFragment");
            }
        });

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOutPopUpFragment logOutPopUpFragment = new LogOutPopUpFragment();
                logOutPopUpFragment.show(getActivity().getSupportFragmentManager(), "LogOutPopUpFragment");
            }
        });


        return view;
    }


}
