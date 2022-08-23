package com.aayushigupta.algovise.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aayushigupta.algovise.AlgorithmActivity;
import com.aayushigupta.algovise.R;
import com.aayushigupta.algovise.algorithms.AlgorithmFactory;
import com.aayushigupta.algovise.algorithms.model.Algorithm;
import com.aayushigupta.algovise.algorithms.model.DataNode;
import com.aayushigupta.algovise.databinding.FragmentVisualBinding;
import com.aayushigupta.algovise.tools.CountDownTimerFactory;
import com.aayushigupta.algovise.visualiser.AlgorithmLogSingleton;
import com.aayushigupta.algovise.visualiser.SearchVisualiser;
import com.aayushigupta.algovise.visualiser.SearchVisualiserSingleton;

import java.util.ArrayList;
import java.util.List;

public class VisualFragment extends Fragment {

    private static boolean inputDropdownIsOpen = false;
    private static int seekbarProgress = 3;
    public static LinearLayout playBtn;
    public static LinearLayout stopBtn;
    View root;
    Paint paint = new Paint();
    RelativeLayout relativeLayout;
    Algorithm algorithm;
    EditText inputText1 = null;
    EditText inputText2 = null;
    EditText inputText3 = null;
    Button button1 = null;
    Button button2 = null;
    SeekBar speedControl = null;
    SearchVisualiser view;
    DataNode[] array;
    private FragmentVisualBinding binding;
    public static VisualFragment visualFragment;

