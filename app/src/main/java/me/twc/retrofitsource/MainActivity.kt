package me.twc.retrofitsource

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.twc.retrofitsource.databinding.ActMainBinding

/**
 * @author 唐万超
 * @date 2022/09/23
 */
class MainActivity : AppCompatActivity() {

    private val mBinding by lazy { ActMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.tvObserverView2.setOnClickListener {
            SourceObserverView2Activity.show(this)
        }
    }
}