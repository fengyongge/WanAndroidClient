package com.fengyongge.baseframework

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
abstract class BaseFragment : Fragment() {

    lateinit var mContext: BaseActivity
    private var isViewCreated: Boolean = false
    private var isVisibleToUser: Boolean = false
    @LayoutRes
    abstract fun initLayout(): Int

    abstract fun initView()

    abstract fun initData()

    abstract fun initLoad()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as BaseActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        beginLoadData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isViewCreated = true
        return inflater.inflate(initLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        beginLoadData()
    }


    private fun beginLoadData(){
        if (isViewCreated && isVisibleToUser && isParentVisible() ) {
            initLoad()
            dispatchParentVisibleState()
        }
    }

    private fun isParentVisible(): Boolean {
        val fragment: Fragment? = parentFragment
        return fragment == null || (fragment is BaseFragment && fragment.isVisibleToUser)
    }


    private fun dispatchParentVisibleState() {
        val fragmentManager: FragmentManager = childFragmentManager
        val fragments: List<Fragment> = fragmentManager.fragments
        if (fragments.isEmpty()) {
            return
        }
        for (child in fragments) {
            if (child is BaseFragment && child.isVisibleToUser) {
                child.beginLoadData()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        isViewCreated = false
        isVisibleToUser = false
    }


}