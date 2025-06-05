package com.asadbyte.taskthirteen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.asadbyte.taskthirteen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSimpleSamples.text = "Simple Samples"
        binding.btnSimpleSamples.setOnClickListener {
            startActivity(Intent(this, SimpleSamples::class.java))
        }

        binding.btnCustomShapes.text = "Custom Shapes"
        binding.btnCustomShapes.setOnClickListener {
            startActivity(Intent(this, CustomShapes::class.java))
        }
    }
}
