package com.example.myapplication0531
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
// NetworkState.kt 或 MainViewModel.kt 中
sealed class NetworkState {
    data object Loading : NetworkState() // 表示正在加载中
    data class Success(val data: String) : NetworkState() // 表示请求成功，并携带数据
    data class Error(val message: String) : NetworkState() // 表示请求失败，并携带错误信息
    data object Idle : NetworkState() // 可选：表示初始状态或空闲状态
}
class Main2ViewModel : ViewModel() {
    // UI状态：使用 NetworkState 密封类来管理所有状态
    var networkState: NetworkState by mutableStateOf(NetworkState.Idle)
        private set // 只能在ViewModel内部修改

    private val client = OkHttpClient()
    private val apiUrl = "https://jsonplaceholder.typicode.com/posts/1"

    fun fetchData() {
        // 则防止重复请求
        if (networkState == NetworkState.Loading)  return
        networkState = NetworkState.Loading // 设置为加载中状态

        viewModelScope.launch { // 启动协程，默认在 Dispatchers.Main
           // try {
                // 切换到 IO 线程执行网络请求
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url(apiUrl)
                        .build()
                    client.newCall(request).execute()
                }

                // 协程自动切换回 Dispatchers.Main
                if (response.isSuccessful) {
                    val responseBody = response.body?.string() ?: "请求成功，但响应体为空。"
                    networkState = NetworkState.Success(responseBody) // 设置为成功状态
                } else {
                    networkState = NetworkState.Error("请求失败: ${response.code} - ${response.message}") // 设置为失败状态
                }

        }
    }
}


