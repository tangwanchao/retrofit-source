package me.twc.retrofitsource

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.twc.retrofitsource.databinding.ActSourceObserverView2Binding
import me.twc.source.ErrorSource
import me.twc.source.LoadingSource
import me.twc.source.Source
import me.twc.source.observer.utils.loadingObserver

/**
 * @author 唐万超
 * @date 2022/09/23
 */
class SourceObserverView2Activity : AppCompatActivity() {

    companion object{
        fun show(context: Context){
            val intent = Intent(context,SourceObserverView2Activity::class.java)
            context.startActivity(intent)
        }
    }

    private val mBinding by lazy { ActSourceObserverView2Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.root.setOnClickListener {
            Toast.makeText(this, "content click", Toast.LENGTH_SHORT).show()
        }
        mBinding.obserview.mReloadFun = fun(){
            test()
        }
        test.loadingObserver(this,mBinding.obserview){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        lifecycleScope.launchWhenResumed { test() }
    }


    private val _test: MutableLiveData<Source<String>> by lazy {
        return@lazy MutableLiveData<Source<String>>()
    }

    val test: LiveData<Source<String>> = _test

    private var testJob: Job? = null

    fun test() {
        testJob?.cancel()
        testJob = lifecycleScope.launch {
            _test.value = LoadingSource
            delay(1000)
            _test.value = ErrorSource("测试异常")
        }
    }
}