package com.com.flag.entity;
import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.com.flag.R;
import java.util.Objects;

public class Utility {
    public static int AnswerIDtoButtonID(String id) {
        if (Objects.equals(id, "0"))
            return R.id.ButtonAnsA;
        else if (Objects.equals(id, "1"))
            return R.id.ButtonAnsB;
        else if (Objects.equals(id, "2"))
            return R.id.ButtonAnsC;
        else
            return R.id.ButtonAnsD;
    }
    public static int RandomExamID(int max) {
        return (int) ((Math.random() * (max)) + 0);
    }
    private int RandomAnswerID(int[] arr, int ans)
    {
        int temp;
        do {
            temp = (int) (Math.random() * arr.length);
        } while (arr[temp] == ans);
        return arr[temp];
    }
    public static Bitmap BlurBitmap(Bitmap image, Context context) {
        if (null == image)
            return null;
        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(context);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(25f);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }
}
