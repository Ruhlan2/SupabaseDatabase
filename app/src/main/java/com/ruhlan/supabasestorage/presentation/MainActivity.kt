package com.ruhlan.supabasestorage.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.ruhlan.supabasestorage.R
import com.ruhlan.supabasestorage.data.common.utils.NetworkResource
import com.ruhlan.supabasestorage.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val vm by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getUsers()
        insertUser()
    }

    private fun getUsers() {
        vm.createUserResult.collectData(
            onSuccess = {
                Log.e("TAG", "getUsers: $it")
            }
        )

        vm.userResult.collectData(
            onSuccess = {
                Log.e("TAG", "getUsers: $it")
            }
        )
    }

    private fun insertUser() {
        binding.insert.setOnClickListener {
            vm.createUser(
                email = "ruhlanusubov@gmail.com",
                name = "Ruhlan11"
            )
        }
    }

    private inline fun <R> Flow<NetworkResource<R>>.collectData(
        crossinline onSuccess: (R?) -> Unit,
        crossinline onError: (String?) -> Unit = {},
        crossinline onLoading: (Boolean) -> Unit = {}
    ) {
        lifecycleScope.launch {
            this@collectData.collectLatest {
                onLoading(false)
                when (it) {
                    is NetworkResource.Error -> {
                        onError(it.message?.localizedMessage)
                    }

                    NetworkResource.Loading -> {
                        onLoading(true)
                    }

                    is NetworkResource.Success -> {
                        onSuccess(it.data)
                    }
                }
            }
        }
    }
}