package com.example.mobile_smart_pantry_project_iv

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mobile_smart_pantry_project_iv.databinding.ActivityAddProductBinding
import com.example.mobile_smart_pantry_project_iv.models.Product

class AddProductActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val icons = resources.getStringArray(R.array.icons)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            icons
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.Icon.adapter = adapter

        binding.Confirm.setOnClickListener {
            val UUID = binding.UUID.text.toString()
            val Name = binding.Name.text.toString()
            val Quantity = binding.Quantity.text.toString().toIntOrNull()
            val Category = binding.Category.text.toString()
            val Icon = binding.Icon.selectedItem.toString()

            if(
                UUID.isNotEmpty() &&
                Name.isNotEmpty() &&
                Quantity != null &&
                Category.isNotEmpty() &&
                Icon.isNotEmpty()
                ) {

                val newProduct = Product(
                    UUID,
                    Name,
                    Quantity,
                    Category,
                    Icon
                )

                val returnIntent = Intent()
                returnIntent.putExtra("NEW_PROD", newProduct)
                setResult(RESULT_OK, returnIntent)
                finish()

            } else {
                Toast.makeText(
                    this,
                    "WYPEŁNIJ WSZYSTKIE DANE",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}