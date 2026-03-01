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
    private val items: MutableList<Product>
) : ArrayAdapter<Product>(context, 0, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val entry = items[position]
        val binding: ItemProductBinding
        val bindingLow: ItemLowBinding
        val view: View

        if (entry.Quantity <= 5) {
            bindingLow = ItemLowBinding.inflate(LayoutInflater.from(context), parent, false)

            bindingLow.ProductID.text = entry.UUID
            bindingLow.ProductName.text = "${entry.Name} (${entry.Quantity})"
            bindingLow.ProductImage.setImageResource(
                context.resources.getIdentifier(
                    entry.ImageRef,
                    "drawable",
                    context.packageName
                )
            )
            bindingLow.ProductCategory.text = entry.Category

            bindingLow.AddBtn.setOnClickListener {
                entry.Quantity += 1
                notifyDataSetChanged()
                if (context is MainActivity) context.SaveJSON()
            }

            bindingLow.SubtractBtn.setOnClickListener {
                if (entry.Quantity > 0) {
                    entry.Quantity -= 1

                    if (entry.Quantity == 0) {
                        items.removeAt(position)
                    }
                }
                notifyDataSetChanged()
                if (context is MainActivity) context.SaveJSON()
            }

            view = bindingLow.root
        } else {
            binding = ItemProductBinding.inflate(LayoutInflater.from(context), parent, false)

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

            binding.AddBtn.setOnClickListener {
                entry.Quantity += 1
                notifyDataSetChanged()
                if (context is MainActivity) context.SaveJSON()
            }

            binding.SubtractBtn.setOnClickListener {
                if (entry.Quantity > 0) {
                    entry.Quantity -= 1

                    if (entry.Quantity == 0) {
                        items.removeAt(position)
                    }
                }
                notifyDataSetChanged()
                if (context is MainActivity) context.SaveJSON()
            }

            view = binding.root
        }

        return view
    }
}