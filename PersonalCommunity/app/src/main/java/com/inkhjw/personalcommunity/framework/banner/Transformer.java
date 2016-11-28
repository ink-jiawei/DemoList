package com.inkhjw.personalcommunity.framework.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.inkhjw.personalcommunity.framework.banner.transformer.AccordionTransformer;
import com.inkhjw.personalcommunity.framework.banner.transformer.BackgroundToForegroundTransformer;
import com.inkhjw.personalcommunity.framework.banner.transformer.CubeInTransformer;
import com.inkhjw.personalcommunity.framework.banner.transformer.CubeOutTransformer;
import com.inkhjw.personalcommunity.framework.banner.transformer.DefaultTransformer;
import com.inkhjw.personalcommunity.framework.banner.transformer.DepthPageTransformer;
import com.inkhjw.personalcommunity.framework.banner.transformer.FlipHorizontalTransformer;
import com.inkhjw.personalcommunity.framework.banner.transformer.FlipVerticalTransformer;
import com.inkhjw.personalcommunity.framework.banner.transformer.ForegroundToBackgroundTransformer;
import com.inkhjw.personalcommunity.framework.banner.transformer.RotateDownTransformer;
import com.inkhjw.personalcommunity.framework.banner.transformer.RotateUpTransformer;
import com.inkhjw.personalcommunity.framework.banner.transformer.ScaleInOutTransformer;
import com.inkhjw.personalcommunity.framework.banner.transformer.StackTransformer;
import com.inkhjw.personalcommunity.framework.banner.transformer.TabletTransformer;
import com.inkhjw.personalcommunity.framework.banner.transformer.ZoomInTransformer;
import com.inkhjw.personalcommunity.framework.banner.transformer.ZoomOutSlideTransformer;
import com.inkhjw.personalcommunity.framework.banner.transformer.ZoomOutTranformer;


public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
