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
import androidx.recyclerview.widget.RecyclerView;


import com.ravemaster.recipeapp.api.getrecipedetails.models.Instruction;
import com.ravemaster.recipeapp.databinding.InstructionListItemBinding;

import java.util.ArrayList;
import java.util.List;

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
        holder.binding.btnTTS.setOnClickListener(v->{
            totalLength = instructions.get(position).display_text.length();
            if (textToSpeech != null){
                textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String utteranceId) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.binding.linearSpeakAloud.setVisibility(VISIBLE);
                                holder.binding.linearSpeakAloud.setProgress(0);
                                holder.binding.linearSpeakAloud.setMax(100);
                                holder.binding.speechCircular.setVisibility(VISIBLE);
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
                                holder.binding.speechCircular.setVisibility(GONE);
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
