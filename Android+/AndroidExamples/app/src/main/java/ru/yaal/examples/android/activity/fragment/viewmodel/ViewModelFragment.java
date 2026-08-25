package ru.yaal.examples.android.activity.fragment.viewmodel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.concurrent.Executors;

import ru.yaal.examples.android.R;

public class ViewModelFragment extends Fragment {

    private ViewModelViewModel mViewModel;

    public static ViewModelFragment newInstance() {
        return new ViewModelFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_model_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ViewModelViewModel.class);

        TextView viewModelFragmentTextView = getActivity().findViewById(R.id.view_model_fragment_text_view);
        StringObserver observer = new StringObserver(viewModelFragmentTextView);
        mViewModel.text.observeForever(observer);
        mViewModel.text.setValue("Init value");

        runThreadUpdatingText();
    }

    private void runThreadUpdatingText() {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                int n = 1;
                while(true) {
                    try {
                        mViewModel.text.postValue("Hi, #" + n);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    n++;
                }
            }
        });
    }

    private static class StringObserver implements Observer<String> {
        private final TextView textView;

        StringObserver(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onChanged(String s) {
            textView.setText(s);
        }
    }

}
