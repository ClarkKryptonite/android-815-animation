package com.example.staffwindowanimation

import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.staffwindowanimation.databinding.DialogFragmentHintBinding

/**
 * @author kun
 * @since 2021-Aug-02
 */
class HintDialogFragment : DialogFragment() {

    var showLocationY: Int = 0



    private lateinit var binding: DialogFragmentHintBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentHintBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.close.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            setCanceledOnTouchOutside(false)
            setOnKeyListener { dialog, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
        dialog?.window?.apply {
            val screenWidth: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val bounds = windowManager?.currentWindowMetrics?.bounds
                bounds?.width() ?: 0 * 0.8
            } else {
                val point = Point()
                windowManager.defaultDisplay.getSize(point)
                point.x * 0.8
            }.toInt()
            attributes.y = showLocationY
            attributes.gravity = Gravity.TOP
            attributes.width = screenWidth
            attributes.height = 200 * 3
            attributes.dimAmount = 0f
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}