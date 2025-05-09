package com.ravemaster.recipeapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravemaster.recipeapp.api.getrecipedetails.models.Instruction;
import com.ravemaster.recipeapp.databinding.InstructionListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class InstructionListAdapter extends RecyclerView.Adapter<InstructionListAdapter.InstructionListViewHolder> {

    private Context context;
    private List<Instruction> instructions = new ArrayList<>();

    public InstructionListAdapter(Context context) {
        this.context = context;
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
