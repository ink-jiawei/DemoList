package com.inkhjw.lottieandroiddemo.lottie;

interface AnimatableValue<T> {
  KeyframeAnimation<T> createAnimation();
  boolean hasAnimation();
}
