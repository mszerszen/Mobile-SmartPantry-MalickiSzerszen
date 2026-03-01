package com.example.mobile_smart_pantry_project_iv

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mobile_smart_pantry_project_iv.databinding.ActivityMainBinding
import com.example.mobile_smart_pantry_project_iv.models.Product
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val products = mutableListOf<Product>()
    lateinit var listAdapter: ItemAdapter

    private val newProductLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val newProduct = if (Build.VERSION.SDK_INT >= 33) {
                data?.getSerializableExtra("NEW_PROD", Product::class.java)
            } else {
                @Suppress("DEPRECATION")
                data?.getSerializableExtra("NEW_PROD") as? Product
            }

            if (newProduct != null) {
                products.add(newProduct)
                SaveJSON()
                listAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun LoadJSON() {
        try {
            val file = File(filesDir, "pantry.json")
            if (!file.exists()) return

            val jsonString = file.readText()
            val json = Json { ignoreUnknownKeys = true }
            val loadedList = json.decodeFromString<List<Product>>(jsonString)

            products.clear()
            products.addAll(loadedList)
            listAdapter.notifyDataSetChanged()
        }
        catch (e: Exception) {
            Toast.makeText(
                this,
                "BŁĄD ODCZYTU PLIKU",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun SaveJSON() {
        try {
            val json = Json { prettyPrint = true }
            val jsonString = json.encodeToString(products)

            val file = File(filesDir, "pantry.json")
            file.writeText(jsonString)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "BŁĄD ZAPISU PLIKU",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.NewProduct.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            newProductLauncher.launch(intent)
        }

        listAdapter = ItemAdapter(this, products)
        binding.ItemsListView.adapter = listAdapter

        LoadJSON()
    }
}