    public VisualFragment() {
        visualFragment = this;
        algorithm = AlgorithmFactory.getCurrentAlgorithm();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentVisualBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        // Logging
        TextView textLog = root.findViewById(R.id.text_log);
        AlgorithmLogSingleton.setView(textLog);
        ScrollView logScroll = root.findViewById(R.id.log_scroll);
        AlgorithmLogSingleton.setScroll(logScroll);
        AlgorithmLogSingleton.refreshLogs();

        // Name of the Algorithm
        TextView algoNameTextView = root.findViewById(R.id.text_algoname);
        algoNameTextView.setText(algorithm.getName());
        relativeLayout = root.findViewById(R.id.rect);
        if(algorithm.getName().startsWith("BST")) {
            ViewGroup.LayoutParams params = relativeLayout.getLayoutParams();
            params.height = 600; // 600 pixels
            relativeLayout.setLayoutParams(params);
        } else {
            ViewGroup.LayoutParams params = relativeLayout.getLayoutParams();
            params.height = 300; // 300 pixels
            relativeLayout.setLayoutParams(params);
        }

        // Hiding Input Dropdown
        LinearLayout inputDropdown = root.findViewById(R.id.input_dropdown);
        LinearLayout showInputBtn = root.findViewById(R.id.btn_ShowInput);
        ImageView showInputImg = root.findViewById(R.id.img_ShowInput);
        showInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputDropdownIsOpen) {
                    Drawable res = getResources().getDrawable(R.drawable.ic_arrow_down);
                    showInputImg.setImageDrawable(res);
                    inputDropdown.setVisibility(View.GONE);
                } else {
                    Drawable res = getResources().getDrawable(R.drawable.ic_arrow_up);
                    showInputImg.setImageDrawable(res);
                    inputDropdown.setVisibility(View.VISIBLE);
                }
                inputDropdownIsOpen = !inputDropdownIsOpen;
            }
        });
        inputText1 = root.findViewById(R.id.text_input);
        inputText2 = root.findViewById(R.id.text_input_2);
        inputText3 = root.findViewById(R.id.text_input_3);
        button1 = root.findViewById(R.id.button1);
        button2 = root.findViewById(R.id.button2);
        if(algorithm.getFirstParamLabel() == null) {
            inputText1.setVisibility(View.GONE);
        } else {
            inputText1.setHint(algorithm.getFirstParamLabel());
        }
        if(algorithm.getSecondParamLabel() == null) {
            inputText2.setVisibility(View.GONE);
        } else {
            inputText2.setHint(algorithm.getSecondParamLabel());
        }
        if(algorithm.getThirdParamLabel() == null) {
            inputText3.setVisibility(View.GONE);
        } else {
            inputText3.setHint(algorithm.getThirdParamLabel());
        }
        if(algorithm.getSecondButtonLabel() == null) {
            button1.setVisibility(View.GONE);
        } else {
            button1.setText(algorithm.getSecondButtonLabel());
        }
        if(algorithm.getThirdButtonLabel() == null) {
            button2.setVisibility(View.GONE);
        } else {
            button2.setText(algorithm.getThirdButtonLabel());
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String operations = "";
                switch(algorithm.getName()) {
                    case AlgorithmFactory.LINEAR_STACK:
                        // Push
                        if(!inputText2.getText().toString().isEmpty()) {
                            operations = "PUSH " + inputText2.getText().toString();
                        }
                        break;
                    case AlgorithmFactory.LINKED_LIST:
                        // Delete
                        if(!inputText2.getText().toString().isEmpty()) {
                            operations = "DELETE " + inputText2.getText().toString();
                        }
                        break;
                    default:
                        break;
                }
                if(!operations.isEmpty()) {
                    List<String> params = new ArrayList<String>();
                    params.add(operations);
                    algorithm.setAlgoParams(params);
                    algorithm.reset();
                    startVisualization();
                } else {
                    Toast toast = Toast.makeText(visualFragment.getContext(),
                            "Missing value for input parameters",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String operations = "";
                switch(algorithm.getName()) {
                    case AlgorithmFactory.LINEAR_STACK:
                        // Pop
                        operations = "POP";
                        break;
                    case AlgorithmFactory.LINKED_LIST:
                        // Insert
                        if(!inputText2.getText().toString().isEmpty() && !inputText3.getText().toString().isEmpty()) {
                            operations = "INSERT " + inputText2.getText().toString() + " " + inputText3.getText().toString();
                        }
                        break;
                    default:
                        break;
                }
                if(!operations.isEmpty()) {
                    List<String> params = new ArrayList<String>();
                    params.add(operations);
                    algorithm.setAlgoParams(params);
                    algorithm.reset();
                    startVisualization();
                } else {
                    Toast toast = Toast.makeText(visualFragment.getContext(),
                            "Missing value for input parameters",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        LinearLayout submitInputBtn = root.findViewById(R.id.btn_submit);
        submitInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textInput = inputText1.getText().toString().trim();
                String textInput2 = inputText2.getText().toString().trim();
                try {
                    if (!textInput.trim().isEmpty()) {
                        String[] input = textInput.trim().split(",");
                        array = new DataNode[input.length];
                        for (int i = 0; i < input.length; i++) {
                            int data = Integer.parseInt(input[i].trim());
                            array[i] = new DataNode(data, Algorithm.DATANODE_COLOR);
                        }
                        algorithm.setDataStrucure(array);
                        CountDownTimerFactory.setStepCount(algorithm.getMaxStepCount());
                    }
                    List<String> params = new ArrayList<String>();
                    switch(algorithm.getName()) {
                        case AlgorithmFactory.LINEAR_STACK:
                            params.add("PEEK");
                            algorithm.setAlgoParams(params);
                            break;
                        case AlgorithmFactory.LINKED_LIST:
                            params.add("TRAVERSE");
                            algorithm.setAlgoParams(params);
                            break;
                        default:
                            if (!textInput2.trim().isEmpty()) {
                                params.add(textInput2.trim());
                                algorithm.setAlgoParams(params);
                            }
                            break;
                    }
                    Toast toast = Toast.makeText(visualFragment.getContext(),
                            "Algorithm data updated!!",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    algorithm.reset();
                    SearchVisualiser view = SearchVisualiserSingleton.getView();
                    view.setDataStructure(algorithm.getDataStrucure());
                    view.setShowArrow(algorithm.getShowArrow());
                    view.refreshDrawableState();
                    view.invalidate();
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                    Toast toast = Toast.makeText(visualFragment.getContext(),
                            "Please enter comma separated list of numbers",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        speedControl = root.findViewById(R.id.seekBar);
        speedControl.setProgress(seekbarProgress);
        speedControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekbarProgress = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                long delay = 1000;
                switch(seekbarProgress) {
                    case 0:
                        delay = 2000;
                        break;
                    case 1:
                        delay = 1500;
                        break;
                    case 2:
                        delay = 1000;
                        break;
                    case 3:
                        delay = 800;
                        break;
                    case 4:
                        delay = 500;
                        break;
                    case 5:
                        delay = 200;
                        break;
                    case 6:
                        delay = 100;
                        break;
                }
                // 2000 => 2 sec => 30 fpm
                int fps = (int)(60000.0f / (float) delay);
                Toast toast = Toast.makeText(visualFragment.getContext(),
                        "Visualization will run at " + fps + " frames per minute.",
                        Toast.LENGTH_SHORT);
                toast.show();
                CountDownTimerFactory.delayInMillisecond = delay;
            }
        });
        playBtn = root.findViewById(R.id.btn_PlayPause);
        stopBtn = root.findViewById(R.id.btn_Stop);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVisualization();
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownTimerFactory.stop();
                algorithm.reset();
                SearchVisualiser view = SearchVisualiserSingleton.getView();
                view.setDataStructure(algorithm.getDataStrucure());
                view.setShowArrow(algorithm.getShowArrow());
                view.refreshDrawableState();
                view.invalidate();
                Toast toast = Toast.makeText(visualFragment.getContext(),
                        "Visualization stopped.",
                        Toast.LENGTH_SHORT);
                toast.show();
                stopBtn.setAlpha(0.9f);
                playBtn.setAlpha(1.0f);
                Drawable res = getResources().getDrawable(R.drawable.ic_play);
                ImageView playPauseImg = root.findViewById(R.id.img_PlayPause);
                playPauseImg.setImageDrawable(res);
            }
        });
        relativeLayout.setVisibility(View.VISIBLE);
        view = new SearchVisualiser(getActivity());
        relativeLayout.addView(view);
        SearchVisualiserSingleton.setView(view);
        algorithm = AlgorithmFactory.getCurrentAlgorithm();
        SearchVisualiser view = SearchVisualiserSingleton.getView();
        view.setDataStructure(algorithm.getDataStrucure());
        view.setShowArrow(algorithm.getShowArrow());
        view.refreshDrawableState();
        view.invalidate();

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

    private void startVisualization() {
        if(playBtn.getAlpha() == 0.9f) {
            // Pause the visualization
            CountDownTimerFactory.pause();
            Toast toast = Toast.makeText(visualFragment.getContext(),
                    "Visualization paused.",
                    Toast.LENGTH_SHORT);
            toast.show();
            playBtn.setAlpha(1.0f);
            stopBtn.setAlpha(1.0f);
            Drawable res = getResources().getDrawable(R.drawable.ic_play);
            ImageView playPauseImg = root.findViewById(R.id.img_PlayPause);
            playPauseImg.setImageDrawable(res);
            return;
        }
        if(!CountDownTimerFactory.isPaused()) {
            CountDownTimerFactory.stop();
            algorithm.reset();
            SearchVisualiser view = SearchVisualiserSingleton.getView();
            view.setDataStructure(algorithm.getDataStrucure());
            view.setShowArrow(algorithm.getShowArrow());
            view.refreshDrawableState();
            view.invalidate();
        }
        CountDownTimerFactory.setStepCount(algorithm.getMaxStepCount());
        CountDownTimerFactory.start();
        Toast toast = Toast.makeText(visualFragment.getContext(),
                "Visualization started.",
                Toast.LENGTH_SHORT);
        toast.show();
        playBtn.setAlpha(0.9f);
        stopBtn.setAlpha(1.0f);
        Drawable res = getResources().getDrawable(R.drawable.ic_pause);
        ImageView playPauseImg = root.findViewById(R.id.img_PlayPause);
        playPauseImg.setImageDrawable(res);
    }
}