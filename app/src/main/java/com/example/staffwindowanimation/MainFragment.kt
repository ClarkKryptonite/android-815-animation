package com.example.staffwindowanimation

import android.animation.AnimatorSet
import android.animation.Keyframe
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
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.Scene
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.example.staffwindowanimation.databinding.FragmentMainBinding
import kotlin.math.roundToInt

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
        layoutInflater.inflate(R.layout.dialog_fragment_hint, null)
    }

    private fun addCardView() {
        binding.root.removeView(cardView)
        cardView.scaleX = 1f
        cardView.scaleY = 1f
        cardView.findViewById<View>(R.id.close).setOnClickListener {
            val endPoint = PointF()
            endPoint.x = binding.icon.width / 2 + binding.icon.x
            endPoint.y = binding.icon.height / 2 + binding.icon.y
            removeCardViewWithAnimation(endPoint)
        }

        // toolBar Height
        val topMarginValue = binding.toolBar.height + 90

        // screenWidth
        val screenWidth: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val bounds = activity?.windowManager?.currentWindowMetrics?.bounds
            bounds?.width() ?: 0
        } else {
            val point = Point()
            activity?.windowManager?.defaultDisplay?.getSize(point)
            point.x
        }.toInt()

        cardView.layoutParams?.apply {
            cardView.x = screenWidth * 0.1f
            cardView.y = topMarginValue.toFloat()
        } ?: cardView.apply {
            layoutParams =
                ConstraintLayout.LayoutParams((screenWidth * 0.80f).roundToInt(), 600).apply {
                    topToTop = ConstraintSet.PARENT_ID
                    startToStart = ConstraintSet.PARENT_ID
                    endToEnd = ConstraintSet.PARENT_ID
                    topMargin = topMarginValue
                }
        }

        TransitionManager.beginDelayedTransition(binding.root)
        binding.root.addView(cardView)
    }

    private fun removeCardViewWithAnimation(endPoint: PointF) {
        val animatorSet = AnimatorSet()

        //scale local
        val scaleXHolder = PropertyValuesHolder.ofFloat("scaleX", 1.07f, 1f)
        val scaleYHolder = PropertyValuesHolder.ofFloat("scaleY", 1.07f, 1f)
        val scaleLocalAnimator =
            ObjectAnimator.ofPropertyValuesHolder(cardView, scaleXHolder, scaleYHolder).apply {
                duration = 300
            }

        //scale and transition
        // val scaleXHolder2 = PropertyValuesHolder.ofFloat("scaleX", 0f)
        val scaleXKeyframeHolder = PropertyValuesHolder.ofKeyframe(
            "scaleX",
            Keyframe.ofFloat(0f, 1f),
            Keyframe.ofFloat(0.05f, 1.07f),
            Keyframe.ofFloat(0.10f, 0.91f),
            Keyframe.ofFloat(0.15f, 0.562f),
            Keyframe.ofFloat(0.20f, 0.421f),
            Keyframe.ofFloat(0.25f, 0.304f),
            Keyframe.ofFloat(0.30f, 0.212f),
            Keyframe.ofFloat(0.35f, 0.143f),
            Keyframe.ofFloat(0.40f, 0.094f),
            Keyframe.ofFloat(0.45f, 0.059f),
            Keyframe.ofFloat(0.50f, 0.036f),
            Keyframe.ofFloat(0.55f, 0.022f),
            Keyframe.ofFloat(0.60f, 0.013f),
            Keyframe.ofFloat(0.65f, 0.008f),
            Keyframe.ofFloat(0.70f, 0.005f),
            Keyframe.ofFloat(0.75f, 0.004f),
            Keyframe.ofFloat(0.80f, 0.003f),
            Keyframe.ofFloat(0.85f, 0.003f),
            Keyframe.ofFloat(0.90f, 0.002f),
            Keyframe.ofFloat(0.95f, 0.000f),
            Keyframe.ofFloat(1f, 0.000f),
        )
        // val scaleYHolder2 = PropertyValuesHolder.ofFloat("scaleY", 0f)
        val scaleYKeyframeHolder = PropertyValuesHolder.ofKeyframe(
            "scaleY",
            Keyframe.ofFloat(0f, 1f),
            Keyframe.ofFloat(0.05f, 1.07f),
            Keyframe.ofFloat(0.10f, 0.91f),
            Keyframe.ofFloat(0.15f, 0.562f),
            Keyframe.ofFloat(0.20f, 0.421f),
            Keyframe.ofFloat(0.25f, 0.304f),
            Keyframe.ofFloat(0.30f, 0.212f),
            Keyframe.ofFloat(0.35f, 0.143f),
            Keyframe.ofFloat(0.40f, 0.094f),
            Keyframe.ofFloat(0.45f, 0.059f),
            Keyframe.ofFloat(0.50f, 0.036f),
            Keyframe.ofFloat(0.55f, 0.022f),
            Keyframe.ofFloat(0.60f, 0.013f),
            Keyframe.ofFloat(0.65f, 0.008f),
            Keyframe.ofFloat(0.70f, 0.005f),
            Keyframe.ofFloat(0.75f, 0.004f),
            Keyframe.ofFloat(0.80f, 0.003f),
            Keyframe.ofFloat(0.85f, 0.003f),
            Keyframe.ofFloat(0.90f, 0.002f),
            Keyframe.ofFloat(0.95f, 0.000f),
            Keyframe.ofFloat(1f, 0.000f),
        )

        val transYValue = endPoint.y - cardView.height / 2 - cardView.y
        val transXValue = endPoint.x - cardView.width / 2 - cardView.x
        val transY =
            PropertyValuesHolder.ofFloat(
                "translationY",
                endPoint.y - cardView.height / 2 - cardView.y
            )
        val transYKeyframeHolder = PropertyValuesHolder.ofKeyframe(
            "translationY",
            Keyframe.ofFloat(0f, 0f),
            Keyframe.ofFloat(0.05f, (-0.06 * transYValue).toFloat()),
            Keyframe.ofFloat(0.10f, (-0.058 * transYValue).toFloat()),
            Keyframe.ofFloat(0.15f, (-0.041 * transYValue).toFloat()),
            Keyframe.ofFloat(0.20f, (0.121 * transYValue).toFloat()),
            Keyframe.ofFloat(0.25f, (0.299 * transYValue).toFloat()),
            Keyframe.ofFloat(0.30f, (0.461 * transYValue).toFloat()),
            Keyframe.ofFloat(0.35f, (0.599 * transYValue).toFloat()),
            Keyframe.ofFloat(0.40f, (0.708 * transYValue).toFloat()),
            Keyframe.ofFloat(0.45f, (0.792 * transYValue).toFloat()),
            Keyframe.ofFloat(0.50f, (0.854 * transYValue).toFloat()),
            Keyframe.ofFloat(0.55f, (0.899 * transYValue).toFloat()),
            Keyframe.ofFloat(0.60f, (0.931 * transYValue).toFloat()),
            Keyframe.ofFloat(0.65f, (0.953 * transYValue).toFloat()),
            Keyframe.ofFloat(0.70f, (0.968 * transYValue).toFloat()),
            Keyframe.ofFloat(0.75f, (0.978 * transYValue).toFloat()),
            Keyframe.ofFloat(0.80f, (0.986 * transYValue).toFloat()),
            Keyframe.ofFloat(0.85f, (0.992 * transYValue).toFloat()),
            Keyframe.ofFloat(0.90f, (0.996 * transYValue).toFloat()),
            Keyframe.ofFloat(0.95f, transYValue),
            Keyframe.ofFloat(1f, transYValue)
        )
        val transXKeyframeHolder = PropertyValuesHolder.ofKeyframe(
            "translationX",
            Keyframe.ofFloat(0f, 0f),
            Keyframe.ofFloat(0.05f, 0f),
            Keyframe.ofFloat(0.10f, 0f),
            Keyframe.ofFloat(0.15f, (0.283 * transXValue).toFloat()),
            Keyframe.ofFloat(0.20f, (0.499 * transXValue).toFloat()),
            Keyframe.ofFloat(0.25f, (0.659 * transXValue).toFloat()),
            Keyframe.ofFloat(0.30f, (0.775 * transXValue).toFloat()),
            Keyframe.ofFloat(0.35f, (0.858 * transXValue).toFloat()),
            Keyframe.ofFloat(0.40f, (0.913 * transXValue).toFloat()),
            Keyframe.ofFloat(0.45f, (0.949 * transXValue).toFloat()),
            Keyframe.ofFloat(0.50f, (0.971 * transXValue).toFloat()),
            Keyframe.ofFloat(0.55f, (0.984 * transXValue).toFloat()),
            Keyframe.ofFloat(0.60f, (0.991 * transXValue).toFloat()),
            Keyframe.ofFloat(0.65f, (0.995 * transXValue).toFloat()),
            Keyframe.ofFloat(0.70f, (0.997 * transXValue).toFloat()),
            Keyframe.ofFloat(0.75f, (0.998 * transXValue).toFloat()),
            Keyframe.ofFloat(0.80f, (0.999 * transXValue).toFloat()),
            Keyframe.ofFloat(0.85f, (0.999 * transXValue).toFloat()),
            Keyframe.ofFloat(0.90f, transXValue),
            Keyframe.ofFloat(0.95f, transXValue),
            Keyframe.ofFloat(1f, transXValue)
        )
        val scaleMoveAnimator = ObjectAnimator.ofPropertyValuesHolder(
            cardView,
            scaleXKeyframeHolder, scaleYKeyframeHolder,
            transXKeyframeHolder, transYKeyframeHolder
        ).apply {
            duration = 700
        }
        //another icon scale
        val scaleXHolder3 = PropertyValuesHolder.ofFloat("scaleX", 1.14f, 1f)
        val scaleYHolder3 = PropertyValuesHolder.ofFloat("scaleY", 1.14f, 1f)
        val scaleUserAnimator = ObjectAnimator.ofPropertyValuesHolder(
            binding.icon,
            scaleXHolder3, scaleYHolder3
        ).apply {
            duration = 300
        }

        animatorSet.apply {
            addListener(onEnd = {
                binding.root.removeView(cardView)
                Toast.makeText(context, "AnimationEnd", Toast.LENGTH_SHORT).show()
            }, onCancel = {

            })
            interpolator = AccelerateDecelerateInterpolator()
            playSequentially(scaleLocalAnimator, scaleMoveAnimator, scaleUserAnimator)
            start()
        }

    }
}