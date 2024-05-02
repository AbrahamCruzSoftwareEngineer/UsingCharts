package com.example.usingcharts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.usingcharts.viewmodel.MyViewModel
import com.robinhood.spark.SparkView


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MyViewModel
    private lateinit var sparkView: SparkView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ensure you have a SparkView with this ID in your layout
        sparkView = findViewById(R.id.sparkView)

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        // Set up observers and any UI components that interact with the ViewModel
        viewModel.numbersLiveData.observe(this) { numbers ->
            updateSparkView(numbers)
        }

        // Start fetching numbers
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                viewModel.fetchRandomNumberRepeatedly()
            }
        })
    }

    private fun updateSparkView(numbers: List<Int>) {
        sparkView.adapter = object : com.robinhood.spark.SparkAdapter() {
            override fun getCount() = numbers.size
            override fun getItem(index: Int) = numbers[index]
            override fun getY(index: Int): Float = numbers[index].toFloat()
        }
    }
}

