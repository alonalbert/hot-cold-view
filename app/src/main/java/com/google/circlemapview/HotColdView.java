package com.google.circlemapview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class HotColdView extends View {


  private final int hotColor;
  private final int coldColor;
  private final Paint paint;
  private final RectF rect = new RectF();

  public HotColdView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);

    final TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
        R.styleable.HotColdView, 0, 0);
    try {
      final Resources r = getResources();
      hotColor = a
          .getColor(R.styleable.HotColdView_hotColor, r.getColor(android.R.color.holo_red_dark));
      coldColor = a
          .getColor(R.styleable.HotColdView_coldColor, r.getColor(android.R.color.holo_green_dark));
    } finally {
      a.recycle();
    }

    paint = new Paint();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    final int width = this.getMeasuredWidth();
    final int height = this.getMeasuredHeight();
    final float cx = width / 2;
    final float cy = height / 2;
    final float radius;
    if (width < height) {
      radius = width / 2;
    } else {
      radius = height / 2;
    }

    rect.left = cx - radius;
    rect.right = cx + radius;
    rect.top = cy - radius;
    rect.bottom = cy + radius;

    paint.setStyle(Style.FILL);
    paint.setAntiAlias(true);
    paint.setColor(hotColor);

    for (int i = 0; i < 1; i++) {
      final double startAngle = Math.toRadians(i - 5);
      final double endAngle = Math.toRadians(i + 5);
      final float x0 = (float) Math.sin(startAngle);
      final float y0 = (float) Math.cos(startAngle);
      final float x1 = (float) Math.sin(endAngle);
      final float y1 = (float) Math.cos(endAngle);
      final LinearGradient gradient = new LinearGradient(x0, y0, x1, y1, hotColor, coldColor,
          TileMode.MIRROR);
      paint.setShader(gradient);
      canvas.drawArc(0, 0, width, height, 10f * i - 5f, 10f * i + 5f, true, paint);
    }
  }
}

