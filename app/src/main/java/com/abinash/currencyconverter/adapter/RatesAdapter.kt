package com.abinash.currencyconverter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abinash.currencyconverter.databinding.ItemRateBinding
import com.abinash.currencyconverter.model.ConversionRate
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class RatesAdapter(//List of entries
    private var rateList: List<ConversionRate?>?
) : RecyclerView.Adapter<RatesAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        return MyViewHolder(
            ItemRateBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup, false
            )
        )
    }

    fun getItem(pos:Int):ConversionRate?{
        return rateList?.get(pos)
    }

    fun update(rateList: List<ConversionRate>) {
        this.rateList = rateList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {
        val rate = rateList!![i]

        holder.binding.amount.text = formatAsCurrency(roundOffDecimal(rate?.value!!))
        holder.binding.currency.text = rate.symbol
    }

    override fun getItemCount(): Int {
        return rateList!!.size
    }

    class MyViewHolder(var binding: ItemRateBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    fun roundOffDecimal(number: Double): String {
        val df = DecimalFormat("0")
        df.maximumIntegerDigits = 50
        if (number > 1 && number <=10) {
            df.maximumFractionDigits = 5
        }else if (number > 10 && number <=100){
            df.maximumFractionDigits = 3
        }else if (number > 100 && number <=1000){
            df.maximumFractionDigits = 2
        }else if (number > 1000){
            df.maximumFractionDigits = 1
        }else{
            df.maximumFractionDigits = 50
        }
        return df.format(number)
    }

    fun formatAsCurrency(str: String): String {
        var str = str
        val floatPos = if (str.indexOf(".") > -1) str.length - str.indexOf(".") else 0
        val nGroups = (str.length - floatPos - 1 - if (str.indexOf("-") > -1) 1 else 0) / 3
        for (i in 0 until nGroups) {
            val commaPos = str.length - i * 4 - 3 - floatPos
            str = str.substring(0, commaPos) + "," + str.substring(commaPos, str.length)
        }
        return str
    }


}