package com.example.staffwindowanimation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Point
import android.graphics.PointF
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.Scene
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.example.staffwindowanimation.databinding.FragmentMainBinding

/**
 * @author kun
 * @since 2021-Aug-02
 */
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            button.setOnClickListener {
                addCardView()
            }
        }
    }

    private val cardView by lazy {
        layoutInflater.inflate(R.layout.dialog_fragment_hint, binding.root, false)
    }

    private fun addCardView() {
        binding.root.removeView(cardView)
        cardView.findViewById<View>(R.id.close).setOnClickListener {
            val endPoint = PointF()
            endPoint.x = binding.icon.width.toFloat() / 2 + binding.icon.x
            endPoint.y = binding.icon.height.toFloat() / 2 + binding.icon.y
            removeCardViewWithAnimation(endPoint)
        }

        // toolBar Height
        val topMarginValue = binding.toolBar.height + 90

        // screenWidth
        val screenWidth: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val bounds = activity?.windowManager?.currentWindowMetrics?.bounds
            bounds?.width() ?: 0 * 0.8
        } else {
            val point = Point()
            activity?.windowManager?.defaultDisplay?.getSize(point)
            point.x * 0.8
        }.toInt()

        val params = ConstraintLayout.LayoutParams(screenWidth, 600).apply {
            topToTop = ConstraintSet.PARENT_ID
            startToStart = ConstraintSet.PARENT_ID
            endToEnd = ConstraintSet.PARENT_ID
            topMargin = topMarginValue
        }
        cardView.layoutParams = params

        TransitionManager.beginDelayedTransition(binding.root)
        binding.root.addView(cardView)
    }

    private fun removeCardViewWithAnimation(endPoint: PointF) {
        val animatorSet = AnimatorSet()

        //scale local
        val scaleXHolder = PropertyValuesHolder.ofFloat("scaleX", 1.14f, 1f)
        val scaleYHolder = PropertyValuesHolder.ofFloat("scaleY", 1.14f, 1f)
        val scaleLocalAnimator =
            ObjectAnimator.ofPropertyValuesHolder(cardView, scaleXHolder, scaleYHolder).apply {
                duration = 300
            }

        //scale and transition
        val scaleXHolder2 = PropertyValuesHolder.ofFloat("scaleX", 0f)
        val scaleYHolder2 = PropertyValuesHolder.ofFloat("scaleY", 0f)
        val transX = PropertyValuesHolder.ofFloat("translationX", endPoint.x)
        val transY = PropertyValuesHolder.ofFloat("translationY", endPoint.y)
        val scaleMoveAnimator = ObjectAnimator.ofPropertyValuesHolder(
            cardView,
            scaleXHolder2, scaleYHolder2,
            transX, transY
        ).apply {
            duration = 700
        }

        animatorSet.apply {
            addListener(onEnd = {
                binding.root.removeView(cardView)
                Toast.makeText(context, "AnimationEnd", Toast.LENGTH_SHORT).show()
            }, onCancel = {

            })
            interpolator = AccelerateDecelerateInterpolator()
            playSequentially(scaleLocalAnimator, scaleMoveAnimator)
            start()
        }

        //another icon scale
    }
}