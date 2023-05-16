package com.example.demo.sundu.ontouch

import android.view.MotionEvent

interface ChildEventInterface {

   fun  dispatchEvent(motionEvent:MotionEvent?):Boolean
 }