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
) : ArrayAdapter<Product>(context, 0, items), android.widget.Filterable {

    private var filteredItems = items.toMutableList()
    private var currentCategory: String? = null
    private var currentSearch: String = ""

    override fun getCount(): Int = filteredItems.size
    override fun getItem(position: Int): Product = filteredItems[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val entry = filteredItems[position]
        val view: View

        if (entry.Quantity <= 5) {
            val bindingLow = ItemLowBinding.inflate(LayoutInflater.from(context), parent, false)
            bindingLow.ProductID.text = entry.UUID
            bindingLow.ProductName.text = "${entry.Name} (${entry.Quantity})"
            bindingLow.ProductImage.setImageResource(
                context.resources.getIdentifier(entry.ImageRef, "drawable", context.packageName)
            )
            bindingLow.ProductCategory.text = entry.Category

            bindingLow.AddBtn.setOnClickListener {
                entry.Quantity += 1
                notifyDataSetChanged()
                if (context is MainActivity) context.SaveJSON()
            }

            bindingLow.SubtractBtn.setOnClickListener {
                if (entry.Quantity > 0) entry.Quantity -= 1
                if (entry.Quantity == 0) {
                    items.remove(entry)
                    applyFilter()
                } else notifyDataSetChanged()
                if (context is MainActivity) context.SaveJSON()
            }

            view = bindingLow.root
        } else {
            val binding = ItemProductBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.ProductID.text = entry.UUID
            binding.ProductName.text = "${entry.Name} (${entry.Quantity})"
            binding.ProductImage.setImageResource(
                context.resources.getIdentifier(entry.ImageRef, "drawable", context.packageName)
            )
            binding.ProductCategory.text = entry.Category

            binding.AddBtn.setOnClickListener {
                entry.Quantity += 1
                notifyDataSetChanged()
                if (context is MainActivity) context.SaveJSON()
            }

            binding.SubtractBtn.setOnClickListener {
                if (entry.Quantity > 0) entry.Quantity -= 1
                if (entry.Quantity == 0) {
                    items.remove(entry)
                    applyFilter()
                } else notifyDataSetChanged()
                if (context is MainActivity) context.SaveJSON()
            }

            view = binding.root
        }

        return view
    }

    override fun getFilter(): android.widget.Filter {
        return object : android.widget.Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                currentSearch = constraint?.toString()?.lowercase() ?: ""
                val results = FilterResults()
                results.values = getFilteredList()
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = results?.values as MutableList<Product>
                notifyDataSetChanged()
            }
        }
    }

    fun filterByCategory(category: String?) {
        currentCategory = category
        applyFilter()
    }

    private fun applyFilter() {
        filteredItems = getFilteredList()
        notifyDataSetChanged()
    }

    private fun getFilteredList(): MutableList<Product> {
        return items.filter { product ->
            val matchesSearch = if (currentSearch.isEmpty()) true else product.Name.lowercase().contains(currentSearch)
            val matchesCategory = if (currentCategory == null) true else product.Category.equals(currentCategory, ignoreCase = true)
            matchesSearch && matchesCategory
        }.toMutableList()
    }

    fun refreshFilter() {
        applyFilter()
    }
}