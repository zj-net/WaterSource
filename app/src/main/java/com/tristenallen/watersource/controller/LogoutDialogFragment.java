package com.tristenallen.watersource.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import com.tristenallen.watersource.R;

/**
 * Created by tristen on 2/14/17.
 * Class that handles the user logging out using a fragment.
 */
public class LogoutDialogFragment extends DialogFragment {

    // create interface to send events back to activity
    /**
     * Interface for creating a dialog box for verifying log out.
     */
    public interface LogoutDialogListener {
        /**
         * Method for handling the user affirming their desire to log out.
         */
        void onDialogPositiveClick();

        /**
         * Method for handling user canceling log out.
         */
        @SuppressWarnings("EmptyMethod")
        // ^^ dialog box has both buttons; in future negative might take action
        void onDialogNegativeClick();
    }

    private LogoutDialogListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //noinspection ChainedMethodCall
        builder.setMessage(R.string.dialog_logout)
                .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                    // route positive button to listener
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    // route negative button to listener
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (LogoutDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

}
