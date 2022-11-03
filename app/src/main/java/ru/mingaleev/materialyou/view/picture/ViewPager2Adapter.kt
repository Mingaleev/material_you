package ru.mingaleev.materialyou.view.picture

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.mingaleev.materialyou.utils.SELECT_DAY_DAY_BEFORE_YESTERDAY
import ru.mingaleev.materialyou.utils.SELECT_DAY_TODAY
import ru.mingaleev.materialyou.utils.SELECT_DAY_YESTERDAY
import ru.mingaleev.materialyou.view.picture.PictureOfTheDayFragment

class ViewPager2Adapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragments =
        arrayOf(
            PictureOfTheDayFragment(SELECT_DAY_TODAY),
            PictureOfTheDayFragment(SELECT_DAY_YESTERDAY),
            PictureOfTheDayFragment(SELECT_DAY_DAY_BEFORE_YESTERDAY)
        )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}