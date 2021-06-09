package com.example.demo.sundu.custview.beziercurve.dragpop

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_berzier_curver_drag_pop.*

class BezierCurverDragPopActivity : AppCompatActivity() , DragBubbleView.OnBubbleStateListener ,
    View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berzier_curver_drag_pop)
        reCreateBtn.setOnClickListener(this)
        dragBubbleView.setText("99+")
        dragBubbleView.setOnBubbleStateListener(this)
    }



    override fun onDrag() {
    }

    override fun onRestore() {
    }

    override fun onDismiss() {
    }

    override fun onMove() {
    }

    override fun onClick(v: View?) {
        dragBubbleView.reCreate()
    }
}