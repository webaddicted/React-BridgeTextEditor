package com.bridgetest;

import android.util.Log;

import com.chinalwb.are.AREditor;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class ReactImageManager extends  SimpleViewManager<AREditor> {


        public static final String REACT_CLASS = "MaterialCalendarView";

        @Override
        public String getName() {
            return REACT_CLASS;
        }

        @Override
        protected AREditor createViewInstance(ThemedReactContext reactContext) {
            MaterialCalendarView materialCalendarView = new MaterialCalendarView(reactContext);
            AREditor arEditor=new AREditor(MainApplication.getInstance().mActivity);
            arEditor.mActivityContext = MainApplication.getInstance().mActivity;

            clickEvent(reactContext);
            return arEditor;
        }


    public void clickEvent(ThemedReactContext reactContext) {
        WritableMap event = Arguments.createMap();
        event.putNull("clickEvent");
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("clickEventName", event);

    }

}
