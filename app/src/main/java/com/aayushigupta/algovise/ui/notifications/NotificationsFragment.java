package com.aayushigupta.algovise.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.aayushigupta.algovise.AlgorithmActivity;
import com.aayushigupta.algovise.algorithms.AlgorithmFactory;
import com.aayushigupta.algovise.algorithms.model.Algorithm;
import com.aayushigupta.algovise.databinding.FragmentNotificationsBinding;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.classifier.CodeProcessor;
import io.github.kbiakov.codeview.highlight.ColorTheme;

public class NotificationsFragment extends Fragment {

    private static boolean isCodeProcessorInitialized = false;

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        if(!isCodeProcessorInitialized) {
            CodeProcessor.init(this.getContext());
            isCodeProcessorInitialized = true;
        }

        final CodeView codeView = binding.textNotifications;
        String language = Algorithm.languages[1];
        String codeString = "Code Not Available!!";
        String code = AlgorithmFactory.getCurrentAlgorithm().getCode(language);
        if(code != null) {
            codeString = code;
        }
        codeView.setOptions(Options.Default.get(root.getContext())
                .withLanguage("java")
                .withCode(codeString)
                .withTheme(ColorTheme.MONOKAI));

        final RadioGroup languageRadioGroup = binding.lanugageRadioGroup;
        languageRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    String langCode = "psuedocode";
                    int langIndex = 0;
                    switch(checkedRadioButton.getText().toString()) {
                        case "Java":
                            langCode = "java";
                            langIndex = 1;
                            break;
                        case "Python":
                            langCode = "py";
                            langIndex = 2;
                            break;
                        case "C":
                            langCode = "c";
                            langIndex = 3;
                            break;
//                        case "Go":
//                            langCode = "go";
//                            langIndex = 4;
//                            break;
                    }
                    String language = Algorithm.languages[langIndex];
                    String codeString = "Code Not Available!!";
                    String code = AlgorithmFactory.getCurrentAlgorithm().getCode(language);
                    if(code != null) {
                        codeString = code;
                    }
                    codeView.setOptions(Options.Default.get(root.getContext())
                            .withLanguage(langCode)
                            .withCode(codeString)
                            .withTheme(ColorTheme.MONOKAI));
                }
            }
        });

        if(AlgorithmActivity.algorithmActivity != null) {
            AlgorithmActivity.algorithmActivity.showSideNavigation();
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}