package io.github.adoniasalcantara.ezgas.ui.common

import androidx.constraintlayout.motion.widget.MotionLayout

interface TransitionListenerAdapter : MotionLayout.TransitionListener {

    override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}

    override fun onTransitionChange(
        motionLayout: MotionLayout?,
        startId: Int,
        endId: Int,
        progress: Float
    ) {}

    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {}

    override fun onTransitionTrigger(
        motionLayout: MotionLayout?,
        startId: Int,
        endId: Boolean,
        progress: Float
    ) {}
}