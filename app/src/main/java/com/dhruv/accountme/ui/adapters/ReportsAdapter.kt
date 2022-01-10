package com.dhruv.accountme.ui.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dhruv.accountme.R
import com.dhruv.accountme.databinding.ItemBudgetBinding
import com.dhruv.accountme.ui.entities.Budget
import com.dhruv.accountme.ui.util.UtilityFunc.dateMilitoString

class ReportsAdapter(
    val listener: MyOnClickListener
) : RecyclerView.Adapter<ReportsAdapter.MyViewHolder>() {
    inner  class MyViewHolder(val itemBudgetBinding: ItemBudgetBinding): RecyclerView.ViewHolder(itemBudgetBinding.root){
        init {
            itemBudgetBinding.root.setOnLongClickListener{
                val position = adapterPosition
                listener.OnClick(position)
                true
            }
        }
    }

    private val differCallback = object  : DiffUtil.ItemCallback<Budget>(){
        override fun areItemsTheSame(oldItem: Budget, newItem: Budget): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Budget, newItem: Budget): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemBudgetBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with((differ.currentList[position])){
                itemBudgetBinding.budgetItemAmount.text = amount.toString()
                itemBudgetBinding.budgetItemDate.text=dateMilitoString(date.toLong())
                itemBudgetBinding.budgetItemPerpose.text = purpose
                itemBudgetBinding.budgetItemPerpose.tooltipText = purpose

                if (creditdebit.equals("Credit")){
                    itemBudgetBinding.budgetItemType.setImageResource(R.drawable.ic_credit)
                }else{
                    itemBudgetBinding.budgetItemType.setImageResource(R.drawable.ic_debit)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface MyOnClickListener{
        fun OnClick(position: Int)
    }
}

