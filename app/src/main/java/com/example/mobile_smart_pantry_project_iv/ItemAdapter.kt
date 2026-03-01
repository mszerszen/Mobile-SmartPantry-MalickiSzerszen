package com.example.mobile_smart_pantry_project_iv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.mobile_smart_pantry_project_iv.databinding.ItemLowBinding
import com.example.mobile_smart_pantry_project_iv.databinding.ItemProductBinding
import com.example.mobile_smart_pantry_project_iv.models.Product

class ItemAdapter(
    private val context: Context,
    private val items: List<Product>
) : ArrayAdapter<Product>(context, 0, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val entry = items[position]

        return if (entry.Quantity <= 5) {
            val binding: ItemLowBinding = if(convertView == null) {
                ItemLowBinding.inflate(LayoutInflater.from(context), parent, false)
            } else {
                ItemLowBinding.bind(convertView)
            }

            binding.ProductID.text = entry.UUID
            binding.ProductName.text = "${entry.Name} (${entry.Quantity})"
            binding.ProductImage.setImageResource(
                context.resources.getIdentifier(
                    entry.ImageRef,
                    "drawable",
                    context.packageName
                )
            )
            binding.ProductCategory.text = entry.Category

            binding.root
        } else {
            val binding: ItemProductBinding = if(convertView == null) {
                ItemProductBinding.inflate(LayoutInflater.from(context), parent, false)
            } else {
                ItemProductBinding.bind(convertView)
            }

            binding.ProductID.text = entry.UUID
            binding.ProductName.text = "${entry.Name} (${entry.Quantity})"
            binding.ProductImage.setImageResource(
                context.resources.getIdentifier(
                    entry.ImageRef,
                    "drawable",
                    context.packageName
                )
            )
            binding.ProductCategory.text = entry.Category

            binding.root
        }
    }
}