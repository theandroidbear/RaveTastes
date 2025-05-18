package com.ravemaster.recipeapp.adapters;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialSplitButton;
import com.ravemaster.recipeapp.R;
import com.ravemaster.recipeapp.activities.PagerActivityTesting;
import com.ravemaster.recipeapp.api.getrecipedetails.models.Instruction;
import com.ravemaster.recipeapp.databinding.InstructionListItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InstructionListAdapter extends RecyclerView.Adapter<InstructionListAdapter.InstructionListViewHolder> {

    private Context context;
    private List<Instruction> instructions = new ArrayList<>();
    private TextToSpeech textToSpeech;
    private Activity activity;
    private int totalLength = 0;

    public InstructionListAdapter(Context context, Activity activity, TextToSpeech textToSpeech) {
        this.context = context;
        this.activity = activity;
        this.textToSpeech = textToSpeech;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InstructionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InstructionListItemBinding binding = InstructionListItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
        );
        return new InstructionListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionListViewHolder holder, int position) {
        holder.setUserData(instructions.get(position));
        holder.binding.btnSpeak.setOnClickListener(v->{
            totalLength = instructions.get(position).display_text.length();
            if (textToSpeech != null){
                Toast.makeText(context, "Reading instruction out loud...", Toast.LENGTH_SHORT).show();
                textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String utteranceId) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.binding.linearSpeakAloud.setVisibility(VISIBLE);
                                holder.binding.linearSpeakAloud.setProgress(0);
                                holder.binding.linearSpeakAloud.setMax(100);
                            }
                        });
                    }

                    @Override
                    public void onDone(String utteranceId) {
                        totalLength = 0;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.binding.linearSpeakAloud.setProgress(100);
                                holder.binding.linearSpeakAloud.setVisibility(GONE);
                            }
                        });
                    }

                    @Override
                    public void onRangeStart(String utteranceId, int start, int end, int frame) {
                        super.onRangeStart(utteranceId, start, end, frame);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                float progress = ((float) start/totalLength)*100;
                                holder.binding.linearSpeakAloud.setProgress((int) progress);
                            }
                        });
                    }

                    @Override
                    public void onError(String utteranceId) {
                        Toast.makeText(context, "Unable to start TTS", Toast.LENGTH_SHORT).show();
                    }
                });

                textToSpeech.speak(instructions.get(position).display_text,TextToSpeech.QUEUE_FLUSH,null,"Speech_Id"+String.valueOf(position));
            } else {
                Toast.makeText(context, "TTS currently unavailable, please try again later!", Toast.LENGTH_SHORT).show();
            }

//            context.startActivity(new Intent(
//                    context,
//                    PagerActivityTesting.class
//            ));

        });

        holder.binding.chooseSpeed.setOnClickListener(v->{
            PopupMenu popupMenu = new PopupMenu(activity,holder.binding.btnTTS);
            popupMenu.getMenuInflater().inflate(R.menu.tts_speed_menu,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.slowSpeed){
                    textToSpeech.setSpeechRate(0.5f);
                    Toast.makeText(context, "Speed set to slow", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.normalSpeed){
                    textToSpeech.setSpeechRate(1.0f);
                    Toast.makeText(context, "Speed set to normal", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.fastSpeed){
                    textToSpeech.setSpeechRate(1.5f);
                    Toast.makeText(context, "Speed set to fast", Toast.LENGTH_SHORT).show();
                }
                return true;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return instructions.size();
    }

    static class InstructionListViewHolder extends RecyclerView.ViewHolder {
        InstructionListItemBinding binding;
        public InstructionListViewHolder(InstructionListItemBinding instructionListItemBinding) {
            super(instructionListItemBinding.getRoot());
            binding = instructionListItemBinding;
        }

        void setUserData(Instruction instruction){
            binding.txtInstructionStep.setText(instruction.display_text);
            binding.instructionPosition.setText("Step "+String.valueOf(instruction.position));
        }
    }
}
