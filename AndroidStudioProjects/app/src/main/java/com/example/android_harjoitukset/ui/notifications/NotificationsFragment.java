package com.example.android_harjoitukset.ui.notifications;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.android_harjoitukset.R;
import com.example.android_harjoitukset.databinding.FragmentNotificationsBinding;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private MaterialButtonToggleGroup toggleGroup;

    private NumberPicker numPicker;
    private CountDownTimer timer;
    private TextView endText;
    private Ringtone defaultRingtone;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        endText = binding.endText;
        endText.setVisibility(View.INVISIBLE);

        numPicker = binding.numberpicker;

        String[] pickerVals = new String[61];
        numPicker.setMinValue(0);
        numPicker.setMaxValue(pickerVals.length - 1);
        for (int i = 0; i < pickerVals.length; i++) {
            pickerVals[i] = i + " s";

        }
        numPicker.setDisplayedValues(pickerVals);
        numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {

            }
        });

        toggleGroup = root.findViewById(R.id.toggleButton);
        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup materialButtonToggleGroup, int i, boolean b) {
                if (b) {
                    if (i == R.id.timerStart) {
                        startTimer(numPicker.getValue());
                    } else if (i == R.id.timerPause) {
                        pauseTimer();
                    } else if (i == R.id.timerStop) {
                        stopTimer();
                    }
                }


            }
        });


        defaultRingtone = RingtoneManager.getRingtone(getActivity(),
                Settings.System.DEFAULT_RINGTONE_URI);

        return root;
    }

    public void stopTimer() {
        timer.cancel();
        numPicker.setValue(0);
    }

    public void pauseTimer() {
        timer.cancel();
    }

    public void startTimer(int selectedTime) {
        Animation fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout);
        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);


        timer = new CountDownTimer((long) selectedTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                numPicker.setValue((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                endText.setVisibility(View.VISIBLE);
                endText.startAnimation(fadeIn);
                defaultRingtone.play();
                endText.clearAnimation();
                endText.startAnimation(fadeOut);
                endText.clearAnimation();

                defaultRingtone.stop();
                endText.setVisibility(View.INVISIBLE);
            }
        }.start();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}