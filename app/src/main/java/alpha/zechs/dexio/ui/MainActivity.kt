package alpha.zechs.dexio.ui

import alpha.zechs.dexio.databinding.ActivityMainBinding
import alpha.zechs.dexio.db.TodoDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        mainViewModel = ViewModelProvider(
            this,
            MainViewModelProviderFactory(TodoDatabase(this))
        )[MainViewModel::class.java]

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
    }

    private fun setupToolbar() {
        mainViewModel.today.observe(this@MainActivity) {
            binding.apply {
                tvDate.text = it.date.toString()
                tvDay.text = it.day
                tvMonthYear.text = it.monthYear
            }
        }
    }
}